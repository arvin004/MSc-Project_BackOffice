package com.backoffice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.backoffice.entity.Client;
import com.backoffice.service.ClientService;

@RestController
@RequestMapping("/api/client")
public class ClientController {
	
	@Autowired
	private ClientService clientService;
	
	
	
	@RequestMapping(value = "/findByUsername" , method=RequestMethod.GET)
	public Client findByUsername(String username) {
		return clientService.findByUsername(username);
		
	}
	
	
	@RequestMapping(value = "/login", method=RequestMethod.POST)
	public Client saveLogin(@RequestBody Client client) {
		return clientService.saveLogin(client);
		
	}
	
}
