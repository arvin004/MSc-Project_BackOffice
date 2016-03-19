package com.backoffice.service;

import com.backoffice.entity.Client;

public interface ClientService {
	
	public Client saveLogin(Client client);
	
	public Client findByUsername(String username);
	

}
