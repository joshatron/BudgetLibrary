package io.joshatron.budgetlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column(unique = true, nullable = false)
    private String name;
    @Column(nullable = false)
    private String bank;
    @Column
    private String description;

    public boolean isValid() {
        return name != null && !name.isEmpty() && bank != null && !bank.isEmpty() && (description == null || !description.isEmpty());
    }
}
