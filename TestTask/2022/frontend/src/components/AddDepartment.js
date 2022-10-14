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
import AddIcon from "@mui/icons-material/Add";

const AddDepartment = (props) => {
	const [open, setOpen] = useState(false);
	const [valid, setValid] = useState(true);
	const [department, setDepartment] = useState({
		name: "",
		phone: "",
		email: "",
	});

	const handleOpen = () => {
		setOpen(true);
	};
	const handleSave = () => {
		if (department.name.length > 0) {
			props.saveDepartment(department);
			handleClose();
		} else setValid(false);
	};
	const handleClose = () => {
		setDepartment({
			name: "",
			phone: "",
			email: "",
		});
		setOpen(false);
	};
	const handleChange = (event) => {
		setDepartment({ ...department, [event.target.name]: event.target.value });
	};
	return (
		<>
			<Tooltip title="Create new department">
				<IconButton onClick={handleOpen}>
					<AddIcon color="success" />
				</IconButton>
			</Tooltip>
			<Dialog open={open}>
				<DialogTitle>Add new department</DialogTitle>
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

export default AddDepartment;
