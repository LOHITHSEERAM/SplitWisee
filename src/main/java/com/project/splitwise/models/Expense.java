package com.project.splitwise.models;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;


@Getter
@Setter
@Entity
public class Expense extends BaseModel {

    String name;

    String description;

    double totalAmount;

    @ManyToOne
    Group group;

    @OneToMany(mappedBy="expense", fetch = FetchType.LAZY ,cascade = CascadeType.ALL)
    List<UserExpense> userExpenses;

    @Enumerated(EnumType.ORDINAL)
    ExpenseType expenseType;

   public Expense(){
       userExpenses = new ArrayList<>();
   }
}
