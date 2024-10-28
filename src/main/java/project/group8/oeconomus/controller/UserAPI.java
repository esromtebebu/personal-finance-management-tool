package project.group8.oeconomus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import project.group8.oeconomus.model.User;
import project.group8.oeconomus.model.Budget;
import project.group8.oeconomus.model.Transaction;
import project.group8.oeconomus.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/v0/user")
public class UserAPI {
    private final UserService userService;

    @Autowired
    public UserAPI(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        this.userService.signUp(user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/signin")
    public ResponseEntity<String> login(@RequestBody User newUser) {
        User user = this.userService.findByEmail(newUser.getEmail());
        if (user == null) {
            return new ResponseEntity<String>("User with email " + newUser.getEmail() + " not found", HttpStatus.NOT_FOUND);
        }
        this.userService.signIn(newUser.getEmail(), newUser.getPassword(), newUser.getUserID());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/{userID}")
    public ResponseEntity<User> getUser(@PathVariable("userID") String userID) {
        User user = this.userService.findByUserID(userID);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/{userID}")
    public ResponseEntity<String> updateUser(@PathVariable("userID") String userID, @RequestBody User newUser) {
        User user = this.userService.findByUserID(userID);
        if (user == null) {
            return new ResponseEntity<String>("User with ID " + userID + " not found", HttpStatus.NOT_FOUND);
        }
        newUser.setUserID(userID);
        this.userService.updateUser(userID, newUser);;
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userID}")
    public ResponseEntity<User> deleteUser(@PathVariable("userID") String userID) {
        this.userService.removeUser(userID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


    @GetMapping("/{userID}/expenses")
    public ResponseEntity<List<Transaction>> getUserExpenses(@PathVariable("userID") String userID) {
        List<Transaction> expenses = this.userService.findByUserID(userID).getExpenses();
        return new ResponseEntity<>(expenses, HttpStatus.OK);
    }

    @PostMapping("/{userID}/expenses")
    public ResponseEntity<User> addUserExpense(@PathVariable("userID") String userID, @RequestBody Transaction newExpense) {
        this.userService.addExpense(userID, newExpense);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{userID}/expenses/{transactionID}/update")
    public ResponseEntity<String> updateUserExpenses(@PathVariable("userID") String userID, @PathVariable("transactionID") String transactionID, @RequestBody Transaction newExpense) {
        User user = this.userService.findByUserID(userID);
        if (user == null) {
            return new ResponseEntity<String>("User with ID " + userID + " not found", HttpStatus.NOT_FOUND);
        }
        newExpense.setTransactionID(transactionID);
        this.userService.updateExpense(userID, newExpense);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userID}/expenses/{transactionID}/delete")
    public ResponseEntity<Void> deleteUserExpense(@PathVariable("userID") String userID, @PathVariable("transactionID") String transactionID) {
        this.userService.removeExpense(userID, transactionID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{userID}/incomes")
    public ResponseEntity<List<Transaction>> getUserIncomes(@PathVariable("userID") String userID) {
        List<Transaction> incomes = this.userService.findByUserID(userID).getIncomes();
        return new ResponseEntity<>(incomes, HttpStatus.OK);
    }

    @PostMapping("/{userID}/incomes")
    public ResponseEntity<User> addUserIncome(@PathVariable("userID") String userID, @RequestBody Transaction newIncome) {
        this.userService.addIncome(userID, newIncome);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{userID}/incomes/{transactionID}/update")
    public ResponseEntity<String> updateUserIncomes(@PathVariable("userID") String userID, @PathVariable("transactionID") String transactionID, @RequestBody Transaction newIncome) {
        User user = this.userService.findByUserID(userID);
        if (user == null) {
            return new ResponseEntity<String>("User with ID " + userID + " not found", HttpStatus.NOT_FOUND);
        }
        newIncome.setTransactionID(transactionID);
        this.userService.updateIncome(userID, newIncome);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{userID}/incomes/{transactionID}/delete")
    public ResponseEntity<Void> deleteUserIncome(@PathVariable("userID") String userID, @PathVariable("transactionID") String transactionID) {
        this.userService.removeIncome(userID, transactionID);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/{userID}/budgetGoal")
    public ResponseEntity<String> newBudgetGoal(@PathVariable("userID") String userID, @RequestBody Budget newBudgetGoal) {
        User user = this.userService.findByUserID(userID);
        if (user == null) {
            return new ResponseEntity<String>("User with ID " + userID + " not found", HttpStatus.NOT_FOUND);
        }
        this.userService.updateBudgetGoal(userID, newBudgetGoal);;
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PutMapping("/{userID}/savingsGoal")
    public ResponseEntity<String> newSavingsGoal(@PathVariable("userID") String userID, @RequestBody Budget newSavingsGoal) {
        User user = this.userService.findByUserID(userID);
        if (user == null) {
            return new ResponseEntity<String>("User with ID " + userID + " not found", HttpStatus.NOT_FOUND);
        }
        this.userService.updateSavingsGoal(userID, newSavingsGoal);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = this.userService.findAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
