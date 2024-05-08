package com.project.splitwise.services;


import com.project.splitwise.models.*;
import com.project.splitwise.repositories.ExpenseRepository;
import com.project.splitwise.repositories.GroupRepository;
import com.project.splitwise.repositories.UserExpenseRepository;
import com.project.splitwise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GroupService {

    UserRepository userRepository;

    UserExpenseRepository userExpenseRepository;
    ExpenseRepository expenseRepository;
    GroupRepository groupRepository;
    @Autowired
    public GroupService(UserRepository userRepository,GroupRepository groupRepository, ExpenseRepository expenseRepository, UserExpenseRepository userExpenseRepository) {
        this.groupRepository = groupRepository;
        this.userRepository = userRepository;
        this.expenseRepository = expenseRepository;
        this.userExpenseRepository = userExpenseRepository;
    }

    public Group createGroup(int userID, String groupName) {

        User user;
        Optional<User> optionalUser= userRepository.findById(userID);
        if(optionalUser.isPresent()) {
            user = optionalUser.get();
        }
        else
            throw new IllegalArgumentException("User not found");

        Group group = new Group();
        group.setName(groupName);
        group.setAdmin(user);
        group.getUsers().add(user);
        user.addGroup(group);
        userRepository.save(user);
        return groupRepository.save(group);
    }

    public Expense createExpense(int userID, int groupID, double amount, String description, Map<Integer,Double> expenses) {

        Expense expense = new Expense();
        User currentUser;
        Optional<User> optionalUser= userRepository.findById(userID);
        if(optionalUser.isPresent()) {
            currentUser = optionalUser.get();
        }
        else
            throw new IllegalArgumentException("User not found");
        Group group;

        Optional<Group> optionalGroup = groupRepository.findById(groupID);
        if(optionalGroup.isPresent()) {
            group = optionalGroup.get();
        }
        else
            throw new IllegalArgumentException("Group not found");

        List<UserExpense> userExpenses = new ArrayList<>();

        // expense of user who paid
        UserExpense userexpense = new UserExpense();
        userexpense.setUser(currentUser);
        userexpense.setAmount(amount);
        userexpense.setUserExpenseType(UserExpenseType.Paid_By);
        userexpense.setGroup(group);
        userExpenses.add(userexpense);


        // adding expenses of users who need to pay
        for(Map.Entry<Integer,Double> entry : expenses.entrySet()) {

            User expenseuser;
            Optional<User> optionalExpenseUser = userRepository.findById(entry.getKey());
            if(optionalExpenseUser.isPresent()) {
                expenseuser = optionalExpenseUser.get();
            }
            else
                throw new IllegalArgumentException("User not found");

            UserExpense usersExpenseToPay = new UserExpense();
            usersExpenseToPay.setUser(expenseuser);
            usersExpenseToPay.setAmount(entry.getValue());
            usersExpenseToPay.setUserExpenseType(UserExpenseType.To_Pay);
            usersExpenseToPay.setGroup(group);
            userExpenses.add(usersExpenseToPay);
        }


        expense.setGroup(group);
        expense.setDescription(description);
        expense.setExpenseType(ExpenseType.Real);
        expense.setTotalAmount(amount);
        expense.setCreated(LocalDateTime.now());
        expense.setUserExpenses(userExpenses);
        expenseRepository.save(expense);

        return expense;

    }

    public Expense settleExpenses(int groupId) {

        return null;
    }
}
