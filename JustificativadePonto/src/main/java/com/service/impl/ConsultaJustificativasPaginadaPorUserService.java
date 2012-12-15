package com.service.impl;

import com.dao.IConsultaJustificativaPontoPorUsuarioDao;
import com.managed.bean.IPermissoesBean;
import com.model.JustificativaPonto;
import com.model.User;
import com.service.IConsultaPaginadaService;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class ConsultaJustificativasPaginadaPorUserService implements IConsultaPaginadaService<JustificativaPonto> {

    IConsultaJustificativaPontoPorUsuarioDao dao;

    IPermissoesBean permissoes;

    Mapper mapper;

    public ConsultaJustificativasPaginadaPorUserService(IConsultaJustificativaPontoPorUsuarioDao dao,
                                                        IPermissoesBean permissoes,
                                                        Mapper mapper) {
        this.dao = dao;
        this.permissoes = permissoes;
        this.mapper = mapper;
    }

    @Override
    public List<JustificativaPonto> todas(int startIndex, int pageSize) {
        if(permissoes.getUsuarioLogado()==null){
            throw new IllegalStateException("O usuario logado nao foi informado");
        }

        User user = mapper.map(permissoes.getUsuarioLogado(), User.class);

        return dao.todos(startIndex, pageSize, user);
    }

    @Override
    public int count() {
        if(permissoes.getUsuarioLogado()==null){
            throw new IllegalStateException("O usuario logado nao foi informado");
        }

        User user = mapper.map(permissoes.getUsuarioLogado(), User.class);

        return dao.count(user);
    }
}
