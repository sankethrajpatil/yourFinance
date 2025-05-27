import React from 'react';
import { LineChart, Line, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from 'recharts';

const ExpensesLineChart = ({ data }) => (
    <ResponsiveContainer width="100%" height={80}>
        <LineChart data={data}>
            <CartesianGrid strokeDasharray="3 3" />
            <XAxis dataKey="month" />
            <YAxis />
            <Tooltip />
            <Line type="monotone" dataKey="total" stroke="#82ca9d" strokeWidth={2} />
        </LineChart>
    </ResponsiveContainer>
);

export default ExpensesLineChart;
