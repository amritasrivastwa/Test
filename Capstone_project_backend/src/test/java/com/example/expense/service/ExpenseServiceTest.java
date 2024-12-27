package com.example.expense.service;
import com.example.expense.Expense;
import com.example.expense.ExpenseRepository;
import com.example.expense.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository; // Mocking the repository

    @InjectMocks
    private ExpenseService expenseService; // Injecting the mock into the service

    private Expense expense;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalDate expenseDate = LocalDate.parse("2024-12-27");
        expense = new Expense(1L, 100.0, "Test expense", expenseDate);
    }

    @Test
    public void testAddExpense() {
        when(expenseRepository.save(expense)).thenReturn(expense);

        Expense createdExpense = expenseService.addExpense(expense);

        assertNotNull(createdExpense);
        assertEquals(expense.getAmount(), createdExpense.getAmount());
        assertEquals(expense.getDescription(), createdExpense.getDescription());

        verify(expenseRepository, times(1)).save(expense);
    }

    @Test
    public void testGetAllExpenses() {
        List<Expense> expenses = Arrays.asList(expense);
        when(expenseRepository.findAll()).thenReturn(expenses);

        // Call the service method
        List<Expense> expenseList = expenseService.getAllExpenses();

        // Verify the result
        assertNotNull(expenseList);
        assertFalse(expenseList.isEmpty());
        assertEquals(1, expenseList.size());
        assertEquals(expense.getAmount(), expenseList.get(0).getAmount());

        verify(expenseRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteExpense() {
        expenseService.deleteExpense(expense.getId());

        verify(expenseRepository, times(1)).deleteById(expense.getId());
    }
}
