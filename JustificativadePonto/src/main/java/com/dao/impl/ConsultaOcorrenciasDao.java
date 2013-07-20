package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaFiltradaPaginadaDao;
import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.model.JustificativaPonto;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

/**
 * User: xonda
 * Date: 4/29/13
 * Time: 10:36 PM
 */
public class ConsultaOcorrenciasDao extends Dao implements IConsultaFiltradaPaginadaDao<Ocorrencia, FiltroJustificativa> {
    protected ConsultaOcorrenciasDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Ocorrencia> todos(FiltroJustificativa filtro, int startIndex, int pageSize) {
        final Criteria criteria = getSession().createCriteria(JustificativaPonto.class, "j");
        criteria.createAlias("j.solicitante", "s");
        aplicaFiltro(criteria, filtro);
        criteria.setProjection(
                Projections.projectionList()
                        .add(Projections.property("s.nome"), "solicitante")
                        .add(Projections.property("j.dataSolicitacao"), "data")
                        .add(Projections.property("j.dataSolicitacaoFim"), "dataFim")
                        .add(Projections.property("j.hrIni"), "horaInicio")
                        .add(Projections.property("j.hrFim"), "horaTermino")
                        .add(Projections.property("j.motivo"), "tipoMotivo")
                        .add(Projections.property("j.tipofalta"), "tipoFalta")
                        .add(Projections.property("j.tipobancohoras"), "tipoBancoHoras")
                        .add(Projections.property("j.tipofaltamarcacao"), "tipoFaltaMarcacao")
                        .add(Projections.property("j.status"), "tipoStatus")
                        .add(Projections.property("j.tipoDecisao"), "tipoDecisaoEnum")
        );
        criteria.setResultTransformer(new AliasToBeanResultTransformer(Ocorrencia.class));
        criteria.setFirstResult(startIndex);
        criteria.setMaxResults(pageSize);

        final List<Ocorrencia> resultado = criteria.list();
        return resultado;
    }

    @Override
    public long count(FiltroJustificativa filtro) {
        final Criteria criteria = getSession().createCriteria(JustificativaPonto.class, "j");
        criteria.createAlias("j.solicitante", "s");
        aplicaFiltro(criteria, filtro);
        criteria.setProjection(
                Projections.rowCount()
        );
        return (Long)criteria.uniqueResult();
    }

    private void aplicaFiltro(Criteria criteria, final FiltroJustificativa filtro){
        filtro.isValid();
        if(filtro.isIdFuncionarioInformado()){
            criteria.add(Restrictions.eq("s.id", filtro.getIdFuncionario()));
        }
        if(filtro.isInicioInformado()){
            criteria.add(
                    Restrictions.disjunction()
                            .add(
                                    Restrictions.conjunction()
                                            .add(Restrictions.isNull("j.dataSolicitacaoFim"))
                                            .add(Restrictions.ge("j.dataSolicitacao", filtro.getInicio()))
                            )
                            .add(
                                    Restrictions.conjunction()
                                            .add(Restrictions.isNotNull("j.dataSolicitacaoFim"))
                                            .add(Restrictions.ge("j.dataSolicitacaoFim", filtro.getInicio()))
                            )
            );
        }
        if(filtro.isTerminoInformado()){
            criteria.add(Restrictions.le("j.dataSolicitacao", filtro.getTermino()));
        }
        if(filtro.isStatusInformado()){
            criteria.add(Restrictions.eq("j.status", filtro.getStatus()));
        }
    }
}
