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
import EditIcon from "@mui/icons-material/Edit";

const UpdateDepartment = (props) => {
	const [open, setOpen] = useState(false);
	const [valid, setValid] = useState(true);
	const [department, setDepartment] = useState({
		name: "",
		phone: "",
		email: "",
	});

	const handleOpen = () => {
		setDepartment(
			props.departments.find(
				(department) => department._links.department.href === props.department
			)
		);
		setOpen(true);
	};
	const handleSave = () => {
		if (department.name.length > 0) {
			props.updateDepartment(department);
			handleClose();
		} else setValid(false);
	};
	const handleClose = () => {
		setOpen(false);
	};
	const handleChange = (event) => {
		setDepartment({ ...department, [event.target.name]: event.target.value });
	};
	if (props.departments.length === 0 || !props.department) return <></>;
	return (
		<>
			<Tooltip title="Edit department">
				<IconButton onClick={handleOpen}>
					<EditIcon htmlColor="orange" />
				</IconButton>
			</Tooltip>
			<Dialog open={open}>
				<DialogTitle>Edit department</DialogTitle>
				<DialogContent>
					<Stack spacing={2} mt={1}>
						<TextField
							label="Name"
							name="name"
							autoFocus
							value={department.name}
							required={true}
							onChange={handleChange}
							error={!valid}
							variant="standard"
						/>
						<TextField
							label="Phone"
							name="phone"
							value={department.phone}
							onChange={handleChange}
							variant="standard"
						/>
						<TextField
							label="Email"
							name="email"
							value={department.email}
							onChange={handleChange}
							variant="standard"
						/>
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

export default UpdateDepartment;
