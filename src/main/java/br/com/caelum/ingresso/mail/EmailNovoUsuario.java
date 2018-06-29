package br.com.caelum.ingresso.mail;

import br.com.caelum.ingresso.model.Token;

public class EmailNovoUsuario implements Email {

	private final Token token;
	
	public EmailNovoUsuario(Token token) {
		this.token = token;
	}
	
	@Override
	public String getTo() {
		return this.token.getEmail();
	}

	@Override
	public String getBody() {
		StringBuilder body = new StringBuilder();
		
		body.append("<html>");
		body.append("<body>");
		body.append("<h2> Bem Vindo </h2>");
		body.append(String.format("Acesse o <a href=%s>link</a> para criar o seu login no sistema de ingressos.", makeURL()));
		body.append("</body>");
		body.append("</html>");
		
		return body.toString();
	}

	@Override
	public String getSubject() {
		return "Cadastro Sistema de Ingressos";
	}
	
	private Object makeURL() {
		return String.format("http://localhost:8081/usuario/validate?uuid=%s", this.token.getUuid());
	}

}
