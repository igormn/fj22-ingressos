package br.com.caelum.ingresso.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;

@Controller
public class SessaoController {

	@Autowired
	private SalaDao salaDAO;
	
	@Autowired
	private FilmeDao filmeDAO;
	
	@GetMapping("/admin/sessao")
	private ModelAndView form(@RequestParam("salaId") Integer salaID) {
		
		ModelAndView modelAndView = new ModelAndView("sessao/sessao");
		
		modelAndView.addObject("sala", salaDAO.findOne(salaID));
		modelAndView.addObject("filmes", filmeDAO.findAll());
		
		return modelAndView;
		
	}
	
}
