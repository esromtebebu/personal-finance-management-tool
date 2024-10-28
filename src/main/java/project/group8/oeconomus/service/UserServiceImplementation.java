package project.group8.oeconomus.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.group8.oeconomus.model.Budget;
import project.group8.oeconomus.model.Transaction;
import project.group8.oeconomus.model.User;
import project.group8.oeconomus.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {

    private UserRepository userRepository;
    
    @Autowired
    UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserServiceImplementation() {
        for (int i = 1; i <= 4; i++) {
            this.userRepository.save(new User("dummy" + i + "@gmail.com", "1234"));
        }
    }


    public List<User> getUsers() { 
        return this.userRepository.findAll();
    }
    
    public void setUsers(List<User> users) { 
        this.userRepository.saveAll(users); 
    }

    public void removeExpense(String userID, String expenseID) {
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                Iterator<Transaction> expenseIterator = user.getExpenses().iterator();
                while (expenseIterator.hasNext()) {
                    Transaction currentExpense = expenseIterator.next();
                    if (currentExpense.getTransactionID().equals(expenseID)) {
                        expenseIterator.remove();
                        this.userRepository.save(user);
                        break;
                    }
                }
            }
        }
    }

    public void removeIncome(String userID, String incomeID) {
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                Iterator<Transaction> incomeIterator = user.getIncomes().iterator();
                while (incomeIterator.hasNext()) {
                    Transaction currentIncome = incomeIterator.next();
                    if (currentIncome.getTransactionID().equals(incomeID)) {
                        incomeIterator.remove();
                        this.userRepository.save(user);
                        break;
                    }
                }
            }
        }
    }

    public void updateExpense(String userID, Transaction expense) {
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                Iterator<Transaction> expenseIterator = user.getExpenses().iterator();
                while (expenseIterator.hasNext()) {
                    Transaction currentExpense = expenseIterator.next();
                    if (currentExpense.getTransactionID().equals(expense.getTransactionID())) {
                        expenseIterator.remove();
                        user.getExpenses().add(expense);
                        this.userRepository.save(user);
                        break;
                    }
                }
            }
        }
    }

    public void updateIncome(String userID, Transaction income) {
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getUserID().equals(userID)) {
                Iterator<Transaction> incomeIterator = user.getIncomes().iterator();
                while (incomeIterator.hasNext()) {
                    Transaction currentincome = incomeIterator.next();
                    if (currentincome.getTransactionID().equals(income.getTransactionID())) {
                        incomeIterator.remove();
                        user.getIncomes().add(income);
                        this.userRepository.save(user);
                        break;
                    }
                }
            }
        }
    }

    public void addExpense(String userID, Transaction expense) {
        List<User> users = this.userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(userID)) {
                users.get(i).getExpenses().add(expense);
                this.userRepository.save(users.get(i));
            }
        }
    }

    @Override
    public void updateBudgetGoal(String userID, Budget budgetGoal) {
        List<User> users = this.userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(userID)) {
                users.get(i).setBudgetGoal(budgetGoal);
                this.userRepository.save(users.get(i));
            }
        }
    }

    @Override
    public void updateSavingsGoal(String userID, Budget savingsGoal) {
        List<User> users = this.userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(userID)) {
                users.get(i).setSavingsGoal(savingsGoal);
                this.userRepository.save(users.get(i));
            }
        }
    }

    @Override
    public void updateUser(String userID, User newUser) {
        List<User> users = this.userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(userID)) {
                users.set(i, newUser);
                this.userRepository.save(users.get(i));
            }
        }
    }

    @Override
    public void removeUser(String userID) {
        List<User> users = this.userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(userID)) {
                this.userRepository.delete(users.get(i));;
            }
        } 
    }

    public void addIncome(String userID, Transaction income) {
        List<User> users = this.userRepository.findAll();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUserID().equals(userID)) {
                users.get(i).getIncomes().add(income);
                this.userRepository.save(users.get(i));
            }
        }
    }

    public User findByEmail(String email) {
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email)) return user;
        }
        throw new IllegalArgumentException(email + " not found.");
    }

    public User findByUserID(String userID) {
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getUserID().equals(userID)) return user;
        }
        throw new IllegalArgumentException(userID + " not found.");
    }

    public void signIn(String email, String password, String userID) {
        List<User> users = this.userRepository.findAll();
        for (User user : users) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                String oldUserID = user.getUserID();
                user.setUserID(userID);
                this.userRepository.save(user);
            }
        }
        // throw new IllegalArgumentException(password + " or " + email + " is wrong.");
    }

    public void signUp(User user) {
        this.userRepository.save(user);
    }

    public List<User> findAllUsers() {
        return this.userRepository.findAll();
    }
}
