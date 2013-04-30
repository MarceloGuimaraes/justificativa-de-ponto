package com.dao.impl;

import com.dao.Dao;
import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.model.JustificativaPonto;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

/**
 * User: xonda
 * Date: 4/29/13
 * Time: 10:36 PM
 */
public class ConsultaOcorrenciasDao extends Dao {
    protected ConsultaOcorrenciasDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public List<Ocorrencia> pesquisar(final FiltroJustificativa filtro){
        final Criteria criteria = getSession().createCriteria(JustificativaPonto.class, "j");
        filtro.isValid();
        if(filtro.isIdFuncionarioInformado()){
            criteria.add(Restrictions.eq("j.solicitante.id", filtro.getIdFuncionario()));
        }
        if(filtro.isInicioInformado()){
            criteria.add(Restrictions.ge("j.dataSolicitacao", filtro.getInicio()));
        }
        if(filtro.isTerminoInformado()){
            criteria.add(Restrictions.le("j.dataSolicitacao", filtro.getTermino()));
        }
        if(filtro.isStatusInformado()){
            criteria.add(Restrictions.eq("j.status", filtro.getStatus()));
        }
        criteria.setProjection(
                Projections.projectionList()
                        .add(Projections.property("j.dataSolicitacao"), "data")
                        .add(Projections.alias(Projections.property("j.hrIni"), "horaInicio"))
                        .add(Projections.alias(Projections.property("j.hrFim"), "horaTermino"))
                        .add(Projections.alias(Projections.property("j.motivo"), "tipoMotivo"))
                        .add(Projections.alias(Projections.property("j.tipofalta"), "tipoFalta"))
                        .add(Projections.alias(Projections.property("j.tipobancohoras"), "tipoBancoHoras"))
                        .add(Projections.alias(Projections.property("j.tipofaltamarcacao"), "tipoFaltaMarcacao"))
                        .add(Projections.alias(Projections.property("j.status"), "tipoStatus"))
        );
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Ocorrencia.class));
        List<Ocorrencia> resultado = criteria.list();
        return resultado;
    }
}
