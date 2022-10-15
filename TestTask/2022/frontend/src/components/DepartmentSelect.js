import React from "react";
import { SERVER_URL } from "./constants";
import { Select, MenuItem, Stack } from "@mui/material";
import AddDepartment from "./AddDepartment";
import EditDepartment from "./EditDepartment";
import DeleteDepartment from "./DeleteDepartment";

const DepartmentsSelect = (props) => {
	const saveDepartment = (department) => {
		fetch(SERVER_URL + "/departments", {
			method: "POST",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify(department),
		})
			.then((response) => {
				if (response.ok) props.fetchDepartments();
				else alert("Can's save department. Ask developer for help!");
			})
			.catch((error) => console.error(error));
	};

	const updateDepartment = (department) => {
		if (department)
			fetch(department._links.self.href, {
				method: "PUT",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(department),
			})
				.then((response) => {
					if (response.ok) props.fetchDepartments();
					else alert("Can's save department. Ask developer for help!");
				})
				.catch((error) => console.error(error));
	};

	async function changeDepartment(department) {
		props.setDepartment(department);
	}

	const deleteSelectedDepartment = () => {
		if (props.department && window.confirm("Are you sure to delete?"))
			fetch(props.department, {
				method: "DELETE",
			})
				.then((response) => {
					if (response.ok) props.fetchDepartments();
					else alert("Can's save department. Ask developer for help!");
				})
				.catch((error) => console.error(error));
	};

	return (
		<React.Fragment>
			<Stack>
				<div align="justify">
					<Select
						width="100%"
						value={props.department}
						onChange={(event) => {
							changeDepartment(event.target.value);
						}}
					>
						{props.departments.map((dept) => (
							<MenuItem
								key={dept._links.self.href}
								value={dept._links.self.href}
							>
								{dept.name}
							</MenuItem>
						))}
					</Select>
					<AddDepartment saveDepartment={saveDepartment} />
					<EditDepartment
						departments={props.departments}
						department={props.department}
						setDepartment={props.setDepartment}
						updateDepartment={updateDepartment}
					/>
					<DeleteDepartment
						departments={props.departments}
						department={props.department}
						deleteSelectedDepartment={deleteSelectedDepartment}
					/>
				</div>
			</Stack>
		</React.Fragment>
	);
};

export default DepartmentsSelect;
