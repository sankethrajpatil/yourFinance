import { PieChart, Pie, Cell, Tooltip, Legend, ResponsiveContainer } from 'recharts';
import { Box, Typography } from '@mui/material';

const COLORS = [
    "#7f8cfa", "#54d2a7", "#ffe066", "#ff6f61", "#70a1ff", "#b8e994", "#ffb8b8"
];

export default function CategoryPieChart({ data }) {
    return (
        <Box sx={{ width: '100%', height: 320 }}>
            <ResponsiveContainer width="100%" height="100%">
                <PieChart>
                    <Pie
                        data={data}
                        dataKey="total"
                        nameKey="category"
                        cx="50%"
                        cy="50%"
                        outerRadius={90}
                        fill="#8884d8"
                        label={({ name, percent }) => `${name}: ${(percent * 100).toFixed(0)}%`}
                        labelLine={false}
                    >
                        {data.map((entry, i) => (
                            <Cell key={`cell-${i}`} fill={COLORS[i % COLORS.length]} />
                        ))}
                    </Pie>
                    <Tooltip
                        contentStyle={{ background: "#222946", color: "#fff", border: "none" }}
                        formatter={(value, name) => [value, name]}
                    />
                    <Legend
                        layout="horizontal"
                        verticalAlign="bottom"
                        iconType="circle"
                        align="center"
                        wrapperStyle={{ marginTop: 18 }}
                    />
                </PieChart>
            </ResponsiveContainer>
        </Box>
    );
}
