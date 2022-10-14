import React, { useState } from "react";
import {
	Dialog,
	DialogActions,
	DialogContent,
	DialogTitle,
	Button,
	TextField,
	Stack,
	IconButton,
	Select,
	MenuItem,
	FormControl,
	InputLabel,
	Tooltip,
} from "@mui/material";
import EditIcon from "@mui/icons-material/Edit";
import { SERVER_URL } from "./constants";

const EditEmployee = (props) => {
	const [open, setOpen] = useState(false);
	const [valid, setValid] = useState(true);
	const [employee, setEmployee] = useState({
		firstName: "",
		lastName: "",
		middleName: "",
		position: "",
	});
	const [positions, setPositions] = useState([]);
	const handleOpen = () => {
		fetch(SERVER_URL + "/employeePositions")
			.then((response) => response.json())
			.then((json) => json._embedded.employeePositions)
			.then((positions) => {
				setPositions(positions);
				let realPosition = positions.find(
					(position) =>
						position.name === props.row.row.positionName &&
						position.salary === props.row.row.salary
				);
				if (realPosition) {
					setEmployee({
						...employee,
						firstName: props.row.row.firstName,
						lastName: props.row.row.lastName,
						middleName: props.row.row.middleName,
						position: realPosition._links.self.href,
					});
				}
			})
			.catch((error) => console.error(error));
		setOpen(true);
	};
	const handleSave = () => {
		if (validate()) {
			let employeeCopy = {
				...employee,
				position: employee.position,
			};
			fetch(props.row.id, {
				method: "PATCH",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(employeeCopy),
			}).catch((error) => console.error(error));
			handleClose();
		}
	};

	const validate = () => {
		setValid(employee.firstName.length > 0 && employee.lastName.length > 0);
		return employee.firstName.length > 0 && employee.lastName.length > 0;
	};

	const handleChange = (event) => {
		setEmployee({ ...employee, [event.target.name]: event.target.value });
	};
	const handleClose = () => {
		setOpen(false);
	};
	return (
		<>
			<Tooltip title="Edit employee">
				<IconButton onClick={handleOpen}>
					<EditIcon htmlColor="orange" />
				</IconButton>
			</Tooltip>
			<Dialog open={open}>
				<DialogTitle>Edit employee</DialogTitle>
				<DialogContent>
					<Stack spacing={2} mt={1}>
						<TextField
							label="First name"
							name="firstName"
							autoFocus
							value={employee.firstName}
							required={true}
							onChange={handleChange}
							error={!valid}
							variant="standard"
						/>
						<TextField
							label="Last name"
							name="lastName"
							autoFocus
							value={employee.lastName}
							required={true}
							onChange={handleChange}
							error={!valid}
							variant="standard"
						/>
						<TextField
							label="Middle name"
							name="middleName"
							autoFocus
							value={employee.middleName}
							onChange={handleChange}
							variant="standard"
						/>
						<FormControl fullWidth>
							<InputLabel>Position</InputLabel>
							<Select
								label="Position"
								value={employee.position}
								width="100%"
								name="position"
								onChange={handleChange}
							>
								{positions.map((position) => (
									<MenuItem key={position.id} value={position._links.self.href}>
										{position.name}
									</MenuItem>
								))}
							</Select>
						</FormControl>
					</Stack>
				</DialogContent>
				<DialogActions>
					<Button onClick={handleClose}>Cancel</Button>
					<Button onClick={handleSave}>Save</Button>
				</DialogActions>
			</Dialog>
		</>
	);
};

export default EditEmployee;
