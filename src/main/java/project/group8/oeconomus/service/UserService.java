package project.group8.oeconomus.service;
import java.util.List;

import project.group8.oeconomus.model.Budget;
import project.group8.oeconomus.model.Transaction;
import project.group8.oeconomus.model.User;

public interface UserService {

    void signUp(User user);
    User findByEmail(String email);
    User findByUserID(String userID);
    void signIn(String email, String password, String userID);
    void addExpense(String userID, Transaction expense);
    void removeExpense(String userID, String expenseID);
    void updateExpense(String userID, Transaction expense);
    void addIncome(String userID, Transaction income);
    void removeIncome(String userID, String incomeID);
    void updateIncome(String userID, Transaction income);
    void updateBudgetGoal(String userID, Budget budgetGoal);
    void updateSavingsGoal(String userID, Budget savingsGoal);
    void removeUser(String userID);
    void updateUser(String userID, User newUser);
    List<User> findAllUsers();
}
