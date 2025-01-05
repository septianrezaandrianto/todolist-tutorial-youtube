import { act, useRef } from "react"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { Box, Button, CircularProgress, FormControl, FormHelperText, InputLabel, MenuItem, Select, TextField, Typography } from "@mui/material"
import Background from "../components/Background"
import CustomDialog from "../components/CustomDialog"
import { CustomSnackbar } from "../components/CustomSnackbar"
import { LocalizationProvider, TimePicker } from "@mui/x-date-pickers"
import {AdapterDayjs} from "@mui/x-date-pickers/AdapterDayjs"
import dayjs from "dayjs"
import utc from 'dayjs/plugin/utc'
import axios from "axios"

export const Todo = () => {
    const [showDialog, setShowDialog] = useState(false)
    const [loading, setLoading] = useState(false)
    const waNumber = localStorage.getItem('isAuthenticated')
    const [showSnackbar, setShowSnackbar] = useState(false)
    const action = useRef("View")
    const message = useRef({
        title : '',
        description : ''
    })
    const snackbarMessage = useRef('')
    const navigate = useNavigate();

    dayjs.extend(utc)
    const [startTime, setStartTime] = useState('')
    const [endTime, setEndTime] = useState('')
    const [activity, setActivity] = useState('')
    const [priority, setPriority] = useState('')
    const [error, setError] = useState('')
    const [dataList, setDataList] = useState([])

    const isButtonDisabled = !startTime || !endTime || !activity || !priority || error;

    const handleStartTimeChange = (newValue) => {
        setStartTime(newValue ? newValue : '')
    }

    const handleEndTimeChange = (newValue) => {
        if (newValue && startTime && dayjs(newValue).isBefore(dayjs(startTime))) {
            setError("Waktu selesai tidak boleh kurang dari waktu mulai")
        } else {
            setEndTime(newValue)
            setError('')
        }
    }

    const handleActivityChange = (event) => {
        setActivity(event.target.value)
    }

    const handlePriorityChange = (event) => {
        setPriority(event.target.value)
    }

    const generateMessage = () => {
        if(action.current === 'View') {
            message.current.title = "Melanjutkan proses keluar"
            message.current.description = "Apakah anda yakin keluar dari halaman ini?"
        } else if (action.current === 'Add') {
            message.current.title = "Melanjutkan proses Tambah"
            message.current.description = "Apakah anda yakin ingin menambahkan aktifitas tersebut?"
        }
    }

    const handleLogout = () => {
        setLoading(true)
        snackbarMessage.current = "Sukses keluar dari halaman ini!"
        setShowSnackbar(true)
        setTimeout(() => {
            setLoading(false)
            localStorage.removeItem('isAuthenticated')
            navigate("/")
        }, 1000);
    }

    const handleAdd = async () => {
        setLoading(true)
        const payload = {
            startTime: dayjs(startTime).format("HH:mm"),
            endTime : dayjs(endTime).format("HH:mm"),
            title : activity,
            priority : priority,
            waNumber : waNumber
        }

        try {
            const response = await axios.post("http://localhost:8080/todo/add", payload);
            setStartTime('')
            setEndTime('')
            setActivity('')
            setPriority('')
            setError('')
            console.log("response", response)
            snackbarMessage.current = "Sukses menambahkan aktifitas"
        } catch (error) {
            console.log('Error', error)
            const errorList = error.response.data.errorList;
            snackbarMessage.current = errorList.length > 1 ? `Error: ${errorList.join(',')}` : `Error: ${errorList[0]}`
        } finally {
            handleClose();
            setTimeout(() => {
                setLoading(false)
                setShowSnackbar(true)
            }, 1000);
        }
    }
    

    const handleApprove = () => {
        if (action.current === 'View') {
            handleLogout();
        } else if(action.current === 'Add') {
            handleAdd()
        }
    }

    const handleOpen = () => {
        action.current = 'Add'
        setShowDialog(true)
        generateMessage()
    }

    const handleClose = () => {
        setShowDialog(false)
        action.current = 'View'
    }

    const getNowDate = () => {
        const today = new Date();
        const day = String(today.getDate()).padStart(2, '0')
        const month = String(today.getMonth() + 1).padStart(2, '0')
        const year = today.getFullYear();
        return `${day}/${month}/${year}`
    }
    return(
        <Background>
            <Box
                sx ={{
                    backgroundColor : '#fff',
                    padding : 4,
                    borderRadius : 2,
                    boxShadow : '0px 4px 10px rgba(0,0,0,0.1)',
                    minWidth : '100px',
                    textAlign : 'center',
                    width : {xs : 'auto', sm : '400px'},
                    height : 'auto'
                }}
            >
                <Button
                    variant="contained"
                    sx = {{
                        width : '0px',
                        height : '40px',
                        mt : -3,
                        ml : {sm : 45, xs : 31},
                        display : 'flex',
                        justifyContent : 'center',
                        alignItems : 'center',
                        backgroundColor : '#006400',
                        '&:hover' : {
                            backgroundColor : '#004d00'
                        }
                    }}
                    onClick={() => {
                        action.current = "View"
                        setShowDialog(true)
                        generateMessage()
                    }}
                    disabled={loading}
                >
                    <span
                        className="material-icons"
                        style={{
                            fontSize : '24px',
                            color: 'white'
                        }}
                    >
                        logout
                    </span>
                </Button>

                <Typography variant="h5" pb="50px">
                    Daftar Aktifitasmu <br/> {getNowDate()}
                </Typography>
                {loading && (
                    <Box
                        sx = {{
                            display : 'flex',
                            justifyContent : 'center',
                            mb : 2
                        }}
                    >
                        <CircularProgress size={'200px'}/>
                    </Box>    
                )}

                <Box sx={{display : loading ? 'none' : 'block'}}>
                    <Box sx ={{mb : 2}}>
                        <LocalizationProvider dateAdapter={AdapterDayjs}>
                            <TimePicker 
                                label = "Waktu Mulai"
                                ampm={false}
                                sx = {{
                                    minWidth : '190px',
                                    width : {xs : '300px', sm : '150px'},
                                    pr : {xs : 0, sm : 2},
                                    pb : {xs : 2, sm : 0}
                                }}
                                value={startTime ? dayjs(startTime) : null}
                                onChange={handleStartTimeChange}
                            />
                            <TimePicker 
                                label = "Waktu Selesai"
                                ampm={false}
                                sx = {{
                                    minWidth : '190px',
                                    width : {xs : '300px', sm : '150px'},
                                }}
                                value={endTime? dayjs(endTime) : null}
                                onChange={handleEndTimeChange}
                            />
                        </LocalizationProvider>
                        {error && (
                            <FormHelperText error>{error}</FormHelperText>
                        )}
                    </Box>

                    <TextField
                        id="activity"
                        label="Aktifitas"
                        fullWidth
                        autoComplete="off"
                        sx={{mb : 2}}
                        value={activity}
                        onChange={handleActivityChange}
                    />
                    <FormControl sx={{minWidth : '100%'}}>
                        <InputLabel id="priority-select-label">
                            Prioritas
                        </InputLabel>
                        <Select
                            labelId="priority-select-label"
                            id="priority-select"
                            label="Prioritas"
                            value={priority}
                            onChange={handlePriorityChange}
                        >
                            <MenuItem value=""><em>...</em></MenuItem>
                            <MenuItem value={'High'}>Tinggi</MenuItem>
                            <MenuItem value={'Medium'}>Sedang</MenuItem>
                            <MenuItem value={'Low'}>Rendah</MenuItem>
                        </Select>
                    </FormControl>
                    <Button
                        variant="contained"
                        sx = {{
                            width : '100%',
                            mt:3
                        }}
                        disabled={isButtonDisabled}
                        onClick={handleOpen}
                    >
                        Tambah
                    </Button>

                </Box>

            </Box>

            {showDialog && (
                <CustomDialog 
                    handleOpen={handleOpen}
                    handleClose={handleClose}
                    handleApprove={handleApprove}
                    title={message.current.title}
                    description={message.current.description}
                    loading={loading}
                />
            )}

            {showSnackbar && (
                <CustomSnackbar 
                    handleClose={() => {
                        setShowSnackbar(false)
                    }}
                    handleOpen={() => {
                        setShowSnackbar(true)
                    }}
                    message={snackbarMessage.current}
                />
            )}
        </Background>
    )
}