package com.bank.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.dao.AccountDao;
import com.bank.modal.Account;
import com.bank.modal.Client;
import com.bank.modal.Operation;
import com.bank.modal.SavingsAccount;
import com.bank.modal.StandingAccount;
import com.bank.service.BankService;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = "*")
public class BankController {

	@Autowired
	private BankService bankService;
	
	@Autowired
	private AccountDao accountDao;

	@GetMapping("/checkingAccount/{code}")
	List<Operation> checkingAccount(@PathVariable int code) {
		return bankService.checkingAccount(code);
	}

	@PostMapping("/depositOperation/{code}")
	void depositOperation(@PathVariable int code, @RequestBody Operation operation) {
		 bankService.depositOperation(code, operation.getAmount());
	}

	@PostMapping("/saveClient")
	Client loginClient(@RequestBody Client client) {
		return bankService.loginClient(client);
	}

	@PostMapping("/addStandingAccount/{id}")
	StandingAccount addStandingAccount(@RequestBody StandingAccount standingAccount, @PathVariable long id) {
		return bankService.addStandingAccount(standingAccount, id);
	}

	@PostMapping("/addSavingsAccount/{id}")
	SavingsAccount addSavingsAccount(@RequestBody SavingsAccount savingsAccount, @PathVariable long id) {
		return bankService.addSavingsAccount(savingsAccount, id);
	}

	@PostMapping("/debitOperation/{code}")
	void debitOperation(@PathVariable int code, @RequestBody Operation operation) {
		 bankService.debitOperation(code, operation.getAmount());
	}

	@GetMapping("/findWithdraws/{id}")
	public List<Operation> findWithdraws(@PathVariable int id) {
		return bankService.findWithdraws(id);
	}

	@GetMapping("/findCredits/{id}")
	List<Operation> findCredits(@PathVariable int id) {
		return bankService.findCredits(id);
	}

	@PostMapping("/transfer/{code1}/{code2}")
	Operation transfer(@PathVariable int code1, @PathVariable int code2, @RequestBody Operation operation) {
		return bankService.transfer(code1, code2, operation);
	}
	
	@GetMapping("/findClients")
	List<Client> findClients() {
		return bankService.findClients();
	}
	
	@GetMapping("/findClient/{id}")
	Client findClient(@PathVariable long id) {
		return bankService.findClient(id);
	}
	
	@PutMapping("/updateClient/{id}")
	Client updateClient(@RequestBody Client client, @PathVariable long id) {
		return bankService.updateClient(client, id);
	}
	@GetMapping("/findStandingAccountAccounts/{id}")
	List<Account> findStandingAccountAccounts(@PathVariable long id) {
		return bankService.findStandingAccountAccounts(id);
	}
	
	@GetMapping("/findSavingsAccountAccounts/{id}")
	List<Account> findSavingsAccountAccounts(@PathVariable long id) {
		return bankService.findSavingsAccountAccounts(id);
	}
	
	@GetMapping("/findAccountById/{id}")
	Account findAccountById(@PathVariable int id) {
		return accountDao.findById(id).orElse(null);
	}
	@GetMapping("/findAccountsForClient/{id}")
	List<Account> findAccountsForClient(@PathVariable long id) {
		return bankService.findAccountsForClient(id);
	}
	@PostMapping("/editStandingAccount/{id}")
	StandingAccount editStandingAccount(@RequestBody StandingAccount standingAccount, @PathVariable int id) {
		return bankService.editStandingAccount(standingAccount, id);
	}
	@PostMapping("/editSavingsAccount/{id}")
	SavingsAccount editSavingsAccount(@RequestBody SavingsAccount savingsAccount, @PathVariable int id) {
		return bankService.editSavingsAccount(savingsAccount, id);
	}
}
