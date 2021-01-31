package com.bank.modal;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SA")
public class SavingsAccount extends Account {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private double tax;

	public SavingsAccount() {
		super();
	}

	public SavingsAccount(String code, Date createDate, double balance, Client client, double tax) {
		super(code, createDate, balance, client);
		this.tax = tax;
	}

	public double getTax() {
		return tax;
	}

	public void setTax(double tax) {
		this.tax = tax;
	}
  

}
