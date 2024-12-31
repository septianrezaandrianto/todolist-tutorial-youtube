import { Alert, Snackbar } from "@mui/material"

export const CustomSnackbar = ({
    handleClose,
    handleOpen,
    message
}) => {
    const severity = message && message.includes("Error:") ? "error" : "success";
    const msg = message.includes("Error:") ? message.replace("/Error:/i", "").trim() : message;
    const alertStyle = severity === "error" ?
            {backgroundColor : "#f44336", color:"#fff"} : {backgroundColor : "#e0e0e0", color:"#000"}
    return (
        <>
        <Snackbar 
            open={handleOpen} 
            autoHideDuration={6000} 
            onClose={handleClose}
        >
            <Alert
                onClose={handleClose}
                severity={severity}
                variant="filled"
                sx={{ width: '100%', ...alertStyle }}
            >
                {msg}
            </Alert>
        </Snackbar>
      </>
    )

}