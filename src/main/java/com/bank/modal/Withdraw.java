package com.bank.modal;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("W")
public class Withdraw extends Operation {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Withdraw() {
		super();
	}

	public Withdraw(Date operationDate, double amount, String typeOperation, Account account) {
		super(operationDate, amount, typeOperation, account);
	}
	
	

}
