package com.dao;

import java.util.List;

import com.model.JustificativaPonto;
import org.hibernate.criterion.Order;


public interface IJustificativaDAO extends ICrudDao<JustificativaPonto> {

	JustificativaPonto recuperar(JustificativaPonto justificativa);
	List<JustificativaPonto> todos();
    List<JustificativaPonto> todosPorData(int startIndex, int pageSize);
    List<JustificativaPonto> todosPorMotivo(int startIndex, int pageSize);
    List<JustificativaPonto> todosPorSolicitante(int startIndex, int pageSize);
    int count();
}
