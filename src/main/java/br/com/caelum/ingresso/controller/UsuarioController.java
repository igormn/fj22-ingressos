package br.com.caelum.ingresso.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.caelum.ingresso.dao.UsuarioDao;
import br.com.caelum.ingresso.mail.EmailNovoUsuario;
import br.com.caelum.ingresso.mail.Mailer;
import br.com.caelum.ingresso.mail.TokenHelper;
import br.com.caelum.ingresso.model.Token;
import br.com.caelum.ingresso.model.Usuario;
import br.com.caelum.ingresso.model.form.ConfirmacaoLoginForm;

@Controller
public class UsuarioController {
	
	@Autowired
	private Mailer mailer;
	
	@Autowired
	private TokenHelper tokenHelper;
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	@GetMapping("/usuario/novoUsuario")
	public ModelAndView formSolicitacaoDeAcesso() {
		return new ModelAndView("/usuario/form");
	}
	
	@Transactional
	@PostMapping("/usuario/criarUsuario")
	public ModelAndView solicitacaoDeAcesso(String email) {
		
		ModelAndView modelAndView = new ModelAndView("/usuario/adicionado");
		
		Token token = tokenHelper.generateFrom(email);
		
		mailer.send(new EmailNovoUsuario(token));
		
		modelAndView.addObject("email", email);
		
		return modelAndView;
	}
	
	@GetMapping("/usuario/validate")
	public ModelAndView validaLink(@RequestParam("uuid") String uuid, RedirectAttributes redirectAttributes) {
		
		Optional<Token> optionalToken = tokenHelper.getTokenFrom(uuid);
		
		if (!optionalToken.isPresent()) {
			
			ModelAndView modelAndView = new ModelAndView("redirect:/login");
			
			redirectAttributes.addFlashAttribute("msg", "O Token do Link utilizado não foi encontrado! Favor, entre em contato com o Administrador do Sistema de Ingressos - CAELUM!");
			
			return modelAndView;
			
		}
		
		Token token = optionalToken.get();
		ConfirmacaoLoginForm confirmacaoLoginForm = new ConfirmacaoLoginForm(token);
		
		ModelAndView modelAndView = new ModelAndView("/usuario/confirmacao");
		
		modelAndView.addObject("confirmacaoLoginForm", confirmacaoLoginForm);
		
		return modelAndView;
		
	}
	
	@Transactional
	@PostMapping("/usuario/cadastrar")
	public ModelAndView cadastrar(ConfirmacaoLoginForm form, RedirectAttributes redirectAttributes) {
		
		if (form.isValid()) {
			Usuario usuario = form.toUsuario(usuarioDao, new BCryptPasswordEncoder());
			
			usuarioDao.save(usuario);
			
			redirectAttributes.addFlashAttribute("msg", "Usuário Cadastrado com Sucesso!");
			
			return new ModelAndView("redirect:/login");
		} else {
			redirectAttributes.addFlashAttribute("msg", "As senhas informadas não conferem. Favor, tente novamente acessando o link do e-mail!");
			
			return new ModelAndView("redirect:/login");
		}
		
	}
	
}
