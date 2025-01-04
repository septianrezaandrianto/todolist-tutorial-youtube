import { Box } from "@mui/material"

const Background = ({children}) => {
    return (
        <>
            <Box
                sx={{
                    display: 'flex',
                    alignItems :'center',
                    justifyContent : 'center',
                    height: {xs : 'auto', sm : '91vh'},
                    minHeight : '91vh',
                    padding : { xs : 2, sm : 4},
                    backgroundColor : '#f5f5f5'
                }}
            >
                {children}
            </Box>
        </>
    )
}

export default Background