import { gridClasses } from "@mui/material";
import { GridToolbarContainer, GridToolbarExport } from "@mui/x-data-grid";

function CustomToolbar() {
	return (
		<GridToolbarContainer className={gridClasses.toolbarContainer}>
			<GridToolbarExport />
		</GridToolbarContainer>
	);
}

export default CustomToolbar;
