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
	
	private IJustificativaDAO dao;
	

	public void setDao(IJustificativaDAO dao) {
		this.dao = dao;
	}

	@Transactional(readOnly = false)
	public void addJustificativaPonto(JustificativaPonto justificativa) {
		dao.adicionar(justificativa);
	}

	@Transactional(readOnly = false)
	public void updateJustificativaPonto(JustificativaPonto justificativa) {
		dao.atualizar(justificativa);
	}

	@Transactional(readOnly = false)
	public void deleteJustificativaPonto(JustificativaPonto justificativa) {
		dao.deletar(justificativa);
	}

	public JustificativaPonto getJustificativaPontoById(
			JustificativaPonto justificativa) {
		return dao.recuperar(justificativa);
	}

	public List<JustificativaPonto> getJustificativaPontos() {
		return dao.todos();
	}

    public List<JustificativaPonto> todasPorData(int startIndex, int pageSize){
        return dao.todosPorData(startIndex, pageSize);
    }

    @Override
    public List<JustificativaPonto> todasPorMotivo(int startIndex, int pageSize) {
        return dao.todosPorMotivo(startIndex, pageSize);
    }

    @Override
    public List<JustificativaPonto> todasPorSolicitante(int startIndex, int pageSize) {
        return dao.todosPorSolicitante(startIndex, pageSize);
    }

    public int count(){
        return dao.count();
    }

}
