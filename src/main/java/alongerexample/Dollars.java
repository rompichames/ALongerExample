package alongerexample;

import java.math.BigInteger;

/** 
 * Ecrite par Hervé Leblanc d'apr�s Analysis Pattern Quantity de Martin Fowler
 *  
 * @author Hervé Leblanc
 *
 */

public class Dollars {
	
	private BigInteger amount;
	
	public Dollars(Dollars dollards) {
		this.amount = dollards.amount;
	}

	public Dollars(double amount) {
		this.amount = BigInteger.valueOf(Math.round (amount * 100));
	}
	
	private Dollars(BigInteger amountInPennies) {
		this.amount = amountInPennies;
	}
	
	public double amount() {
		return this.amount.doubleValue() / 100;
	}

	/**
	 * Méthodes arithmétiques de base
	 * Chaque méthode a sa contrepartie avec un double ou un dollar
	 */
	public Dollars plus(Dollars dollars) {
		return new Dollars(this.amount.add(dollars.amount));
	}
	
	public Dollars plus(double amount) {
		return this.plus(new Dollars(amount));
	}

	public Dollars minus(Dollars dollars) {
		return new Dollars(this.amount.add(dollars.amount.negate()));
	}
	
	public Dollars minus(double amount) {
		return this.minus(new Dollars(amount));
	}
	
	public Dollars times(double arg) {
		return new Dollars(this.amount() * arg);
	}

	/**
	 *  Quelques méthodes de comparaison entre dollars
	 */
	public Dollars min(Dollars dollars) {
		return new Dollars(this.amount.min(dollars.amount));
	}

	public Dollars max(Dollars dollars) {
		return new Dollars(this.amount.max(dollars.amount));
	}

	public boolean isGreaterThan(Dollars dollars) {
		return this.amount.compareTo(dollars.amount) == 1;
	}

}
