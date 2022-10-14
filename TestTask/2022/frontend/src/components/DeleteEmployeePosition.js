import React from "react";
import { IconButton, Tooltip } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

const DeleteEmployeePosition = (props) => {
	const onDelete = () => {
		if (window.confirm("Are you sure to delete?")) {
			fetch(props.row.id, {
				method: "DELETE",
				headers: { "Content-Type": "application/json" },
			})
				.then((response) => {
					if (response.status === 400) {
						response.text().then((text) => alert(text));
					}
					props.fetchPositions();
				})
				.catch((error) => console.log(error));
		}
	};

	return (
		<Tooltip title="Delete position">
			<IconButton onClick={onDelete}>
				<DeleteIcon color="error" />
			</IconButton>
		</Tooltip>
	);
};

export default DeleteEmployeePosition;
