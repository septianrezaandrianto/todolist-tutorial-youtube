import { Box, Button, InputAdornment, TextField, Typography } from "@mui/material"
import  Background from "../components/Background"
import { useState } from "react"
import axios from "axios";
import { CustomSnackbar } from "../components/CustomSnackbar";
import { useNavigate } from "react-router-dom";

export const Login = () => {
    const [waNumber, setWaNumber] = useState("");
    const [error, setError] = useState("");
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");
    const [showsnackBar, setShowSnackbar] = useState(false);
    const navigate = useNavigate();
    const handleInputChange = (e) =>{
        const value = e.target.value;
        if(/^[0-9]/.test(value.replace("+62", ""))) {
            setWaNumber(value)
            setError("")
        } else {
            setWaNumber(value)
            setError("Input hanya boleh berupa angka")
        }
    }

    const handleClose =()=>{
        setShowSnackbar(false)
    }

    const handleOpen =()=> {
        setShowSnackbar(true)
    }

    const handleSubmit = async () => {
        setLoading(true)
        setMessage("")
        let msg;
        try {
            await axios.post('http://localhost:8080/user-detail/login', {
                waNumber : `+62${waNumber}`
            });
            localStorage.setItem("isAuthenticated", `+62${waNumber}`)
            msg = "Login berhasil!!!"
            console.log('Login berhasil')
            setTimeout(() => {
                navigate("/todo")
            }, 2000);
        } catch(error) {
            console.log('Login Gagal')
            const errorList = error.response != undefined ? error.response.data.errorList : error.message
            msg = Array.isArray(errorList) ? `Error: ${errorList.join(',')}` : `Error: ${errorList}`;
            
        } finally {
            setTimeout(() => {
                setLoading(false);
                setMessage(msg)
                handleOpen()
            }, 1000);
        }
    }
    console.log('waNumber', waNumber)
    return (
        <Background>
            <Box 
                sx={{
                    backgroundColor : '#fff',
                    padding: 4,
                    borderRadius: 2,
                    boxShadow: '0px 4px 10px rgba(0,0,0,0.1)',
                    minWidth: '100px',
                    textAlign : 'center',
                    width: { xs : 'auto', sm :'400px'},
                    height: 'auto'
                }}    
            >
                <Typography 
                    variant="h4"
                    sx ={{
                        mb : 5
                    }}    
                >
                    ToDo List Apps
                </Typography>
                <TextField
                    id="input-wa-number"
                    label="Masukan No WhatsApp"
                    variant="standard"
                    sx={{
                        width : '80%'
                    }}
                    InputProps={{
                        startAdornment:(
                            <InputAdornment position="start">
                                +62
                            </InputAdornment>
                        )
                    }}
                    value={waNumber}
                    onChange={handleInputChange}
                    error={Boolean(error)}
                    helperText={error}
                />
                <Button
                    variant="contained"
                    sx={{
                        width:'80%',
                        mt:3
                    }}
                    disabled={!waNumber.trim() || Boolean(error) || loading}
                    onClick={handleSubmit}
                >
                    {loading ? "Mengirim..." : "Kirim"}
                </Button>
                {showsnackBar && (
                    <CustomSnackbar
                        handleClose={handleClose}
                        handleOpen={handleOpen}
                        message={message}
                    />
                )}
            </Box>
        </Background>
    )
}