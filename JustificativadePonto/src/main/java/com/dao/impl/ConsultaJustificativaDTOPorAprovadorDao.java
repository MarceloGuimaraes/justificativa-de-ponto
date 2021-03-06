package com.dao.impl;

import com.dao.Dao;
import com.dao.IConsultaFiltradaPaginadaDao;
import com.domain.dto.JustificativaPontoDTO;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.hibernate.transform.AliasToBeanResultTransformer;

import java.util.List;

/**
 * User: xonda
 * Date: 21/07/13
 * Time: 14:46
 */
public class ConsultaJustificativaDTOPorAprovadorDao extends Dao implements IConsultaFiltradaPaginadaDao<JustificativaPontoDTO, User> {

    protected ConsultaJustificativaDTOPorAprovadorDao(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<JustificativaPontoDTO> todos(User filtro, int startIndex, int pageSize) {
        String hql = "select j.id as id, " +
                "j.data as data, " +
                "j.dataSolicitacao as dataSolicitacao, " +
                "j.dataSolicitacaoFim as dataSolicitacaoFim, " +
                "j.solicitante as userSolicitante, " +
                "j.hrIni as hrIni, " +
                "j.hrFim as hrFim, " +
                "j.descricao as descricao, " +
                "j.status as status, " +
                "j.motivo as motivo, " +
                "j.tipofalta as tipofalta, " +
                "j.tipobancohoras as tipobancohoras, " +
                "j.tipofaltamarcacao as tipofaltamarcacao, " +
                "j.tipoDecisao as tipoDecisao, " +
                "j.obsRh as obsRh " +
                "from JustificativaPonto j " +
                "join j.historico h " +
                "where h.data = (select max(hh.data) from HistoricoJustificativaPonto hh where hh.justificativaPonto = j) " +
                "and h.class = EncaminhamentoJustificativaPonto " +
                "and h.tipoEvento != :tipoEvento " +
                "and h.responsavel.id = :responsavel " +
                "order by j.solicitante.nome";

        final Query query = getSession().createQuery(hql)
                .setFirstResult(startIndex)
                .setMaxResults(pageSize)
                .setParameter("tipoEvento", TipoEventoJustificativaPontoEnum.ENVIADO_SOLICITANTE)
                .setParameter("responsavel", filtro.getId())
                .setResultTransformer(new AliasToBeanResultTransformer(JustificativaPontoDTO.class));

        return query.list();
    }

    @Override
    public long count(User filtro) {
        String hql = "select count(j.id) from JustificativaPonto j " +
                "join j.historico h " +
                "where h.data = (select max(hh.data) from HistoricoJustificativaPonto hh where hh.justificativaPonto = j) " +
                "and h.class = EncaminhamentoJustificativaPonto " +
                "and h.tipoEvento != :tipoEvento " +
                "and h.responsavel.id = :responsavel";
        final Query query = getSession().createQuery(hql)
                .setParameter("tipoEvento", TipoEventoJustificativaPontoEnum.ENVIADO_SOLICITANTE)
                .setParameter("responsavel", filtro.getId());

        return  (Long)query.uniqueResult();
    }
}
