package expensemanager;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import expensemanager.controller.ExpenseManager;
import expensemanager.models.EqualSplit;
import expensemanager.models.ExactSplit;
import expensemanager.models.Expense;
import expensemanager.models.ExpenseType;
import expensemanager.models.PercentSplit;
import expensemanager.models.Split;
import expensemanager.models.User;

public class App {

	public static void main(String args[]) {
		ExpenseManager expenseManager = ExpenseManager.getInstance();

		expenseManager.createUser("u1", "User1", "u1@gmail.com", "123");
		expenseManager.createUser("u2", "User2", "u2@gmail.com", "1234");
		expenseManager.createUser("u3", "User3", "u3@gmail.com", "1231");
		expenseManager.createUser("u4", "User4", "u4@gmail.com", "123123");

		Scanner scanner = new Scanner(System.in);
		String command;
		do {
			command = scanner.nextLine();
			doProcess(command);
		} while (!command.equalsIgnoreCase("end"));
		scanner.close();
	}

	private static void doProcess(String command) {
		ExpenseManager expenseManager = ExpenseManager.getInstance();

		String[] commands = command.split(" ");
		String commandType = commands[0].toUpperCase();

		switch (commandType) {
		case "SHOW": {
			if (commands.length == 2) {
				expenseManager.showDashboard(commands[1]);
			}
//			for (Expense expense : expenseManager.getExpenses()) {
//				System.out.println(expense);
//			}
			break;
		}

		case "EXPENSE": {
			User paidBy = expenseManager.getUserMap().get(commands[1]);
			if (paidBy == null) {
				return;
			}
			String type = commands[2];
			ExpenseType expenseType = ExpenseType.valueOf(type);

			double totalAmount = Double.parseDouble(commands[3]);

			switch (expenseType) {
			case EQUAL: {
				List<Split> splits = new ArrayList<>();
				for (int i = 4; i < commands.length; i++) {
					EqualSplit split = new EqualSplit(expenseManager.getUserMap().get(commands[i]));
					splits.add(split);
				}
				expenseManager.addExpense(totalAmount, paidBy, expenseType, splits, null);
				break;
			}

			case EXACT: {
				List<Split> splits = new ArrayList<>();
				for (int i = 4; i < commands.length; i = i + 2) {
					ExactSplit split = new ExactSplit(expenseManager.getUserMap().get(commands[i]),
							Double.parseDouble(commands[i + 1]));
					splits.add(split);
				}
				expenseManager.addExpense(totalAmount, paidBy, expenseType, splits, null);
				break;
			}

			case PERCENT: {
				List<Split> splits = new ArrayList<>();
				for (int i = 4; i < commands.length; i = i + 2) {
					PercentSplit split = new PercentSplit(expenseManager.getUserMap().get(commands[i]),
							Double.parseDouble(commands[i + 1]));
					splits.add(split);
				}
				expenseManager.addExpense(totalAmount, paidBy, expenseType, splits, null);
				break;
			}

			default:
				break;

			}

		}

		case "END":
			return;
		}
	}

}
