package com.bank.service;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.bank.dao.AccountDao;
import com.bank.dao.ClientDao;
import com.bank.dao.OperationDao;
import com.bank.modal.Account;
import com.bank.modal.Client;
import com.bank.modal.Credit;
import com.bank.modal.StandingAccount;
import com.bank.modal.Operation;
import com.bank.modal.SavingsAccount;
import com.bank.modal.Withdraw;

@Transactional
@Component
public class BankServiceImp implements BankService {

	@Autowired
	private AccountDao accountDao;

	@Autowired
	private OperationDao operationDao;

	@Autowired
	private ClientDao clientDao;

	@Override
	public List<Operation> checkingAccount(int code) {
		return operationDao.checkingAccount(code);
	}

	@Override
	public Operation deposit(int code, double amount) {
		String deposit = "deposit";
		Account account = accountDao.findById(code).orElse(null);
		Credit credit = new Credit(new Date(), amount, deposit, account);
		account.setBalance(account.getBalance() + amount);
		accountDao.save(account);
		return operationDao.save(credit);
	}

	@Override
	public Operation debit(int code, double amount) {
		String debit = "debit";
		Account account = accountDao.findById(code).orElse(null);
		double checkout = 0;
		if (account instanceof StandingAccount) {
			checkout = ((StandingAccount) account).getLoan();
		}

		if (account.getBalance() + checkout < amount) {
			throw new RuntimeException("Insufficient balance");
		}
		Withdraw withdraw = new Withdraw(new Date(), amount, debit, account);
		account.setBalance(account.getBalance() - amount);
		accountDao.save(account);

		return operationDao.save(withdraw);

	}

	@Override
	public Operation transfer(int code1, int code2, Operation operation) {
		if (code1 == code2) {
			throw new RuntimeException("Cannot transfer to the same account ");
		}
		deposit(code1, operation.getAmount());
		debit(code2, operation.getAmount());
		return operationDao.save(operation);
	}

	@Override
	public void depositOperation(int code, double amount) {
		Account account = accountDao.findById(code).orElse(null);
		deposit(account.getId(), amount);
		/*
		try {
			Account account = accountDao.findById(code).orElse(null);

			operation.setOperationDate(new Date());
			account.doOperation(operation);
			String deposit = "deposit";

			Credit credit = new Credit(new Date(), operation.getAmount(), deposit, account);
			account.setBalance(account.getBalance() + operation.getAmount());
			accountDao.save(account);
						
			
			return operationDao.save(operation);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
		*/
	}

	@Override
	public Client loginClient(Client client) {
		List<Client> clients = clientDao.findAll();
		if (clients.size() == 0) {
			client.setAdmin(true);
		}

		for (Client client2 : clients) {
			if (client.getUsername().equals(client2.getUsername())) {
				client2.setUsername(client2.getUsername());
				return clientDao.save(client2);
			}
		}
		return clientDao.save(client);
	}

	@Override
	public StandingAccount addStandingAccount(StandingAccount standingAccount, long id) {
		char[] chars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(6);
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		Client client = clientDao.findById(id).orElse(null);
		standingAccount.setCreateDate(new Date());
		standingAccount.setCode(output);
		client.addAccount(standingAccount);
		return accountDao.save(standingAccount);
	}

	@Override
	public void debitOperation(int code, double amount) {
		Account account = accountDao.findById(code).orElse(null);
		debit(account.getId(), amount);

	}

	@Override
	public SavingsAccount addSavingsAccount(SavingsAccount savingsAccount, long id) {
		char[] chars = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
		StringBuilder sb = new StringBuilder(6);
		Random random = new Random();
		for (int i = 0; i < 6; i++) {
			char c = chars[random.nextInt(chars.length)];
			sb.append(c);
		}
		String output = sb.toString();
		Client client = clientDao.findById(id).orElse(null);
		savingsAccount.setCreateDate(new Date());
		savingsAccount.setCode(output);
		client.addAccount(savingsAccount);
		return accountDao.save(savingsAccount);
	}

	@Override
	public List<Operation> findWithdraws(int id) {
		List<Operation> operations = accountDao.getOne(id).getOperations().stream().filter(w -> w instanceof Withdraw)
				.collect(Collectors.toList());

		return operations;
	}

	@Override
	public List<Operation> findCredits(int id) {
		List<Operation> operations = accountDao.getOne(id).getOperations().stream().filter(c -> c instanceof Credit)
				.collect(Collectors.toList());
		return operations;
	}

	@Override
	public List<Client> findClients() {
		return clientDao.findAll();
	}

	@Override
	public Client findClient(long id) {
		return clientDao.findById(id).orElse(null);
	}

	@Override
	public Client updateClient(Client client, long id) {
		Client client2 = clientDao.findById(id).orElse(null);
		client2.setPassword(client.getPassword());
		client2.setUsername(client.getUsername());
		client2.setEmail(client.getEmail());
		client2.setName(client.getName());
		return clientDao.save(client2);
	}

	@Override
	public List<Account> findStandingAccountAccounts(long id) {
		List<Account> accounts = clientDao.getOne(id).getAccounts().stream().filter(s -> s instanceof StandingAccount)
				.collect(Collectors.toList());
		return accounts;
	}

	@Override
	public List<Account> findSavingsAccountAccounts(long id) {
		List<Account> accounts = clientDao.getOne(id).getAccounts().stream().filter(s -> s instanceof SavingsAccount)
				.collect(Collectors.toList());
		return accounts;
	}

	@Override
	public List<Account> findAccountsForClient(long id) {
		Client client = clientDao.findById(id).orElse(null);
		return client.getAccounts();
	}

	@Override
	public StandingAccount editStandingAccount(StandingAccount standingAccount, int id) {
		StandingAccount standingAccount2 = (StandingAccount) accountDao.findById(id).orElse(null);
		standingAccount2.setLoan(standingAccount.getLoan());
		standingAccount2.setBalance(standingAccount.getBalance() + standingAccount.getLoan());
		return accountDao.save(standingAccount2);
	}

	@Override
	public SavingsAccount editSavingsAccount(SavingsAccount savingsAccount, int id) {
		SavingsAccount savingsAccount2 = (SavingsAccount) accountDao.findById(id).orElse(null);
		savingsAccount2.setTax(savingsAccount.getTax());
		savingsAccount2.setBalance(savingsAccount2.getBalance() - savingsAccount.getTax());
		return accountDao.save(savingsAccount2);
	}

}
