package br.com.caelum.ingresso.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Carrinho;
import br.com.caelum.ingresso.model.ImagemCapa;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.TipoDeIngresso;
import br.com.caelum.ingresso.model.form.SessaoForm;
import br.com.caelum.ingresso.model.validacao.GerenciadorDeSessao;
import br.com.caelum.ingresso.rest.ImdbClient;

@Controller
public class SessaoController {

	@Autowired
	private SalaDao salaDAO;
	
	@Autowired
	private FilmeDao filmeDAO;
	
	@Autowired
	private SessaoDao sessaoDAO;
	
	@Autowired
	private ImdbClient client;
	
	@Autowired
	private Carrinho carrinho;
	
	@GetMapping("/admin/sessao")
	public ModelAndView form(@RequestParam("salaId") Integer salaId, SessaoForm form) {

		form.setSalaId(salaId);
		
		ModelAndView modelAndView = new ModelAndView("/sessao/sessao");
		modelAndView.addObject("sala", salaDAO.findOne(salaId));
		modelAndView.addObject("filmes", filmeDAO.findAll());
		modelAndView.addObject("form", form);
		
		return modelAndView;
		
	}
	
	@Transactional
	@PostMapping("/admin/sessao")
	public ModelAndView salva(@Valid SessaoForm form, BindingResult result) {
		
		if (result.hasErrors()) {
			return this.form(form.getSalaId(), form);
		}
		
		Sessao sessao = form.toSessao(this.filmeDAO, this.salaDAO);
		
		GerenciadorDeSessao gerenciadorDeSessao = new GerenciadorDeSessao(sessaoDAO.buscarSessoesDaSala(sessao.getSala()));
		
		if (gerenciadorDeSessao.cabe(sessao)) {
			this.sessaoDAO.save(sessao);
			return new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");
		}
		else {
			return form(form.getSalaId(), form);
		}
		
	}
	
	@GetMapping("/sessao/{id}/lugares")
	public ModelAndView lugaresNaSessao(@PathVariable("id") Integer sessaoId) {
		
		ModelAndView modelAndView = new ModelAndView("/sessao/lugares");

		Sessao sessao = sessaoDAO.findOne(sessaoId);
		
		modelAndView.addObject("sessao", sessao);
		
		Optional<ImagemCapa> imagemCapa = client.request(sessao.getFilme(), ImagemCapa.class);
		
		modelAndView.addObject("imagemCapa", imagemCapa.orElse(new ImagemCapa()));
		modelAndView.addObject("tiposDeIngressos", TipoDeIngresso.values());
		modelAndView.addObject("carrinho", carrinho);
		
		return modelAndView;
		
	}
	
}
