package br.com.caelum.ingresso.model.form;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.caelum.ingresso.dao.UsuarioDao;
import br.com.caelum.ingresso.model.Permissao;
import br.com.caelum.ingresso.model.Token;
import br.com.caelum.ingresso.model.Usuario;

public class ConfirmacaoLoginForm {
	
	private Token token;
	private String password;
	private String confirmPassword;
	
	/**
	 * @deprecated Hibernate Only
	 */
	public ConfirmacaoLoginForm() {}
	
	public ConfirmacaoLoginForm(Token token) {
		this.token = token;
	}

	public Token getToken() {
		return token;
	}
	public void setToken(Token token) {
		this.token = token;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}
	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	public boolean isValid() {
		return this.password.equals(confirmPassword);
	}
	
	public Usuario toUsuario(UsuarioDao usuarioDao, PasswordEncoder passwordEnconder) {
		
		String encriptedPassword = passwordEnconder.encode(this.password);
		
		String email = token.getEmail();
		
		Usuario usuario = usuarioDao.findByEmail(email).orElse(this.novoUsuario(email, encriptedPassword));
		
		usuario.setPassword(encriptedPassword);
		
		return usuario;
		
	}
	
	private Usuario novoUsuario(String email, String password) {
		
		Set<Permissao> permissoes = new HashSet<>();
		permissoes.add(new Permissao("ROLE_COMPRADOR"));
		
		return new Usuario(email, password, permissoes);
		
	}

}