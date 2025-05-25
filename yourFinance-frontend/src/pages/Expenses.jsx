import React, { useEffect, useState } from 'react';
import { Box, Paper, Typography, IconButton, Snackbar } from '@mui/material';
import DeleteIcon from '@mui/icons-material/Delete';
import { DataGrid } from '@mui/x-data-grid';
import { getExpenses, deleteExpense } from '../api/expenses';

const columns = [
    { field: 'id', headerName: 'ID', width: 90 },
    { field: 'amount', headerName: 'Amount', width: 120, type: 'number' },
    { field: 'description', headerName: 'Description', width: 200 },
    { field: 'category', headerName: 'Category', width: 130 },
    { field: 'date', headerName: 'Date', width: 160 },
    {
        field: 'actions',
        headerName: 'Actions',
        width: 100,
        sortable: false,
        renderCell: (params) => (
            <IconButton
                color="error"
                onClick={() => params.row.onDelete(params.row.id)}
                aria-label="delete"
            >
                <DeleteIcon />
            </IconButton>
        ),
    },
];

const Expenses = () => {
    const [expenses, setExpenses] = useState([]);
    const [total, setTotal] = useState(0);
    const [page, setPage] = useState(0);
    const [pageSize, setPageSize] = useState(10);
    const [loading, setLoading] = useState(false);
    const [snackbar, setSnackbar] = useState({ open: false, message: '' });

    const fetchExpenses = async () => {
        setLoading(true);
        try {
            const data = await getExpenses(page, pageSize);
            // Attach onDelete handler to each row for inline action
            setExpenses(data.expenses.map((exp) => ({
                ...exp,
                onDelete: handleDelete,
            })));
            setTotal(data.total);
        } catch {
            setSnackbar({ open: true, message: 'Failed to fetch expenses' });
        }
        setLoading(false);
    };

    useEffect(() => {
        fetchExpenses();
        // eslint-disable-next-line
    }, [page, pageSize]);

    const handleDelete = async (id) => {
        if (!window.confirm('Delete this expense?')) return;
        try {
            await deleteExpense(id);
            setSnackbar({ open: true, message: 'Expense deleted' });
            fetchExpenses();
        } catch {
            setSnackbar({ open: true, message: 'Failed to delete expense' });
        }
    };

    return (
        <Box sx={{ p: 4, background: '#0b1623', minHeight: '100vh' }}>
            <Paper sx={{ p: 3, background: '#162036' }}>
                <Typography variant="h5" gutterBottom>
                    Expenses
                </Typography>
                <DataGrid
                    rows={expenses}
                    columns={columns}
                    pagination
                    page={page}
                    pageSize={pageSize}
                    rowCount={total}
                    paginationMode="server"
                    onPageChange={(newPage) => setPage(newPage)}
                    onPageSizeChange={(newPageSize) => setPageSize(newPageSize)}
                    loading={loading}
                    autoHeight
                    sx={{
                        background: '#162036',
                        color: '#fff',
                        borderColor: '#22315f',
                        '& .MuiDataGrid-cell': { color: '#fff' },
                        '& .MuiDataGrid-columnHeaders': { background: '#22315f' },
                    }}
                />
            </Paper>
            <Snackbar
                open={snackbar.open}
                autoHideDuration={2500}
                onClose={() => setSnackbar({ ...snackbar, open: false })}
                message={snackbar.message}
            />
        </Box>
    );
};

export default Expenses;
