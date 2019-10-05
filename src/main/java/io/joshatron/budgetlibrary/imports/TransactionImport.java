package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.dtos.Money;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class TransactionImport {
    private LocalDate timestamp;
    private Money amount;
    private String rawVendor;
}
