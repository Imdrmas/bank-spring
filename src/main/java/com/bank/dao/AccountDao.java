package com.bank.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.modal.Account;

public interface AccountDao extends JpaRepository<Account, Integer> {
	Optional<Account> findByCode(String code);
}
