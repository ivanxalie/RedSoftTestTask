import { Box, IconButton } from "@mui/material";
import React, { useState } from "react";
import PhoneIcon from "@mui/icons-material/Phone";
import EmailIcon from "@mui/icons-material/Email";

const DepartmentInfo = (props) => {
	const [department, setDepartment] = useState({});
	if (props.href) {
		fetch(props.href)
			.then((response) => response.json())
			.then((json) => setDepartment(json))
			.catch((error) => console.error(error));
		return (
			<div width="100%">
				<h3>{department.name}</h3>
				<Box align="right" component="form">
					<div>
						<IconButton disabled={true}>
							<PhoneIcon />
						</IconButton>
						{department.phone ? department.phone : "No data available"}
					</div>
					<div>
						<IconButton disabled={true}>
							<EmailIcon />
						</IconButton>
						{department.email ? department.email : "No data available"}
					</div>
				</Box>
			</div>
		);
	}
	return <div>Sorry, we don't have information about selected department!</div>;
};

export default DepartmentInfo;
