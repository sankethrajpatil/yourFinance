import React, { useEffect, useState } from "react";
import api from "../api/axios";

const API_URL = "http://localhost:8080/api/analysis"; // adjust if needed

function AnalysisPage() {
    const [categoryTotals, setCategoryTotals] = useState({});
    const [monthlyTotals, setMonthlyTotals] = useState({});
    const [start, setStart] = useState("");
    const [end, setEnd] = useState("");
    const [monthsBack, setMonthsBack] = useState(6);

    useEffect(() => {
        fetchCategoryTotals();
        fetchMonthlyTotals();
        // eslint-disable-next-line
    }, []);

    const fetchCategoryTotals = async () => {
        const params = {};
        if (start) params.start = start;
        if (end) params.end = end;
        const res = await api.get(`/analysis/category-totals`, { params });
        setCategoryTotals(res.data);
    };

    const fetchMonthlyTotals = async () => {
        const res = await api.get(`/analysis/monthly-totals`, {
            params: { monthsBack: 6 },
        });
        setMonthlyTotals(res.data);
    };

    const handleFilter = (e) => {
        e.preventDefault();
        fetchCategoryTotals();
        fetchMonthlyTotals();
    };

    return (
        <div style={{ padding: "2rem" }}>
            <h2>Expense Analysis</h2>
            <form onSubmit={handleFilter} style={{ marginBottom: "1rem" }}>
                <label>
                    Start Date:{" "}
                    <input
                        type="datetime-local"
                        value={start}
                        onChange={(e) => setStart(e.target.value)}
                    />
                </label>
                <label style={{ marginLeft: 10 }}>
                    End Date:{" "}
                    <input
                        type="datetime-local"
                        value={end}
                        onChange={(e) => setEnd(e.target.value)}
                    />
                </label>
                <label style={{ marginLeft: 10 }}>
                    Months Back:{" "}
                    <input
                        type="number"
                        min="1"
                        max="24"
                        value={monthsBack}
                        onChange={(e) => setMonthsBack(e.target.value)}
                    />
                </label>
                <button type="submit" style={{ marginLeft: 10 }}>
                    Filter
                </button>
            </form>

            <h3>Totals by Category</h3>
            <table border="1" cellPadding="5" style={{ marginBottom: "2rem" }}>
                <thead>
                <tr>
                    <th>Category</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                {Object.entries(categoryTotals).map(([cat, total]) => (
                    <tr key={cat}>
                        <td>{cat}</td>
                        <td>{total.toFixed(2)}</td>
                    </tr>
                ))}
                </tbody>
            </table>

            <h3>Monthly Totals</h3>
            <table border="1" cellPadding="5">
                <thead>
                <tr>
                    <th>Month</th>
                    <th>Total</th>
                </tr>
                </thead>
                <tbody>
                {Object.entries(monthlyTotals).map(([month, total]) => (
                    <tr key={month}>
                        <td>{month}</td>
                        <td>{total.toFixed(2)}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
}

export default AnalysisPage;
