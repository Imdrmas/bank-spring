package com.bank.service;

import java.util.List;

import com.bank.modal.Account;
import com.bank.modal.Client;
import com.bank.modal.Operation;
import com.bank.modal.SavingsAccount;
import com.bank.modal.StandingAccount;

public interface BankService {

	List<Operation> checkingAccount(int code);

	Operation deposit(int code, double amount);

	Operation debit(int code, double amount);

	Operation transfer(int code1, int code2, Operation operation);

	void debitOperation(int code, double amount);

	void depositOperation(int code, double amount);

	Client loginClient(Client client);
	
	Client updateClient(Client client, long id);

	StandingAccount addStandingAccount(StandingAccount standingAccount, long id);

	SavingsAccount addSavingsAccount(SavingsAccount savingsAccount, long id);
	
	StandingAccount editStandingAccount(StandingAccount standingAccount, int id);

	SavingsAccount editSavingsAccount(SavingsAccount savingsAccount, int id);

	List<Operation> findWithdraws(int id);

	List<Operation> findCredits(int id);
	
	List<Client> findClients();
	
	Client findClient(long id);
	
	List<Account> findStandingAccountAccounts(long id);
	
	List<Account> findSavingsAccountAccounts(long id);
	
	List<Account> findAccountsForClient(long id);

}
