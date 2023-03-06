package com.generation.blogpessoal.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.generation.blogpessoal.model.Usuario;


public class UserDetailsImpl implements UserDetails{
	
private static final long serialVersionUID = 1L;
	
	private String userName;
	private String password;
	
	private List<GrantedAuthority> authorities;
	
	public UserDetailsImpl (Usuario user) {
		this.userName = user.getUsuario();
		this.password = user.getSenha();
	}
	
	public UserDetailsImpl() {}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(){
		return authorities;
	}
	
	@Override
	public String getPassword() {
	
		return password;
	}

	@Override
	public String getUsername() {
		
		return userName;
	}

	@Override
	//Indica se a conta do usuário expirou. Uma conta expirada não pode ser autenticada (return false).
	public boolean isAccountNonExpired() {
		
		return true;
	}

	@Override
	//Indica se o usuário está bloqueado ou desbloqueado. Um usuário bloqueado não pode ser autenticado (return false).
	public boolean isAccountNonLocked() {
		
		return true;
	}

	@Override
	//Indica se as credenciais do usuário (senha) expiraram. Senha expirada impede a autenticação (return false).
	public boolean isCredentialsNonExpired() {
		
		return true;
	}

    @Override
    //Indica se o usuário está habilitado ou desabilitado. Um usuário desabilitado não pode ser autenticado (return false).
	public boolean isEnabled() {
		
		return true;
	}
}
