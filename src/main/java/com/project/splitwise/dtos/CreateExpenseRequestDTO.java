package com.project.splitwise.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class CreateExpenseRequestDTO {

    int userId;

    double amount;

    String description;

    int groupID;

    Map<Integer,Double> expenses;
}
