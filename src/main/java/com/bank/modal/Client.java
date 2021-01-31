package com.bank.modal;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

@Entity
@Table(name = "clients")
public class Client implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "_id")
	private long id;

	@Column(name = "_username", unique = true)
	private String username;

	@Column(name = "_password")
	private String password;

	@Column(name = "_name")
	private String name;

	@Column(name = "_email")
	private String email;

	private boolean admin;

	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
	private List<Account> accounts;

	public Client() {
		super();
	}

	public Client(String username, String password, String name, String email, boolean admin, List<Account> accounts) {
		super();
		this.username = username;
		this.password = password;
		this.name = name;
		this.email = email;
		this.admin = admin;
		this.accounts = accounts;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}

	public void addAccount(Account account) {
		if (getAccounts() == null) {
			this.accounts = new ArrayList<>();
		}
		getAccounts().add(account);
		account.setClient(this);
	}

}
