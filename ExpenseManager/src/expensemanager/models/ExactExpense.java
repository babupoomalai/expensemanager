package expensemanager.models;

import java.util.List;

public class ExactExpense extends Expense {

	public ExactExpense(User paidBy, double amount, List<Split> splits, ExpenseMetadata metadata) {
		super(paidBy, amount, splits, metadata);
	}

	@Override
	public boolean validate() {
		double totalAmount = 0;
		
		for (Split split : this.getSplits()) {
			if (!(split instanceof ExactSplit)) {
				return false;
			}
			
			ExactSplit exactSplit = (ExactSplit) split;
			totalAmount += exactSplit.getAmount();
		}
		return totalAmount == this.getAmount();
	}

}
