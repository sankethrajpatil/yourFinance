import React, { useEffect, useState } from "react";
import api from "../api/axios";

const CATEGORY_OPTIONS = [
    "FOOD",
    "RENT",
    "UTILITIES",
    "TRANSPORT",
    "ENTERTAINMENT",
    "HEALTH",
    "OTHER",
    // Add/remove categories as per your backend's Category enum
];

function SetBudgetPage() {
    const [budgets, setBudgets] = useState([]);
    const [category, setCategory] = useState("");
    const [amount, setAmount] = useState("");
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");

    // Fetch existing budgets on mount
    useEffect(() => {
        fetchBudgets();
    }, []);

    const fetchBudgets = async () => {
        setLoading(true);
        try {
            const res = await api.get("/budgets");
            setBudgets(res.data);
            setError("");
        } catch (err) {
            setError("Failed to fetch budgets.");
        } finally {
            setLoading(false);
        }
    };

    const handleSetBudget = async (e) => {
        e.preventDefault();
        if (!category || !amount) {
            setError("Please select a category and enter an amount.");
            return;
        }
        setLoading(true);
        try {
            await api.post("/budgets", {
                category,
                amount: parseFloat(amount),
            });
            setCategory("");
            setAmount("");
            await fetchBudgets();
            setError("");
        } catch (err) {
            setError("Failed to set budget.");
        } finally {
            setLoading(false);
        }
    };

    const handleDelete = async (category) => {
        setLoading(true);
        try {
            await api.delete(`/budgets/${category}`);
            await fetchBudgets();
        } catch (err) {
            setError("Failed to delete budget.");
        } finally {
            setLoading(false);
        }
    };

    return (
        <div style={{ maxWidth: 500, margin: "2rem auto" }}>
            <h2>Set Budget</h2>
            <form onSubmit={handleSetBudget} style={{ marginBottom: "2rem" }}>
                <div>
                    <label>
                        Category:{" "}
                        <select
                            value={category}
                            onChange={(e) => setCategory(e.target.value)}
                            required
                        >
                            <option value="">Select category</option>
                            {CATEGORY_OPTIONS.map((cat) => (
                                <option key={cat} value={cat}>
                                    {cat.charAt(0) + cat.slice(1).toLowerCase()}
                                </option>
                            ))}
                        </select>
                    </label>
                </div>
                <div style={{ marginTop: "1rem" }}>
                    <label>
                        Amount:{" "}
                        <input
                            type="number"
                            min="1"
                            step="any"
                            value={amount}
                            onChange={(e) => setAmount(e.target.value)}
                            required
                        />
                    </label>
                </div>
                <button type="submit" disabled={loading} style={{ marginTop: "1rem" }}>
                    {loading ? "Saving..." : "Set Budget"}
                </button>
                {error && <div style={{ color: "red", marginTop: "1rem" }}>{error}</div>}
            </form>
            <h3>Current Budgets</h3>
            {loading ? (
                <div>Loading...</div>
            ) : budgets.length === 0 ? (
                <div>No budgets set.</div>
            ) : (
                <table style={{ width: "100%", borderCollapse: "collapse" }}>
                    <thead>
                    <tr>
                        <th style={{ textAlign: "left" }}>Category</th>
                        <th style={{ textAlign: "left" }}>Amount</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    {budgets.map((b) => (
                        <tr key={b.category}>
                            <td>{b.category.charAt(0) + b.category.slice(1).toLowerCase()}</td>
                            <td>{b.amount}</td>
                            <td>
                                <button
                                    onClick={() => handleDelete(b.category)}
                                    disabled={loading}
                                >
                                    Delete
                                </button>
                            </td>
                        </tr>
                    ))}
                    </tbody>
                </table>
            )}
        </div>
    );
}

export default SetBudgetPage;
