package com.service;

import java.io.Serializable;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.dao.IJustificativaDAO;
import com.model.JustificativaPonto;

@Transactional(readOnly = true)
public class JustificativaService implements IJustificativaService,
		Serializable {

	private static final long serialVersionUID = 1L;
	
	private IJustificativaDAO justificativaDAO;
	

	public IJustificativaDAO getJustificativaDAO() {
		return justificativaDAO;
	}

	public void setJustificativaDAO(IJustificativaDAO justificativaDAO) {
		this.justificativaDAO = justificativaDAO;
	}

	@Transactional(readOnly = false)
	public void addJustificativaPonto(JustificativaPonto justificativa) {
		getJustificativaDAO().adicionar(justificativa);
	}

	@Transactional(readOnly = false)
	public void updateJustificativaPonto(JustificativaPonto justificativa) {
		getJustificativaDAO().atualizar(justificativa);
	}

	@Transactional(readOnly = false)
	public void deleteJustificativaPonto(JustificativaPonto justificativa) {
		getJustificativaDAO().deletar(justificativa);
	}

	public JustificativaPonto getJustificativaPontoById(
			JustificativaPonto justificativa) {
		return getJustificativaDAO().recuperar(justificativa);
	}

	public List<JustificativaPonto> getJustificativaPontos() {
		return getJustificativaDAO().todos();
	}

}
