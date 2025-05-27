import React from 'react';
import AppBar from '@mui/material/AppBar';
import Toolbar from '@mui/material/Toolbar';
import Typography from '@mui/material/Typography';
import Button from '@mui/material/Button';
import Box from '@mui/material/Box';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { useAuth } from '../context/AuthContext';

const Navbar = () => {
    const location = useLocation();
    const { token, logout } = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };

    // Hide navbar on login/register pages
    if (location.pathname === '/login' || location.pathname === '/register') return null;

    return (
        <AppBar position="static" color="transparent" elevation={0}>
            <Toolbar sx={{ justifyContent: 'space-between', px: 4 }}>
                <Typography variant="h5" sx={{ fontWeight: 700 }}>
                    yourFinance
                </Typography>
                {token && (
                    <Box>
                        <Button
                            color={location.pathname === '/' ? 'primary' : 'inherit'}
                            component={Link}
                            to="/"
                        >Dashboard</Button>
                        <Button
                            color={location.pathname === '/expenses' ? 'primary' : 'inherit'}
                            component={Link}
                            to="/expenses"
                        >Expenses</Button>
                        <Button
                            color={location.pathname === '/analysis' ? 'primary' : 'inherit'}
                            component={Link}
                            to="/analysis"
                        >Analysis</Button>
                        <Button
                            color={location.pathname === '/budgets' ? 'primary' : 'inherit'}
                            component={Link}
                            to="/budgets"
                        >Budget</Button>
                        <Button color="inherit">About Us</Button>
                        <Button color="inherit" onClick={handleLogout}>Logout</Button>
                    </Box>
                )}
            </Toolbar>
        </AppBar>
    );
};

export default Navbar;
