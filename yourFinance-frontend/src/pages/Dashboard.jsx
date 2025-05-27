import React, { useEffect, useState } from 'react';
import { Grid, Paper, Box, Typography } from '@mui/material';
import AddExpenseForm from '../components/AddExpenseForm';
import { getDashboardStats } from '../api/stats';
import { getExpensesByCategory, getExpensesOverTime } from '../api/expenses';
import CategoryPieChart from '../components/CategoryPieChart';
import ExpensesLineChart from '../components/ExpensesLineChart';

// Stats Card Component
const StatCard = ({ label, value, color }) => (
    <Paper
        elevation={3}
        sx={{
            p: 2,
            background: "#1b2330",
            color: color || "white",
            borderRadius: 2,
            boxShadow: "0 2px 8px rgba(0,0,0,0.2)",
            textAlign: "center",
            minHeight: 90,
            display: "flex",
            alignItems: "center",
            justifyContent: "center",
            flexDirection: "column"
        }}
    >
        <Typography variant="subtitle2" sx={{ opacity: 0.9 }}>{label}</Typography>
        <Typography variant="h6" sx={{ color: color || "white", mt: 1 }}>
            {value}
        </Typography>
    </Paper>
);

const Dashboard = () => {
    // Stats
    const [stats, setStats] = useState(null);
    const [statsLoading, setStatsLoading] = useState(true);
    const [statsError, setStatsError] = useState(null);

    // Category Chart
    const [categoryData, setCategoryData] = useState([]);
    const [categoryLoading, setCategoryLoading] = useState(true);
    const [categoryError, setCategoryError] = useState(null);

    // Time Chart
    const [timeData, setTimeData] = useState([]);
    const [timeLoading, setTimeLoading] = useState(true);
    const [timeError, setTimeError] = useState(null);

    // Fetch stats
    const fetchStats = async () => {
        setStatsLoading(true);
        setStatsError(null);
        try {
            const data = await getDashboardStats();
            setStats(data);
        } catch (e) {
            setStatsError('Could not load stats');
            setStats(null);
        } finally {
            setStatsLoading(false);
        }
    };

    // Fetch charts
    const fetchCharts = async () => {
        setCategoryLoading(true);
        setCategoryError(null);
        setTimeLoading(true);
        setTimeError(null);
        try {
            const category = await getExpensesByCategory();
            setCategoryData(category);
        } catch (e) {
            setCategoryError('Could not load category data');
            setCategoryData([]);
        } finally {
            setCategoryLoading(false);
        }

        try {
            const time = await getExpensesOverTime();
            setTimeData(time);
        } catch (e) {
            setTimeError('Could not load time data');
            setTimeData([]);
        } finally {
            setTimeLoading(false);
        }
    };

    useEffect(() => {
        fetchStats();
        fetchCharts();
        // eslint-disable-next-line
    }, []);

    useEffect(() => {
        console.log("Category Data:", categoryData);
        console.log("Time Data:", timeData);
    }, [categoryData, timeData]);

    // Stat cards display
    const statItems = [
        {
            label: 'Monthly Savings',
            value: stats?.monthlySavings !== undefined ? stats.monthlySavings : 'N/A',
            color: 'white'
        },
        {
            label: 'Expenditure',
            value: stats?.expenditure !== undefined ? stats.expenditure : 'N/A',
            color: 'white'
        },
        {
            label: 'Weekly Avg',
            value: stats?.weeklyAverage !== undefined ? stats.weeklyAverage : 'N/A',
            color: 'white'
        },
        {
            label: 'Monthly Avg',
            value: stats?.monthlyAverage !== undefined ? stats.monthlyAverage : 'N/A',
            color: 'white'
        }
    ];

    return (
        <Box sx={{ p: 4, background: '#0b1623', minHeight: '100vh' }}>
            <Grid columns={12} columnSpacing={3} rowSpacing={3}>
                {/* Add Expense Card */}
                <Grid gridColumn="span 12" md={4} lg={4}>
                    <Paper sx={{ p: 3, background: '#162036' }}>
                        <AddExpenseForm onAdd={() => { fetchStats(); fetchCharts(); }} />
                    </Paper>
                </Grid>
                {/* Stats Grid */}
                <Grid gridColumn="span 12" md={8} lg={8}>
                    <Grid columns={12} columnSpacing={3} rowSpacing={3}>
                        {/* Stats Cards */}
                        <Grid gridColumn="span 12">
                            <Box sx={{ mb: 2 }}>
                                {statsLoading ? (
                                    <Typography color="info.main">Loading stats...</Typography>
                                ) : statsError ? (
                                    <Typography color="error">{statsError}</Typography>
                                ) : stats ? (
                                    <Grid container spacing={3}>
                                        {statItems.map((item, idx) => (
                                            <Grid item xs={12} sm={6} md={3} key={item.label}>
                                                <StatCard label={item.label} value={item.value} color={item.color} />
                                            </Grid>
                                        ))}
                                    </Grid>
                                ) : null}
                            </Box>
                        </Grid>
                        {/* Pie Chart */}
                        <Grid gridColumn="span 6">
                            <Paper sx={{
                                p: 3,
                                background: '#162036',
                                height: 350,
                                display: 'flex',
                                alignItems: 'center',
                                justifyContent: 'center',
                                minHeight: 320,
                                width: '100%',
                                boxShadow: 3
                            }}>
                                {categoryLoading ? (
                                    <Typography color="info.main">Loading category chart...</Typography>
                                ) : categoryError ? (
                                    <Typography color="error">{categoryError}</Typography>
                                ) : (
                                    <CategoryPieChart data={categoryData} />
                                )}
                            </Paper>
                        </Grid>
                        {/* Line Chart */}
                        <Grid gridColumn="span 6">
                            <Paper sx={{
                                p: 3,
                                background: '#162036',
                                height: 350,
                                display: 'flex',
                                flexDirection: 'column',
                                alignItems: 'center',
                                justifyContent: 'center',
                                minHeight: 320,
                                boxShadow: 3
                            }}>
                                {timeLoading ? (
                                    <Typography color="info.main">Loading time chart...</Typography>
                                ) : timeError ? (
                                    <Typography color="error">{timeError}</Typography>
                                ) : (
                                    <ExpensesLineChart data={timeData} />
                                )}
                                <Typography variant="subtitle2" sx={{ mt: 2, color: "#fff", opacity: 0.7 }}>See more</Typography>
                            </Paper>
                        </Grid>
                    </Grid>
                </Grid>
            </Grid>
        </Box>
    );
};

export default Dashboard;
