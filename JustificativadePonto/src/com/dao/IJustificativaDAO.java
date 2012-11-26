package com.dao;

import java.util.List;

import com.model.JustificativaPonto;

public interface IJustificativaDAO {

	public void addJustificativaPonto(JustificativaPonto justificativa);
	public void updateJustificativaPonto(JustificativaPonto justificativa);
	public void deleteJustificativaPonto(JustificativaPonto justificativa);
	public JustificativaPonto getJustificativaPontoById(JustificativaPonto justificativa);
	public List<JustificativaPonto> getJustificativaPontos();
}
