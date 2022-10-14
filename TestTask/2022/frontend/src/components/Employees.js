import React from "react";
import { DataGrid } from "@mui/x-data-grid";
import DeleteEmployee from "./DeleteEmployee";
import EditEmployee from "./EditEmployee";
import AddEmployee from "./AddEmployee";
import DepartmentInfo from "./DepartmentInfo";
import CustomToolbar from "./CustomToolbar";
import { IconButton, Tooltip } from "@mui/material";
import StarIcon from "@mui/icons-material/Star";
import AssignOrFireBoss from "./AssignOrFireBoss";

const Employees = (props) => {
	const columns = [
		{
			field: "isBoss",
			headerName: "",
			width: 20,
			renderCell: (row) =>
				row.row.boss ? (
					<Tooltip title="He is the boss of this department!">
						<IconButton>
							<StarIcon color="success" />
						</IconButton>
					</Tooltip>
				) : (
					<></>
				),
		},
		{ field: "firstName", headerName: "First Name", width: 200 },
		{ field: "lastName", headerName: "Last Name", width: 200 },
		{ field: "middleName", headerName: "Middle Name", width: 200 },
		{ field: "positionName", headerName: "Position", width: 200 },
		{ field: "salary", headerName: "Salary", width: 200 },
		{
			field: "update",
			headerName: "",
			sortable: false,
			filterable: false,
			renderCell: (row) => <EditEmployee row={row} />,
		},
		{
			field: "delete",
			headerName: "",
			sortable: false,
			filterable: false,
			renderCell: (row) => <DeleteEmployee row={row} />,
		},
		{
			field: "assignOrFire",
			headerName: "",
			sortable: false,
			filterable: false,
			renderCell: (row) => (
				<AssignOrFireBoss row={row} employees={props.employees} />
			),
		},
	];
	if (props.department) {
		props.fetchEmployees();
		return (
			<React.Fragment>
				<DepartmentInfo href={props.department} />
				<div style={{ height: 500, width: "100%" }}>
					<DataGrid
						rows={props.employees}
						columns={columns}
						disableSelectionOnClick={true}
						getRowId={(row) => row._links.self.href}
						components={{ Toolbar: CustomToolbar }}
					/>
					<AddEmployee department={props.department} />
				</div>
			</React.Fragment>
		);
	}
	return <></>;
};

export default Employees;
