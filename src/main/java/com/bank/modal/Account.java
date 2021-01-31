package com.bank.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class Account implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	private String code;

	private Date createDate;

	private double balance;

	@ManyToOne
	@JsonBackReference(value = "client")
	private Client client;

	@OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
	private List<Operation> operations;

	public Account() {
		super();
	}

	public Account(String code, Date createDate, double balance, Client client) {
		super();
		this.code = code;
		this.createDate = createDate;
		this.balance = balance;
		this.client = client;
	}

	public Account(String code, Date createDate, double balance, Client client, List<Operation> operations) {
		super();
		this.code = code;
		this.createDate = createDate;
		this.balance = balance;
		this.client = client;
		this.operations = operations;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<Operation> getOperations() {
		return operations;
	}

	public void setOperations(List<Operation> operations) {
		this.operations = operations;
	}

	public void doOperation(Operation operation) {
		if (getOperations() == null) {
			this.operations = new ArrayList<>();
		}
		getOperations().add(operation);
		operation.setAccount(this);
	}

}
