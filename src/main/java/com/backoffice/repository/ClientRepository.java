package com.backoffice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.backoffice.entity.Client;


@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
	
	public Client findByUsername(String username);
	
}
