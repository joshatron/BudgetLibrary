package io.joshatron.budgetlibrary.imports;

import io.joshatron.budgetlibrary.dtos.Money;
import io.joshatron.budgetlibrary.dtos.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransactionImport {
    private Timestamp timestamp;
    private Money amount;
    private String rawVendor;
}
