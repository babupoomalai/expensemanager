package expensemanager.models;

import java.util.List;

public class PercentExpense extends Expense {

	public PercentExpense(User paidBy, double amount, List<Split> splits, ExpenseMetadata metadata) {
		super(paidBy, amount, splits, metadata);
	}

	@Override
	public boolean validate() {
		double totalPercent = 0;

		for (Split split : getSplits()) {
			if (!(split instanceof PercentSplit)) {
				return false;
			}

			PercentSplit percentSplit = (PercentSplit) split;
			totalPercent += percentSplit.getPercent();
		}
		
		return totalPercent == 100;
	}

}
