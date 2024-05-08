package com.project.splitwise.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserExpense extends BaseModel {

    @ManyToOne
    User user;

    @Enumerated(EnumType.ORDINAL)
    UserExpenseType userExpenseType;

    double amount;

    @ManyToOne
    Expense expense;

    @ManyToOne
    Group group;

}
