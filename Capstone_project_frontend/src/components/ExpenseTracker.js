import React, { useState, useEffect } from 'react';
import axios from 'axios';

const ExpenseTracker = () => {
    const [expenses, setExpenses] = useState([]);
    const [expense, setExpense] = useState({ amount: '', description: '', date: '' });

    useEffect(() => {
        axios.get('http://localhost:8080/expenses')
            .then(response => setExpenses(response.data))
            .catch(error => console.error(error));
    }, []);

    const handleChange = (e) => {
        setExpense({ ...expense, [e.target.name]: e.target.value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        axios.post('http://localhost:8080/expenses', expense)
            .then(() => {
                setExpense({ amount: '', description: '', date: '' });
                // Update the expenses list
                axios.get('http://localhost:8080/expenses')
                    .then(response => setExpenses(response.data));
            })
            .catch(error => console.error(error));
    };

    const handleDelete = (id) => {
        axios.delete(`http://localhost:8080/expenses/${id}`)
            .then(() => {
                // Update the expenses list
                axios.get('http://localhost:8080/expenses')
                    .then(response => setExpenses(response.data));
            })
            .catch(error => console.error(error));
    };

    return (
        <div>
            <form onSubmit={handleSubmit}>
                <input type="number" name="amount" value={expense.amount} onChange={handleChange} />
                <input type="text" name="description" value={expense.description} onChange={handleChange} />
                <input type="date" name="date" value={expense.date} onChange={handleChange} />
                <button type="submit">Add Expense</button>
            </form>

            <table>
                <thead>
                    <tr>
                        <th>Amount</th>
                        <th>Description</th>
                        <th>Date</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    {expenses.map(exp => (
                        <tr key={exp.id}>
                            <td>{exp.amount}</td>
                            <td>{exp.description}</td>
                            <td>{exp.date}</td>
                            <td><button onClick={() => handleDelete(exp.id)}>Delete</button></td>
                        </tr>
                    ))}
                </tbody>
            </table>
        </div>
    );
};

export default ExpenseTracker;
