package alongerexample;

import java.util.Date;

public class Reading {
	
	private Date _date;
	private int _amount;
	
	public Reading(int amount, Date date) {
		_amount = amount;
		_date = date;
	}

	public int amount() {
		return _amount;
	}

	public Date date() {
		return _date;
	}

}
