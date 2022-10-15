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
	Tooltip,
} from "@mui/material";
import { SERVER_URL } from "./constants";
import PersonAddIcon from "@mui/icons-material/PersonAdd";

const AddEmployeePosition = (props) => {
	const [open, setOpen] = useState(false);
	const [valid, setValid] = useState(true);
	const [position, setPosition] = useState({
		name: "",
		salary: "",
	});
	const handleOpen = () => {
		setOpen(true);
	};
	const handleSave = () => {
		if (validate()) {
			fetch(SERVER_URL + "/employeePositions", {
				method: "POST",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(position),
			})
				.then((response) => {
					if (response.ok) props.fetchPositions();
					if (response.status === 400) {
						response.text().then((text) => alert(text));
					}
				})
				.catch((error) => console.error(error));
			handleClose();
		}
	};

	const validate = () => {
		setValid(!!position.name && !!position.salary);
		return !!position.name && !!position.salary;
	};

	const handleChange = (event) => {
		setPosition({ ...position, [event.target.name]: event.target.value });
	};
	const handleClose = () => {
		setPosition({});
		setOpen(false);
	};
	return (
		<div align="left" width="100%">
			<Tooltip title="Create new position">
				<IconButton onClick={handleOpen}>
					<PersonAddIcon color="success" fontSize="large" />
				</IconButton>
			</Tooltip>
			<Dialog open={open}>
				<DialogTitle>Add employee position</DialogTitle>
				<DialogContent>
					<Stack spacing={2} mt={1}>
						<TextField
							label="Name"
							name="name"
							autoFocus
							value={position.name}
							required={true}
							onChange={handleChange}
							error={!valid}
							variant="standard"
						/>
						<TextField
							label="Salary"
							name="salary"
							type="number"
							value={position.salary}
							required={true}
							onChange={handleChange}
							error={!valid}
							variant="standard"
						/>
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

export default AddEmployeePosition;
