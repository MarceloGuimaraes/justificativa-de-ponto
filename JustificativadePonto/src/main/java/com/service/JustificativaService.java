package com.service;

import java.io.Serializable;
import java.util.List;

import com.model.HistoricoJustificativaPonto;
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
	public void adicionar(JustificativaPonto justificativa) {
		dao.adicionar(justificativa);
	}

	@Transactional(readOnly = false)
	public void atualizar(JustificativaPonto justificativa) {
		dao.atualizar(justificativa);
	}

	@Transactional(readOnly = false)
	public void apagar(JustificativaPonto justificativa) {
		dao.deletar(justificativa);
	}

	public JustificativaPonto recuperar(
            JustificativaPonto justificativa) {
		return dao.recuperar(justificativa);
	}

    @Override
    public JustificativaPonto recuperar(Serializable id) {
        JustificativaPonto j = dao.recuperar(id);
        dao.initialize(j.getHistorico());
        return j;
    }

    public List<JustificativaPonto> todas() {
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
