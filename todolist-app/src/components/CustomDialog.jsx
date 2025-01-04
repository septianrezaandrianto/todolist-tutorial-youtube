import { Button, Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle } from "@mui/material"

const CustomDialog = ({
    handleClose,
    handleOpen,
    title,
    description,
    handleApprove,
    loading
}) => {
    return (
        <Dialog
        open={handleOpen}
        onClose={handleClose}
        aria-labelledby="alert-dialog-title"
        aria-describedby="alert-dialog-description"
      >
        <DialogTitle id="alert-dialog-title">
          {title}
        </DialogTitle>
        <DialogContent>
          <DialogContentText id="alert-dialog-description">
            {description}
          </DialogContentText>
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} disabled={loading}>Tidak</Button>
          <Button onClick={handleApprove} autoFocus disabled={loading}>
            Lanjut
          </Button>
        </DialogActions>
      </Dialog>
    )
}

export default CustomDialog