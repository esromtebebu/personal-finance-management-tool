package project.group8.oeconomus.client;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import project.group8.oeconomus.model.Budget;
import project.group8.oeconomus.model.Transaction;
import project.group8.oeconomus.model.User;
import project.group8.oeconomus.service.UserService;

@Controller
public class UserController {
    private final RestTemplate restTemplate;
    private final String apiURL = "http://localhost:8080/api/v0/user/";

    @Autowired
    public UserController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/signup")
    public String createUser(User user) {  
        String URL = apiURL + "/signup";
        ResponseEntity<String> response = restTemplate.postForEntity(URL, user, String.class);
        System.out.println(response.getBody());
        return "redirect:/" + user.getUserID() + "/dashboard";
    }

    @GetMapping("/signin")
    public String signin(Model model) {
        model.addAttribute("user", new User());
        return "signin";
    }

    @PostMapping("/signin")
    public String login(User user) {  
        String URL = apiURL + "/signin";
        HttpEntity<User> req = new HttpEntity<>(user);
        ResponseEntity<String> resp = restTemplate.exchange(URL, HttpMethod.PUT, req, String.class);
        restTemplate.put(URL, user);
        if (resp.getStatusCode().is2xxSuccessful()) {
            System.out.println(resp);
        } else {
            System.out.println("Failed to signin: " + resp.getStatusCode());
        }
        System.out.println(resp);   
        return "redirect:/" + user.getUserID() + "/dashboard";
    }

