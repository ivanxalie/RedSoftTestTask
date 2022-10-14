import { IconButton, Tooltip } from "@mui/material";
import DeleteIcon from "@mui/icons-material/Delete";

const DeleteDepartment = (props) => {
	if (props.departments.length === 0 || !props.department) return <></>;
	return (
		<Tooltip title="Delete department">
			<IconButton onClick={props.deleteSelectedDepartment}>
				<DeleteIcon color="error" />
			</IconButton>
		</Tooltip>
	);
};

export default DeleteDepartment;
