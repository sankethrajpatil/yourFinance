import api from './axios';

// Adjust endpoints/response parsing as needed to match your backend
export const getDashboardStats = async () => {
    const [
        { data: monthlySavings },
        { data: expenditure },
        { data: averages },
        { data: budgets }
    ] = await Promise.all([
        api.get('/analysis/monthly-savings'),
        api.get('/analysis/monthly-expenditure'),
        api.get('/analysis/averages'),
        api.get('/budgets'),
    ]);
    return {
        monthlySavings,
        expenditure,
        weeklyAverage: averages.weekly,
        monthlyAverage: averages.monthly,
        budgets, // Expected: { FOOD: ..., TRAVEL: ... }
    };
};
