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

const EditEmployeePosition = (props) => {
	const [open, setOpen] = useState(false);
	const [valid, setValid] = useState(true);
	const [position, setPosition] = useState({
		name: "",
		salary: "",
	});
	const handleOpen = () => {
		setPosition({
			...position,
			name: props.row.row.name,
			salary: props.row.row.salary,
		});
		setOpen(true);
	};
	const handleSave = () => {
		if (validate()) {
			fetch(props.row.id, {
				method: "PATCH",
				headers: { "Content-Type": "application/json" },
				body: JSON.stringify(position),
			})
				.then((response) => {
					if (response.ok) props.fetchPositions();
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
		setOpen(false);
	};
	return (
		<>
			<Tooltip title="Edit position">
				<IconButton onClick={handleOpen}>
					<EditIcon htmlColor="orange" />
				</IconButton>
			</Tooltip>
			<Dialog open={open}>
				<DialogTitle>Edit employee position</DialogTitle>
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
		</>
	);
};

export default EditEmployeePosition;
