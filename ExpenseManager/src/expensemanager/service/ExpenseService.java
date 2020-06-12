package expensemanager.service;

import java.util.List;

import expensemanager.models.EqualExpense;
import expensemanager.models.ExactExpense;
import expensemanager.models.Expense;
import expensemanager.models.ExpenseMetadata;
import expensemanager.models.ExpenseType;
import expensemanager.models.PercentExpense;
import expensemanager.models.PercentSplit;
import expensemanager.models.Split;
import expensemanager.models.User;

public class ExpenseService {

	public Expense createExpense(double amount, User paidBy, ExpenseType type, List<Split> splits,
			ExpenseMetadata metadata) {

		switch (type) {
		case EQUAL: {
			int splitSize = splits.size();
			double shareAmount = (double) Math.round(amount * 100 / splitSize) / 100.0;
			// To make 100% percentage
			double adjustedAmount = amount - (shareAmount * (splitSize - 1));
			boolean firstPass = true;

			for (Split split : splits) {
				if (firstPass) {
					split.setAmount(adjustedAmount);
					firstPass = false;
				} else {
					split.setAmount(shareAmount);
				}

			}

			EqualExpense equalExpense = new EqualExpense(paidBy, amount, splits, metadata);
			boolean isValid = equalExpense.validate();
			if (!isValid) {
				System.out.println("Invalid equal expense record: " + splits);
				return null;
			}
			return equalExpense;
		}

		case EXACT: {
			ExactExpense exactExpense = new ExactExpense(paidBy, amount, splits, metadata);
			boolean isValid = exactExpense.validate();
			if (!isValid) {
				return null;
			}
			return exactExpense;
		}

		case PERCENT: {
			for (Split split : splits) {
				PercentSplit percentSplit = (PercentSplit) split;
				percentSplit.setAmount(amount * percentSplit.getPercent() / 100.0);
			}

			PercentExpense percentExpense = new PercentExpense(paidBy, amount, splits, metadata);
			boolean isValid = percentExpense.validate();
			if (!isValid) {
				System.out.println("Invalid percent expense record: " + splits);
				return null;
			}
			return percentExpense;

		}

		default:
			return null;
		}

	}

}
