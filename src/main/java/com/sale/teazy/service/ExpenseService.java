package com.sale.teazy.service;

import com.sale.teazy.dto.ExpenseRequestDto;
import com.sale.teazy.dto.ExpenseResponseDto;

public interface ExpenseService {

    ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto);

    ExpenseResponseDto updateExpense(ExpenseRequestDto expenseRequestDto, Long id);


}
