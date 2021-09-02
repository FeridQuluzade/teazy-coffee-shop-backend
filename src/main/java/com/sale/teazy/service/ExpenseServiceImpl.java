package com.sale.teazy.service;

import com.sale.teazy.domain.Expense;
import com.sale.teazy.dto.ExpenseRequestDto;
import com.sale.teazy.dto.ExpenseResponseDto;
import com.sale.teazy.exception.EntityNotFoundException;
import com.sale.teazy.mapper.ExpenseMapper;
import com.sale.teazy.repository.ExpenseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;

    public ExpenseServiceImpl(ExpenseRepository expenseRepository,
                              ExpenseMapper expenseMapper) {
        this.expenseRepository = expenseRepository;
        this.expenseMapper = expenseMapper;
    }

    protected Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Expense.class, id));
    }

    @Override
    public ExpenseResponseDto createExpense(ExpenseRequestDto expenseRequestDto) {
        return expenseMapper
                .toExpenseResponseDto(
                        expenseRepository.save(
                                expenseMapper.toExpenseEntity(expenseRequestDto)));
    }

    @Override
    public ExpenseResponseDto updateExpense(ExpenseRequestDto expenseRequestDto, Long id) {
        Expense expense = getExpenseById(id);
        expense.setTitle(expenseRequestDto.getTitle());
        return expenseMapper.toExpenseResponseDto(expenseRepository.save(expense));
    }

    @Override
    public List<ExpenseResponseDto> findAllExpense() {
        if (!expenseMapper.toExpenseDtoList(expenseRepository.findAll()).isEmpty()) {
            return expenseMapper.toExpenseDtoList(expenseRepository.findAll());
        } else throw new EntityNotFoundException(Expense.class);
    }

    @Override
    public ExpenseResponseDto findExpenseById(Long id) {
        return expenseMapper.toExpenseResponseDto(getExpenseById(id));
    }

    @Override
    public ExpenseResponseDto deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.deleteById(id);
        return expenseMapper.toExpenseResponseDto(expense);
    }

}
