package expensemanager.models;

import java.util.Date;

public class ExpenseNote {

	private String note;
	private Date createdOn;

	public ExpenseNote(String note, Date createdOn) {
		super();
		this.note = note;
		this.createdOn = createdOn;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

}
