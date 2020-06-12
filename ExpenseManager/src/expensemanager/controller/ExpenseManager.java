package expensemanager.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import expensemanager.models.Expense;
import expensemanager.models.ExpenseMetadata;
import expensemanager.models.ExpenseType;
import expensemanager.models.Split;
import expensemanager.models.User;
import expensemanager.service.ExpenseService;

public class ExpenseManager {

	private static ExpenseManager instance;

	private List<Expense> expenses;
	private Map<String, User> userMap;
	private Map<String, Map<String, Double>> balanceSheet;

	private ExpenseManager() {
		super();
		this.expenses = new ArrayList<Expense>();
		this.userMap = new HashMap<String, User>();
		this.balanceSheet = new HashMap<String, Map<String, Double>>();
	}

	public static ExpenseManager getInstance() {
		if (instance == null) {
			instance = new ExpenseManager();
		}
		return instance;
	}

	public Map<String, User> getUserMap() {
		return userMap;
	}

	public List<Expense> getExpenses() {
		return expenses;
	}

	public boolean createUser(String userId, String name, String email, String contactNumber) {
		if (userMap.containsKey(userId)) {
			return false;
		}
		User user = new User(userId, name, email, contactNumber);
		userMap.put(userId, user);
		balanceSheet.put(userId, new HashMap<String, Double>());

		return true;
	}

	public void addExpense(double amount, User paidBy, ExpenseType type, List<Split> splits, ExpenseMetadata metadata) {
		Expense expense = new ExpenseService().createExpense(amount, paidBy, type, splits, metadata);
		this.expenses.add(expense);

		for (Split split : splits) {
			String paidTo = split.getUser().getId();
			Map<String, Double> pendingBalanceMap = balanceSheet.get(paidBy.getId());
			if (!pendingBalanceMap.containsKey(paidTo)) {
				pendingBalanceMap.put(paidTo, 0.0);
			}
			pendingBalanceMap.put(paidTo, pendingBalanceMap.get(paidTo) + split.getAmount());

			// Payee balance sheet
			Map<String, Double> payeeMap = balanceSheet.get(paidTo);
			if (!payeeMap.containsKey(paidBy.getId())) {
				payeeMap.put(paidBy.getId(), 0.0);
			}
			payeeMap.put(paidBy.getId(), payeeMap.get(paidBy.getId()) - split.getAmount());

		}
	}

	public void showDashboard(String userId) {
		Map<String, Double> balances = balanceSheet.get(userId);
		Map<String, Double> owePeopleMap = balances.entrySet().stream().filter(map -> map.getValue() < 0.0)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));
		Map<String, Double> peopleOweMap = balances.entrySet().stream().filter(map -> map.getValue() > 0.0)
				.collect(Collectors.toMap(map -> map.getKey(), map -> map.getValue()));

		if (owePeopleMap.size() > 0) {
			System.out.println("You owe:");
			for (String user : owePeopleMap.keySet()) {
				System.out.println("User: " + user + ", Amount: " + (-owePeopleMap.get(user)));
			}
		}

		if (peopleOweMap.size() > 0) {
			System.out.println("People owe you");
			for (String user : peopleOweMap.keySet()) {
				System.out.println("user: " + user + ", Amount: " + peopleOweMap.get(user));
			}
		}

	}

}
