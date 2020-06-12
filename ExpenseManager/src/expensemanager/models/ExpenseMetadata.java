package expensemanager.models;

import java.util.ArrayList;
import java.util.List;

public class ExpenseMetadata {

	private List<ExpenseNote> notes = new ArrayList<ExpenseNote>();

	public ExpenseMetadata(ExpenseNote note) {
		if (note == null) {
			this.notes = new ArrayList<ExpenseNote>();
		}
		this.notes.add(note);
	}

}
