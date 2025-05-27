import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Navbar from './components/Navbar';
import Dashboard from './pages/Dashboard';
import Expenses from './pages/Expenses';
import Login from './pages/Login';
import Register from './pages/Register';
import { AuthProvider } from './context/AuthContext';
import PrivateRoute from './components/PrivateRoute';
import AnalysisPage from "./pages/AnalysisPage.jsx";
import SetBudgetPage from "./pages/SetBudgetPage.jsx";

function App() {
    return (
        <AuthProvider>
            <Router>
                <Navbar />
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/dashboard" element={<PrivateRoute><Dashboard /></PrivateRoute>} />
                    <Route path="/expenses" element={<PrivateRoute><Expenses /></PrivateRoute>} />
                    <Route path="/analysis" element={<PrivateRoute><AnalysisPage /></PrivateRoute>} />
                    <Route path="/budgets" element={<PrivateRoute><SetBudgetPage /></PrivateRoute>} />
                    {/* Redirect root to dashboard! */}
                    <Route path="/" element={<Navigate to="/dashboard" replace />} />
                    {/* Catch-all: redirect to dashboard */}
                    <Route path="*" element={<Navigate to="/dashboard" replace />} />
                </Routes>
            </Router>
        </AuthProvider>
    );
}

export default App;
