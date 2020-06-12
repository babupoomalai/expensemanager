package expensemanager.models;

import java.util.List;

public class EqualExpense extends Expense {

	public EqualExpense(User paidBy, double amount, List<Split> splits, ExpenseMetadata metadata) {
		super(paidBy, amount, splits, metadata);
	}

	@Override
	public boolean validate() {
		double totalAmount = 0;
		
		for (Split split : this.getSplits()) {
			if (!(split instanceof EqualSplit)) {
				return false;
			}
			
			EqualSplit equalSplit = (EqualSplit) split;
			totalAmount += equalSplit.getAmount();
		}
		return totalAmount == this.getAmount();
	}

}
