import React, { useState } from 'react';
import { Box, Paper, TextField, Button, Typography, Link } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { register } from '../api/auth';

const Register = () => {
    const [username, setUsername] = useState('');
    const [monthlyExpenditure, setMonthlyExpenditure] = useState('');
    const [memberOfFamilies, setMemberOfFamilies] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        setSuccess('');
        try {
            await register({
                username,
                password,
                monthlyExpenditure: Number(monthlyExpenditure),
                memberOfFamilies: Number(memberOfFamilies),
            });
            setSuccess('Registration successful! You can now login.');
            setTimeout(() => navigate('/login'), 1200);
        } catch (err) {
            setError('Registration failed. Please check your details or try a different username.');
        }
    };

    return (
        <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', background: '#0b1623' }}>
            <Paper sx={{ p: 4, width: 360, background: '#162036' }}>
                <Typography variant="h5" mb={2}>Register</Typography>
                <form onSubmit={handleSubmit}>
                    <TextField label="Username" fullWidth variant="filled" sx={{ mb: 2 }} value={username} onChange={e => setUsername(e.target.value)} required />
                    <TextField label="Password" type="password" fullWidth variant="filled" sx={{ mb: 2 }} value={password} onChange={e => setPassword(e.target.value)} required />
                    <TextField label="Monthly Expenditure" type="number" fullWidth variant="filled" sx={{ mb: 2 }} value={monthlyExpenditure} onChange={e => setMonthlyExpenditure(e.target.value)} required />
                    <TextField label="Member of Families" type="number" fullWidth variant="filled" sx={{ mb: 2 }} value={memberOfFamilies} onChange={e => setMemberOfFamilies(e.target.value)} required />
                    {error && <Typography color="error" mb={1}>{error}</Typography>}
                    {success && <Typography color="success.main" mb={1}>{success}</Typography>}
                    <Button type="submit" variant="contained" fullWidth sx={{ mb: 2 }}>Register</Button>
                </form>
                <Typography variant="body2" align="center">
                    Already have an account?{' '}
                    <Link href="/login" underline="hover">Login</Link>
                </Typography>
            </Paper>
        </Box>
    );
};

export default Register;
