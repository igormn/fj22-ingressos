package br.com.caelum.ingresso.model.form;

import java.time.LocalTime;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import br.com.caelum.ingresso.dao.FilmeDao;
import br.com.caelum.ingresso.dao.SalaDao;
import br.com.caelum.ingresso.model.Filme;
import br.com.caelum.ingresso.model.Sala;
import br.com.caelum.ingresso.model.Sessao;

public class SessaoForm {
	
	private Integer id;
	
	@NotNull
	private Integer salaId;
	
	@NotNull
	private Integer filmeId;
	
	@NotNull
	@DateTimeFormat(pattern="HH:mm")
	private LocalTime horario;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getSalaId() {
		return salaId;
	}
	public void setSalaId(Integer salaId) {
		this.salaId = salaId;
	}
	
	public Integer getFilmeId() {
		return filmeId;
	}
	public void setFilmeId(Integer filmeId) {
		this.filmeId = filmeId;
	}
	
	public LocalTime getHorario() {
		return horario;
	}
	public void setHorario(LocalTime horario) {
		this.horario = horario;
	}

	public Sessao toSessao(FilmeDao filmeDAO, SalaDao salaDAO) {

		Filme filme = filmeDAO.findOne(this.filmeId);
		Sala sala = salaDAO.findOne(this.salaId);
		
		return new Sessao(this.horario, sala, filme);
	}

}
