package com.backoffice;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;


public class BackOfficeUser extends User {
	
	private static final long serialVersionUID = -1460047265506653280L;
	
    public BackOfficeUser(Long id, String username, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
    	super(username, password, enabled, true, true, true, authorities);
    }
	
}