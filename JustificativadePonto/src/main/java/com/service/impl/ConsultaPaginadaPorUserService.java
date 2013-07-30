package com.service.impl;

import com.dao.IConsultaFiltradaPaginadaDao;
import com.managed.bean.IPermissoesBean;
import com.model.User;
import com.service.IConsultaPaginadaService;
import org.dozer.Mapper;

import java.util.List;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 16:14
 */
public abstract class ConsultaPaginadaPorUserService<T> implements IConsultaPaginadaService<T> {
    private IConsultaFiltradaPaginadaDao<T, User> dao;

    private IPermissoesBean permissoes;

    private Mapper mapper;

    public ConsultaPaginadaPorUserService(IConsultaFiltradaPaginadaDao<T, User> dao,
                                          IPermissoesBean permissoes,
                                          Mapper mapper) {
        this.dao = dao;
        this.permissoes = permissoes;
        this.mapper = mapper;
    }

    @Override
    public List<T> todas(int startIndex, int pageSize) {
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
