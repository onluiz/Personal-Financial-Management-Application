package com.fean.seufinanceiro.security.services;

import com.fean.seufinanceiro.model.Usuario;
import com.fean.seufinanceiro.security.JwtUserFactory;
import com.fean.seufinanceiro.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Primary
public class JwtUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<Usuario> usuario = usuarioService.findUserByUsernameEmail(email);

		if (usuario.isPresent()){
			return JwtUserFactory.create(usuario.get());
		}

		throw new UsernameNotFoundException("Email não encontrado.");
	}

}
