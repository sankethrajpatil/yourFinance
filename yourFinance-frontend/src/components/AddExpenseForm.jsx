import React, { useState } from 'react';
import { Typography, TextField, Box, Button, MenuItem } from '@mui/material';
import api from '../api/axios';

const categories = [
    'FOOD',
    'TRAVEL',
    'ENTERTAINMENT',
    'UTILITIES',
    'OTHER',
    'TRANSPORT',
    'HEALTH'
];

const AddExpenseForm = ({ onAdd }) => {
    const [amount, setAmount] = useState('');
    const [description, setDescription] = useState('');
    const [category, setCategory] = useState('');
    const [loading, setLoading] = useState(false);

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            await api.post('/expenses', {
                amount: parseFloat(amount),
                description,
                category
            });
            setAmount('');
            setDescription('');
            setCategory('');
            if (onAdd) onAdd();
        } catch (err) {
            alert('Failed to add expense!');
        }
        setLoading(false);
    };

    return (
        <form onSubmit={handleSubmit}>
            <Typography variant="subtitle1" gutterBottom>
                Add Expense
            </Typography>
            <TextField
                fullWidth
                variant="filled"
                label="Amount"
                type="number"
                value={amount}
                onChange={e => setAmount(e.target.value)}
                sx={{ mb: 2 }}
                required
            />
            <Typography variant="subtitle1" gutterBottom>
                Description
            </Typography>
            <TextField
                fullWidth
                variant="filled"
                label="Description"
                value={description}
                onChange={e => setDescription(e.target.value)}
                sx={{ mb: 2 }}
                required
            />
            <TextField
                fullWidth
                select
                variant="filled"
                label="Category"
                value={category}
                onChange={e => setCategory(e.target.value)}
                sx={{ mb: 2 }}
                required
            >
                {categories.map(c => (
                    <MenuItem key={c} value={c}>{c}</MenuItem>
                ))}
            </TextField>
            <Box sx={{ display: 'flex', gap: 2 }}>
                <Button
                    variant="contained"
                    color="primary"
                    type="submit"
                    disabled={loading}
                >
                    Add
                </Button>
                <Button
                    variant="contained"
                    color="secondary"
                    disabled
                >
                    Upload
                </Button>
            </Box>
        </form>
    );
};

export default AddExpenseForm;
