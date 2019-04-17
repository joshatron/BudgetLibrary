package io.joshatron.budgetlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Embedded
    private Timestamp timestamp;
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
        return "Date: " + timestamp.getTimestampString() + " Amount: " + amount.toString() + " Vendor: " + vendor.getName() + " Type: " + vendor.getType();
    }
}

