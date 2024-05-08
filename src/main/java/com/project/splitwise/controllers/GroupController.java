package com.project.splitwise.controllers;

import com.project.splitwise.dtos.CreateExpenseRequestDTO;
import com.project.splitwise.dtos.CreateGroupRequestDTO;
import com.project.splitwise.models.Expense;
import com.project.splitwise.models.Group;
import com.project.splitwise.services.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/group")
public class GroupController {

    GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }


    @PostMapping("/create")
    public ResponseEntity<Group> createGroup(@RequestBody CreateGroupRequestDTO groupRequestDTO) throws Exception {

        ResponseEntity<Group> groupResponseEntity;
        Group group = groupService.createGroup(groupRequestDTO.getUserId(),groupRequestDTO.getName());
        groupResponseEntity = ResponseEntity.ok(group);
        return groupResponseEntity;
    }

    @GetMapping("/settleup")
    public ResponseEntity<Expense> settleUpGroup(@RequestParam int groupId) {

        try {
            Expense expense = groupService.settleExpenses(groupId);
            return ResponseEntity.ok(expense);
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }


    @PostMapping("/createexpense")
    public ResponseEntity createExpense(@RequestBody CreateExpenseRequestDTO createExpenseRequestDTO) {
        try {
           Expense expense =  groupService.createExpense(createExpenseRequestDTO.getUserId(),
                    createExpenseRequestDTO.getGroupID(),
                    createExpenseRequestDTO.getAmount(),
                    createExpenseRequestDTO.getDescription(),
                    createExpenseRequestDTO.getExpenses());
            return ResponseEntity.ok(expense);
        }
        catch(Exception e) {
            return new ResponseEntity(e, HttpStatus.BAD_REQUEST);
        }
    }
}
