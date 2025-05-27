import api from './axios';

// Fetch a breakdown of expenses by category
export const getExpensesByCategory = async () => {
    try {
        const { data } = await api.get('/analysis/category-totals');
        // Transform object to array
        if (data && typeof data === 'object' && !Array.isArray(data)) {
            return Object.entries(data).map(([category, total]) => ({
                category,
                total,
            }));
        }
        return Array.isArray(data) ? data : [];
    } catch (e) {
        console.error("Error fetching category totals", e);
        return [];
    }
};

export const getExpensesOverTime = async () => {
    try {
        const { data } = await api.get('/analysis/monthly-totals');
        return Array.isArray(data) ? data : [];
    } catch (e) {
        console.error("Error fetching monthly totals", e);
        return [];
    }
};

export const getExpenses = async (page = 0, pageSize = 10) => {
    try {
        const { data } = await api.get('/expenses', {
            params: { page, pageSize },
        });
        return data && data.expenses ? data : { expenses: [], total: 0 };
    } catch (e) {
        console.error("Error fetching expenses", e);
        return { expenses: [], total: 0 };
    }
};

export const deleteExpense = async (id) => {
    try {
        await api.delete(`/expenses/${id}`);
        return true;
    } catch (e) {
        console.error("Failed to delete expense", e);
        return false;
    }
};
