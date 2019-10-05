package io.joshatron.budgetlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private LocalDate timestamp;
    @Embedded
    private Money amount;
    @ManyToOne
    private Vendor vendor;
    @ManyToOne
    private Account account;

    public boolean isValid() {
        return timestamp != null && amount != null && vendor != null && vendor.isValid() && account != null && account.isValid();
    }

    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        return "Date: " + formatter.format(timestamp) + " Amount: " + amount.toString() + " Vendor: " + vendor.getName() + " Type: " + vendor.getType();
    }
}

