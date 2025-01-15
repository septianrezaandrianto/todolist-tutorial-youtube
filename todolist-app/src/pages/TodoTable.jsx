import { Button, Paper, Stack } from "@mui/material";
import { DataGrid } from "@mui/x-data-grid";

export const TodoTable = ({
  dataList,
  loading,
  setShowDialog,
  action,
  generateMessage,
  setSelectedRow
}) =>{
  const columns = [
    { field: 'no', headerName: 'No', width: 70, sortable : false, headerAlign : 'center', align : 'center'},
    { field: 'priority', headerName: 'Prioritas', width: 100, sortable : false, headerAlign : 'center', align : 'center',
      renderCell : (params) => {
        let color = '';
        switch (params.row.priority) {
          case 'Tinggi':
            color = '#FF0000';
            break;
          case 'Sedang':
            color = '#facd05';
            break;
          default:
            color = '#03ab22'
        }
        return (
          <span style={{color, fontWeight : 'bold'}}>{params.row.priority}</span>
        )
      }
    },
    { field: 'startTime', headerName: 'Waktu Mulai', width: 130, sortable : false, headerAlign : 'center', align : 'center'},
    { field: 'endTime', headerName: 'Waktu Selesai', width: 130, sortable : false, headerAlign : 'center', align : 'center'},
    { field: 'title', headerName: 'Aktifitas', width: 355, sortable : false, headerAlign : 'center', align : 'center'},
    { field: 'status', headerName: 'Status', width: 100, sortable : false, headerAlign : 'center', align : 'center'},
    { field: 'action', headerName: 'Aksi', width: 100, sortable : false, headerAlign : 'center', align : 'center',
      renderCell: (params) => (
        <Stack
          sx = {{
            pt:2,
            justifyContent : 'center'
          }}
          direction="row"
          spacing={1}
        >
          {params.row.status === 'Dibuat' && (
            <>
              <Button
                variant="contained"
                size="small"
                onClick={()=> handleOpen(params.row)}
                sx ={{
                  backgroundColor : '#FF0000',
                  color: 'white',
                  '&:hover': {
                    backgroundColor : 'darkred'
                  },
                  padding : '4px 8px',
                  fontSize : '12px',
                  minWidth : 'auto',
                  height : '24px',
                }}
                disable={loading}
              >
                <span className="material-icons" style={{fontSize : '24px'}}>delete</span>
              </Button>
              <Button
                variant="contained"
                size="small"
                onClick={()=> handleOpenFinish(params.row)}
                sx ={{
                  backgroundColor : '#4CAF50',
                  color: 'white',
                  '&:hover': {
                    backgroundColor : 'green'
                  },
                  padding : '4px 8px',
                  fontSize : '12px',
                  minWidth : 'auto',
                  height : '24px',
                }}
                disable={loading}
              >
                <span className="material-icons" style={{fontSize : '20px'}}>check_circle</span>
              </Button>
            </>
          )}

        </Stack>
      )
    },
    
  ];

  const handleOpen = (rowData) => {
    setShowDialog(true)
    setSelectedRow(rowData)
    action.current = "Delete"
    generateMessage()
  }

  const handleOpenFinish = (rowData) => {

  }

  const paginationModel = {page : 0, pageSize : 10}

  return (
      <Paper
          sx= {{
              height : '400px',
              width : {sm : '68%', xs : '100%'}
          }}
      >
        <DataGrid
            rows={dataList}
            columns={columns}
            initialState={{
            pagination: {paginationModel}}}
            pageSizeOptions={[10]}
            sx = {{
              border : 0
            }}
            localeText={{
              noRowsLabel : 'List daftar belum dibuat'
            }}
            rowSelectionModel={[]}
        />
      </Paper>
  )
}