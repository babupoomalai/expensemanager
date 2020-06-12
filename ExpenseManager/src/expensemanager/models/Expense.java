package expensemanager.models;

import java.util.Date;
import java.util.List;

public abstract class Expense {

	private int id;
	private User paidBy;
	private List<Split> splits;
	private double amount;
	private Date createdOn;
	private Date updatedOn;
	private ExpenseMetadata metadata;

	public Expense(User paidBy, double amount, List<Split> splits, ExpenseMetadata metadata) {
		super();
		this.id = (int) Math.random();
		this.paidBy = paidBy;
		this.amount = amount;
		this.splits = splits;
		this.metadata = metadata;
		this.createdOn = new Date();
		this.updatedOn = new Date();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public User getPaidBy() {
		return paidBy;
	}

	public void setPaidBy(User paidBy) {
		this.paidBy = paidBy;
	}

	public List<Split> getSplits() {
		return splits;
	}

	public void setSplits(List<Split> splits) {
		this.splits = splits;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public Date getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Date updatedOn) {
		this.updatedOn = updatedOn;
	}

	public ExpenseMetadata getMetadata() {
		return metadata;
	}

	public void setMetadata(ExpenseMetadata metadata) {
		this.metadata = metadata;
	}

	public abstract boolean validate();

	@Override
	public String toString() {
		return "Expense [createdOn= " + createdOn + ", paidBy=" + paidBy + ", splits=" + splits + ", amount=" + amount
				+ ", updatedOn=" + updatedOn + ", metadata=" + metadata + "]";
	}

}
