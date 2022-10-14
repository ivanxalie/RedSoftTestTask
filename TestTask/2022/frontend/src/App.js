import "./App.css";
import { AppBar, Chip, Divider, Toolbar, Typography } from "@mui/material";
import React, { useState, useEffect } from "react";
import { SERVER_URL } from "./components/constants";
import { useNavigate, Route, Routes } from "react-router-dom";
import Main from "./components/Main";
import EmployeePositions from "./components/EmployeePositions";

function App() {
	const [departments, setDepartments] = useState([]);
	const [department, setDepartment] = useState("");
	const [employees, setEmployyees] = useState([]);
	const navigate = useNavigate();

	useEffect(() => {
		fetchDepartments();
	}, []);

	const fetchDepartments = () => {
		fetch(SERVER_URL + "/departments")
			.then((response) => response.json())
			.then((data) => {
				setDepartments(data._embedded.departments);
				if (data._embedded.departments[0]) {
					setDepartment(data._embedded.departments[0]._links.self.href);
				} else setDepartment("");
				return "";
			})
			.catch((error) => console.error(error));
	};

	const fetchEmployees = () => {
		fetch(department)
			.then((response) => response.json())
			.then((data) => data._links.employees.href)
			.then((link) => fetch(link))
			.then((response) => response.json())
			.then((data) => data._embedded.employees)
			.then((employees) => setEmployyees(employees))
			.catch((error) => console.error(error));
	};

	return (
		<div className="App">
			<AppBar position="static">
				<Toolbar>
					<Typography variant="h4" width="100%">
						Accounting of company employees
					</Typography>
					<Divider />
					<Chip
						label="Home"
						variant="outline"
						onClick={() => navigate("/")}
						color="info"
					/>
					<Chip
						label="Manage positions"
						variant="outline"
						onClick={() => navigate("/positions")}
						color="info"
					/>
				</Toolbar>
			</AppBar>
			<Routes>
				<Route
					path="/"
					element={
						<Main
							fetchDepartments={fetchDepartments}
							departments={departments}
							department={department}
							setDepartment={setDepartment}
							employees={employees}
							fetchEmployees={fetchEmployees}
						/>
					}
				/>
				<Route path="/positions" element={<EmployeePositions />} />
			</Routes>
		</div>
	);
}

export default App;
