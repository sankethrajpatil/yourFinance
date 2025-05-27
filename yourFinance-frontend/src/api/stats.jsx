import api from './axios';

// Adjust endpoints/response parsing as needed to match your backend
export const getDashboardStats = async () => {
    let monthlySavings = null, expenditure = null, averages = {}, budgets = {};
    try {
        const results = await Promise.allSettled([
            api.get('/analysis/monthly-savings'),
            api.get('/analysis/monthly-expenditure'),
            api.get('/analysis/averages'),
            api.get('/budgets'),
        ]);
        if (results[0].status === "fulfilled") monthlySavings = results[0].value.data;
        if (results[1].status === "fulfilled") expenditure = results[1].value.data;
        if (results[2].status === "fulfilled") averages = results[2].value.data;
        if (results[3].status === "fulfilled") budgets = results[3].value.data;
    } catch (e) {
        // This catch is now only for Promise.allSettled itself (rare)
    }
    return { monthlySavings, expenditure, weeklyAverage: averages.weekly, monthlyAverage: averages.monthly, budgets };
};
