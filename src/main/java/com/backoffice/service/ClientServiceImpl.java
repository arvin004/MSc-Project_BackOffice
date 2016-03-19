package com.backoffice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.backoffice.entity.Client;
import com.backoffice.repository.ClientRepository;
import com.backoffice.service.ClientService;

@Service
public class ClientServiceImpl implements ClientService {
	
	@Autowired
	private ClientRepository clientRepository;

	@Override
	public Client saveLogin(Client client) {
		// TODO Auto-generated method stub
		return clientRepository.save(client);
	}

	@Override
	public Client findByUsername(String username) {
		// TODO Auto-generated method stub
		return clientRepository.findByUsername(username);
	}

	
}
