package io.joshatron.budgetlibrary.analysis;

import io.joshatron.budgetlibrary.dtos.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class TimeframeAnalysis {
    private List<Transaction> transactions;
    private Money totalIn;
    private Money totalOut;
    private Map<Vendor,Money> spendingPerVendor;
    private Map<Type,Money> spendingPerType;
    private Map<LocalDate,Money> spendingPerDay;
}
