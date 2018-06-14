package br.com.caelum.ingresso.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.dao.SessaoDao;
import br.com.caelum.ingresso.model.Sessao;
import br.com.caelum.ingresso.model.form.SessaoForm;

@Controller
public class SessaoController {

	@Autowired
	private SalaDao salaDAO;
	
	@Autowired
	private FilmeDao filmeDAO;
	
	@Autowired
	private SessaoDao sessaoDAO;
	
	@GetMapping("/admin/sessao")
	private ModelAndView form(@RequestParam("salaId") Integer salaID, SessaoForm form) {
		
		ModelAndView modelAndView = new ModelAndView("sessao/sessao");
		
		form.setSalaId(salaID);
		
		modelAndView.addObject("sala", salaDAO.findOne(salaID));
		modelAndView.addObject("filmes", filmeDAO.findAll());
		modelAndView.addObject("form", form);
		
		return modelAndView;
		
	}
	
	@Transactional
	@PostMapping("/admin/sessao")
	private ModelAndView save(@Valid SessaoForm form, BindingResult result) {
		
		if (result.hasErrors()) {
			return this.form(form.getSalaId(), form);
		}
		
		Sessao sessao = form.toSessao(this.filmeDAO, this.salaDAO);
		
		this.sessaoDAO.save(sessao);
		
		return new ModelAndView("redirect:/admin/sala/" + form.getSalaId() + "/sessoes");
		
	}
	
}
