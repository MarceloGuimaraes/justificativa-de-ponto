package com.service;

import java.io.Serializable;
import java.util.List;

import com.model.JustificativaPonto;

public interface IJustificativaService {

    JustificativaPonto recuperar(JustificativaPonto justificativa);
    JustificativaPonto recuperar(Serializable id);
    void adicionar(JustificativaPonto justificativa);
    void atualizar(JustificativaPonto justificativa);
    void apagar(JustificativaPonto justificativa);
	List<JustificativaPonto> todas();
    List<JustificativaPonto> todasPorData(int startIndex, int pageSize);
    List<JustificativaPonto> todasPorMotivo(int startIndex, int pageSize);
    List<JustificativaPonto> todasPorSolicitante(int startIndex, int pageSize);
    int count();
}
