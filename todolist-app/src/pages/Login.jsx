import { Box, Button, InputAdornment, TextField, Typography } from "@mui/material"
import { Background } from "../components/Background"
import { useState } from "react"

export const Login = () => {
    const [waNumber, setWaNumber] = useState("");
    const [error, setError] = useState("");

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
                    disabled={!waNumber.trim() || Boolean(error)}
                >
                    Kirim
                </Button>
            </Box>
        </Background>
    )
}