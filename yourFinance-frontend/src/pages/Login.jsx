import React, { useState } from 'react';
import { Box, Paper, TextField, Button, Typography, Link } from '@mui/material';
import { useNavigate } from 'react-router-dom';
import { login } from '../api/auth';
import { useAuth } from '../context/AuthContext';

const Login = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');
    const { setUser, setToken } = useAuth();
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setError('');
        try {
            const res = await login(username, password);
            setToken(res.token);
            console.log('Token: ' + res.token);
            navigate('/');
        } catch {
            setError('Invalid username or password');
        }
    };

    return (
        <Box sx={{ minHeight: '100vh', display: 'flex', alignItems: 'center', justifyContent: 'center', background: '#0b1623' }}>
            <Paper sx={{ p: 4, width: 360, background: '#162036' }}>
                <Typography variant="h5" mb={2}>Sign In</Typography>
                <form onSubmit={handleSubmit}>
                    <TextField label="Username" fullWidth variant="filled" sx={{ mb: 2 }} value={username} onChange={e => setUsername(e.target.value)} required />
                    <TextField label="Password" type="password" fullWidth variant="filled" sx={{ mb: 2 }} value={password} onChange={e => setPassword(e.target.value)} required />
                    {error && <Typography color="error" mb={1}>{error}</Typography>}
                    <Button type="submit" variant="contained" fullWidth sx={{ mb: 2 }}>Login</Button>
                </form>
                <Typography variant="body2" align="center">
                    Don't have an account?{' '}
                    <Link href="/register" underline="hover">Register</Link>
                </Typography>
            </Paper>
        </Box>
    );
};

export default Login;
