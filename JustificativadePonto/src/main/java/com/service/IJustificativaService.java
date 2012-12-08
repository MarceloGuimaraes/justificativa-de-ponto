package com.service;

import java.util.List;

import com.model.JustificativaPonto;

public interface IJustificativaService {

	public void addJustificativaPonto(JustificativaPonto justificativa);
	public void updateJustificativaPonto(JustificativaPonto justificativa);
	public void deleteJustificativaPonto(JustificativaPonto justificativa);
	public JustificativaPonto getJustificativaPontoById(JustificativaPonto justificativa);
	public List<JustificativaPonto> getJustificativaPontos();
    List<JustificativaPonto> todas(int startIndex, int pageSize);
    int count();
}
