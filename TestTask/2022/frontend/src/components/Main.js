import DepartmentsSelect from "./DepartmentSelect";
import Employees from "./Employees";

const Main = (props) => {
	return (
		<>
			<DepartmentsSelect
				fetchDepartments={props.fetchDepartments}
				departments={props.departments}
				department={props.department}
				setDepartment={props.setDepartment}
			/>
			<Employees
				employees={props.employees}
				department={props.department}
				fetchEmployees={props.fetchEmployees}
			/>
		</>
	);
};

export default Main;
