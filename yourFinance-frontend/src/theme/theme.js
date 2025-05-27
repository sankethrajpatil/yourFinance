import { createTheme } from '@mui/material/styles';

const theme = createTheme({
    palette: {
        mode: 'dark',
        background: {
            default: '#0b1623',
            paper: '#162036',
        },
        primary: {
            main: '#3a4c7a',
            contrastText: '#fff',
        },
        secondary: {
            main: '#1e2a3a',
        },
        text: {
            primary: '#fff',
            secondary: '#8a99b7',
        },
    },
    typography: {
        fontFamily: "'Inter', sans-serif",
        h4: {
            fontWeight: 700,
        },
    },
});

export default theme;
