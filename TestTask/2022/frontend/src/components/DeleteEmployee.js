import React from "react";
import { IconButton, Tooltip } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

const DeleteEmployee = (props) => {
	const onDelete = () => {
		if (window.confirm("Are you sure to delete?")) {
			fetch(props.row.id, {
				method: "DELETE",
				headers: { "Content-Type": "application/json" },
			}).catch((error) => console.error(error));
		}
	};

	return (
		<Tooltip title="Delete employee">
			<IconButton onClick={onDelete}>
				<DeleteIcon color="error" />
			</IconButton>
		</Tooltip>
	);
};

export default DeleteEmployee;
