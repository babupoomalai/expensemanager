package expensemanager.controller;

public class Sample {

	public static void main(String args[]) {
		double val = 100.00;
		double shareAmount = (double) Math.round(100 * 100 / 3) / 100.0;
		double adjustedAmount = 100 - (shareAmount * (2));
		System.out.println(shareAmount);
		System.out.println(adjustedAmount);
	}

}
