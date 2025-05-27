import api from './axios';

export const register = async ({ username, password, monthlyExpenditure, memberOfFamilies }) => {
    const { data } = await api.post('/auth/register', {
        username,
        password,
        monthlyExpenditure,
        memberOfFamilies,
    });
    return data;
};

export const login = async (username, password) => {
    const { data } = await api.post('/auth/login', { username, password });
    return data;
};
