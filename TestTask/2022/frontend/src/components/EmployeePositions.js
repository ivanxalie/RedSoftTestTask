import { DataGrid } from "@mui/x-data-grid";
import React, { useEffect, useState } from "react";
import AddEmployeePosition from "./AddEmployeePosition";
import { SERVER_URL } from "./constants";
import CustomToolbar from "./CustomToolbar";
import DeleteEmployeePosition from "./DeleteEmployeePosition";
import EditEmployeePosition from "./EditEmployeePosition";

const EmployeePositions = () => {
	const [positions, setPositions] = useState([]);
	const columns = [
		{ field: "name", headerName: "Position name", width: 200 },
		{ field: "salary", headerName: "Position salary", width: 200 },
		{
			field: "update",
			headerName: "",
			sortable: false,
			filterable: false,
			renderCell: (row) => (
				<EditEmployeePosition row={row} fetchPositions={fetchPositions} />
			),
		},
		{
			field: "delete",
			headerName: "",
			sortable: false,
			filterable: false,
			renderCell: (row) => (
				<DeleteEmployeePosition row={row} fetchPositions={fetchPositions} />
			),
		},
	];
	useEffect(() => {
		fetchPositions();
	}, []);
	const fetchPositions = () => {
		fetch(SERVER_URL + "/employeePositions")
			.then((response) => response.json())
			.then((json) => setPositions(json._embedded.employeePositions))
			.catch((error) => console.error(error));
	};
	return (
		<React.Fragment>
			<div style={{ height: 500, width: "100%" }}>
				<DataGrid
					rows={positions}
					columns={columns}
					disableSelectionOnClick={true}
					getRowId={(row) => row._links.self.href}
					components={{ Toolbar: CustomToolbar }}
				/>
				<AddEmployeePosition fetchPositions={fetchPositions} />
			</div>
		</React.Fragment>
	);
};

export default EmployeePositions;
