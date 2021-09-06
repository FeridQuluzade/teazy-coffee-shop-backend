package com.sale.teazy.mapper;
import com.sale.teazy.domain.Expense;
import com.sale.teazy.dto.ExpenseRequestDto;
import com.sale.teazy.dto.ExpenseResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = "spring")
public interface ExpenseMapper {
    ExpenseResponseDto  toExpenseResponseDto(Expense expense);

    Expense toExpenseEntity(ExpenseRequestDto expenseRequestDto);

    List<ExpenseResponseDto> toExpenseDtoList(List<Expense> expenseList);

}
