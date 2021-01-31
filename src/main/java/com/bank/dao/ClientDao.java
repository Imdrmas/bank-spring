package com.bank.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.modal.Client;

public interface ClientDao extends JpaRepository<Client, Long> {

}
