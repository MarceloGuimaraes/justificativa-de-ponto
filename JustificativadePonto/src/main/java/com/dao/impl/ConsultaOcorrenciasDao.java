package com.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import com.dao.Dao;
import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.model.JustificativaPonto;

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
        filtro.isValid();
        final Criteria criteria = getSession().createCriteria(JustificativaPonto.class, "j");
        criteria.createAlias("j.solicitante", "s");
        if(filtro.isIdFuncionarioInformado()){
            criteria.add(Restrictions.eq("s.id", filtro.getIdFuncionario()));
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
                        .add(Projections.property("s.nome"), "solicitante")
                        .add(Projections.property("j.dataSolicitacao"), "data")
                        .add(Projections.property("j.hrIni"), "horaInicio")
                        .add(Projections.property("j.hrFim"), "horaTermino")
                        .add(Projections.property("j.motivo"), "tipoMotivo")
                        .add(Projections.property("j.tipofalta"), "tipoFalta")
                        .add(Projections.property("j.tipobancohoras"), "tipoBancoHoras")
                        .add(Projections.property("j.tipofaltamarcacao"), "tipoFaltaMarcacao")
                        .add(Projections.property("j.status"), "tipoStatus")
                         .add(Projections.property("j.tipoDecisao"), "decisaoEnum")
        );
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Ocorrencia.class));
        final List<Ocorrencia> resultado = criteria.list();
        return resultado;
    }
}
