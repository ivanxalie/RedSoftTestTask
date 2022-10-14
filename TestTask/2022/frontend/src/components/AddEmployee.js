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
import PersonAddIcon from "@mui/icons-material/PersonAdd";
import { SERVER_URL } from "./constants";

const AddEmployee = (props) => {
	const [open, setOpen] = useState(false);
	const [valid, setValid] = useState(true);
	const [employee, setEmployee] = useState({
		firstName: "",
		lastName: "",
		middleName: "",
		position: "",
		department: "",
	});
	const [positions, setPositions] = useState([]);
	const handleOpen = () => {
		fetch(SERVER_URL + "/employeePositions")
			.then((response) => response.json())
			.then((json) => setPositions(json._embedded.employeePositions))
			.catch(console.error);
		setOpen(true);
	};
	const handleSave = () => {
		if (validate()) {
			let employeeCopy = {
				...employee,
				department: props.department,
			};
			fetch(SERVER_URL + "/employees", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(employeeCopy),
			}).catch((error) => console.error(error));
			handleClose();
		}
	};

	const validate = () => {
		setValid(
			employee.firstName.length > 0 &&
				employee.lastName.length > 0 &&
				employee.position.length > 0
		);
		return (
			employee.firstName.length > 0 &&
			employee.lastName.length > 0 &&
			employee.position.length > 0
		);
	};

	const handleChange = (event) => {
		setEmployee({ ...employee, [event.target.name]: event.target.value });
	};
	const handleClose = () => {
		setEmployee({
			firstName: "",
			lastName: "",
			middleName: "",
			position: "",
			department: "",
		});
		setOpen(false);
	};
	return (
		<div align="left" width="100%">
			<Tooltip title="Create new employee">
				<IconButton onClick={handleOpen}>
					<PersonAddIcon color="success" fontSize="large" />
				</IconButton>
			</Tooltip>
			<Dialog open={open}>
				<DialogTitle>Add employee</DialogTitle>
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
								name="position"
								required={true}
								autoWidth={true}
								error={!valid}
								onChange={handleChange}
							>
								{positions.map((position) => (
									<MenuItem
										key={position._links.self.href}
										value={position._links.self.href}
									>
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
		</div>
	);
};

export default AddEmployee;
