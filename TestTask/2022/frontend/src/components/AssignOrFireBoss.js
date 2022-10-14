import CloseIcon from "@mui/icons-material/Close";
import { IconButton, Tooltip } from "@mui/material";
import AssignmentIndIcon from "@mui/icons-material/AssignmentInd";

const AssignOrFireBoss = (props) => {
	const fireBoss = () => {
		if (window.confirm("Are you sure to fire?")) {
			updateState(false);
		}
	};
	const assignBoss = () => {
		if (window.confirm("Are you sure to assign?")) {
			updateState(true);
		}
	};
	const updateState = (isBoss) => {
		fetch(props.row.id, {
			method: "PATCH",
			headers: { "Content-Type": "application/json" },
			body: JSON.stringify({ boss: isBoss }),
		}).catch((error) => console.error(error));
	};
	if (props.row.row.boss)
		return (
			<Tooltip title="Fire">
				<IconButton onClick={fireBoss}>
					<CloseIcon color="error" />
				</IconButton>
			</Tooltip>
		);
	let boss = props.employees.find((employee) => employee.boss);
	if (boss) return <></>;
	return (
		<Tooltip title="Assign as boss of this department">
			<IconButton onClick={assignBoss}>
				<AssignmentIndIcon color="success" />
			</IconButton>
		</Tooltip>
	);
};

export default AssignOrFireBoss;
