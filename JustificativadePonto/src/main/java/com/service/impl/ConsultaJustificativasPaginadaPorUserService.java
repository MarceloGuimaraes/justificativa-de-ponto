package com.service.impl;

import com.dao.IConsultaFiltradaPaginadaDao;
import com.domain.dto.JustificativaPontoGrid;
import com.managed.bean.IPermissoesBean;
import com.model.User;
import com.service.IConsultaPaginadaService;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class ConsultaJustificativasPaginadaPorUserService implements IConsultaPaginadaService<JustificativaPontoGrid> {

    private IConsultaFiltradaPaginadaDao<JustificativaPontoGrid, User> dao;

    private IPermissoesBean permissoes;

    private Mapper mapper;

    public ConsultaJustificativasPaginadaPorUserService(IConsultaFiltradaPaginadaDao<JustificativaPontoGrid, User> dao,
                                                        IPermissoesBean permissoes,
                                                        Mapper mapper) {
        this.dao = dao;
        this.permissoes = permissoes;
        this.mapper = mapper;
    }

    @Override
    public List<JustificativaPontoGrid> todas(int startIndex, int pageSize) {
        if(permissoes.getUsuarioLogado()==null){
            throw new IllegalStateException("O usuario logado nao foi informado");
        }

        User user = mapper.map(permissoes.getUsuarioLogado(), User.class);

        return dao.todos(user, startIndex, pageSize);
    }

    @Override
    public long count() {
        if(permissoes.getUsuarioLogado()==null){
            throw new IllegalStateException("O usuario logado nao foi informado");
        }

        User user = mapper.map(permissoes.getUsuarioLogado(), User.class);

        return dao.count(user);
    }
}
