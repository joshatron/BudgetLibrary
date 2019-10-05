package io.joshatron.budgetlibrary.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class BudgetCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @ElementCollection
    private List<Type> types;
    @AttributeOverride(name = "amount", column = @Column(name = "goal"))
    @Embedded
    private Money goal;
    @AttributeOverride(name = "amount", column = @Column(name = "idealGoal"))
    @Embedded
    private Money idealGoal;
    @Column
    private LocalDate start;
    @Embedded
    private LocalDate end;
    //If true, goal set up for spending less than the goal
    //If false, goal set up for saving at least the goal
    @Column
    private boolean spendingGoal;
    @Enumerated
    private CategoryDuration duration;

    public BudgetCategory() {
        types = new ArrayList<>();
    }
}