    @GetMapping("/{userID}/dashboard")
    public String visualizeDashboard(Model model, @PathVariable("userID") String userID) {
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);
        return "dashboard";
    }

    @GetMapping("/{userID}/budgetgoal")
    public String getBudgetGoal(Model model, @PathVariable("userID") String userID) {
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);
        model.addAttribute("budget", user.getBudgetGoal());
        return "updateBudgetGoal";
    }

    @PostMapping("/{userID}/budgetgoal")
    public String setBudgetGoal(@PathVariable("userID") String userID, Budget newBudgetGoal) {
        String URL = apiURL + "/" + userID + "/budgetGoal";
        HttpEntity<Budget> req = new HttpEntity<>(newBudgetGoal);
        ResponseEntity<String> resp = restTemplate.exchange(URL, HttpMethod.PUT, req, String.class);
        restTemplate.put(URL, newBudgetGoal);
        if (resp.getStatusCode().is2xxSuccessful()) {
            System.out.println(resp);
        } else {
            System.out.println("Failed to update budgetGoal: " + resp.getStatusCode());
        }
        System.out.println(resp);   
        return "redirect:/" + userID + "/dashboard";
    }

    @GetMapping("/{userID}/savingsgoal")
    public String getSavingsGoal(Model model, @PathVariable("userID") String userID) {
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);
        model.addAttribute("budget", user.getSavingsGoal());
        return "updateSavingsGoal";
    }

    @PostMapping("/{userID}/savingsgoal")
    public String setSavingsGoal(@PathVariable("userID") String userID, Budget newSavingsGoal) {
        String URL = apiURL + "/" + userID + "/savingsGoal";
        HttpEntity<Budget> req = new HttpEntity<>(newSavingsGoal);
        ResponseEntity<String> resp = restTemplate.exchange(URL, HttpMethod.PUT, req, String.class);
        restTemplate.put(URL, newSavingsGoal);
        if (resp.getStatusCode().is2xxSuccessful()) {
            System.out.println(resp);
        } else {
            System.out.println("Failed to update savingsGoal: " + resp.getStatusCode());
        }
        System.out.println(resp);   
        return "redirect:/" + userID + "/dashboard";
    }

    @GetMapping("/{userID}/expenses")
    public String visualizeExpenses(Model model, @PathVariable("userID") String userID) { 
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);
        return "expenses";
    }

    public int getExpensesIndex(String userID, String transactionID) {  
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        for (int i = 0; i < user.getExpenses().size(); i++) {
            if (user.getExpenses().get(i).getTransactionID().equals(transactionID)) {
                return i;
            }
        }
        return -1;
    }

    @GetMapping("/{userID}/expenses/add")   
    public String addExpense(Model model, @PathVariable("userID") String userID) {
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);
        model.addAttribute("expense", new Transaction());
        return "addExpense";
    }

    @PostMapping("/{userID}/expenses/add")   
    public String createExpense(Model model, @PathVariable("userID") String userID, Transaction expense) {
        String URL = apiURL + userID + "/expenses";
        ResponseEntity<String> response = restTemplate.postForEntity(URL, expense, String.class);
        System.out.println(response.getBody());
        return "redirect:/" + userID + "/expenses";
    }

    @GetMapping("/{userID}/incomes/add")   
    public String addIncome(Model model, @PathVariable("userID") String userID) {
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);
        model.addAttribute("income", new Transaction());
        return "addIncome";
    }

    @PostMapping("/{userID}/incomes/add")   
    public String createIncome(Model model, @PathVariable("userID") String userID, Transaction income) {
        String URL = apiURL + userID + "/incomes";
        ResponseEntity<String> response = restTemplate.postForEntity(URL, income, String.class);
        System.out.println(response.getBody());
        return "redirect:/" + userID + "/incomes";
    }

    @GetMapping("/{userID}/expenses/{transactionID}/update")   
    public String visualizeExpenses(Model model, @PathVariable("userID") String userID, @PathVariable String transactionID) {
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        int index = getExpensesIndex(userID, transactionID);
        model.addAttribute("transaction", user.getExpenses().get(index)); 
        model.addAttribute("user", user); 
        return "updateExpense";
    }

    @PostMapping("/{userID}/expenses/{transactionID}/update") 
    public String updateUserExpenses(@PathVariable("userID") String userID, @PathVariable("transactionID") String transactionID, Transaction newExpense) {
        String URL = apiURL + "/" + userID + "/expenses/" + transactionID + "/update";
        System.out.println("----------" + newExpense);
        newExpense.setTransactionID(transactionID);
        HttpEntity<Transaction> req = new HttpEntity<>(newExpense);
        ResponseEntity<String> resp = restTemplate.exchange(URL, HttpMethod.PUT, req, String.class);
        restTemplate.put(URL, newExpense);
        if (resp.getStatusCode().is2xxSuccessful()) {
            System.out.println(resp);
        } else {
            System.out.println("Failed to update expense: " + resp.getStatusCode());
        }
        System.out.println(resp);   
        return "redirect:/" + userID + "/expenses";
    }

    @PostMapping("/{userID}/expenses/{transactionID}/delete")
    public String deleteUserExpenses(@PathVariable("userID") String userID, @PathVariable("transactionID") String transactionID) {
        String URL = apiURL + "/" + userID + "/expenses/" + transactionID + "/delete";
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.DELETE, null, String.class);
        // restTemplate.delete(URL);
        System.out.println(response);
        return "redirect:/" + userID + "/expenses";
    }

    @GetMapping("/{userID}/incomes")
    public String visualizeIncomes(Model model, @PathVariable("userID") String userID) { 
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        model.addAttribute("user", user);
        return "incomes";
    }

    public int getIncomesIndex(String userID, String transactionID) {  
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        for (int i = 0; i < user.getIncomes().size(); i++) {
            if (user.getIncomes().get(i).getTransactionID().equals(transactionID)) {
                return i;
            }
        }
        return -1;
    }

    @GetMapping("/{userID}/incomes/{transactionID}/update")   
    public String visualizeIncomes(Model model, @PathVariable("userID") String userID, @PathVariable String transactionID) {
        String URL = apiURL + "/" + userID;
        ResponseEntity<User> response = restTemplate.getForEntity(URL, User.class);
        User user = response.getBody();
        int index = getIncomesIndex(userID, transactionID);
        model.addAttribute("transaction", user.getIncomes().get(index)); 
        model.addAttribute("user", user); 
        return "updateIncome";
    }

    @PostMapping("/{userID}/incomes/{transactionID}/update") 
    public String updateUserIncomes(@PathVariable("userID") String userID, @PathVariable("transactionID") String transactionID, Transaction newIncome) {
        String URL = apiURL + "/" + userID + "/incomes/" + transactionID + "/update";
        System.out.println("----------" + newIncome);
        newIncome.setTransactionID(transactionID);
        HttpEntity<Transaction> req = new HttpEntity<>(newIncome);
        ResponseEntity<String> resp = restTemplate.exchange(URL, HttpMethod.PUT, req, String.class);
        restTemplate.put(URL, newIncome);
        if (resp.getStatusCode().is2xxSuccessful()) {
            System.out.println(resp);
        } else {
            System.out.println("Failed to update incomes: " + resp.getStatusCode());
        }
        System.out.println(resp);
        return "redirect:/" + userID + "/incomes";
    }

    @PostMapping("/{userID}/incomes/{transactionID}/delete")
    public String deleteUserIncomes(@PathVariable("userID") String userID, @PathVariable("transactionID") String transactionID) {
        String URL = apiURL + "/" + userID + "/incomes/" + transactionID + "/delete";
        ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.DELETE, null, String.class);
        // restTemplate.delete(URL);
        System.out.println(response);
        return "redirect:/" + userID + "/incomes";
    }
}
