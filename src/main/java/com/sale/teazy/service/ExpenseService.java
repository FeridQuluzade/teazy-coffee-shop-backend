package com.sale.teazy.service;

import com.sale.teazy.dto.ExpenseRequestDto;
import com.sale.teazy.dto.ExpenseResponseDto;

import java.util.List;

public interface ExpenseService {

    ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto);

    ExpenseResponseDto updateExpense(ExpenseRequestDto expenseRequestDto, Long id);

    List<ExpenseResponseDto> findAllExpense();

    ExpenseResponseDto findExpenseById(Long id);

    ExpenseResponseDto deleteExpenseById(Long id);

}
