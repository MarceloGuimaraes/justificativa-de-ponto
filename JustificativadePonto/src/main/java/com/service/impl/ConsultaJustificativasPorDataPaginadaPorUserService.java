package com.service.impl;

import com.dao.IConsultaJustificativaPontoPorUsuario;
import com.managed.bean.IPermissoesBean;
import com.model.JustificativaPonto;
import com.service.IConsultaPaginadaService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class ConsultaJustificativasPorDataPaginadaPorUserService implements IConsultaPaginadaService<JustificativaPonto> {

    IConsultaJustificativaPontoPorUsuario dao;

    IPermissoesBean permissoes;

    public ConsultaJustificativasPorDataPaginadaPorUserService(IConsultaJustificativaPontoPorUsuario dao,
                                                               IPermissoesBean permissoes) {
        this.dao = dao;
        this.permissoes = permissoes;
    }

    @Override
    public List<JustificativaPonto> todas(int startIndex, int pageSize) {
        if(permissoes.getUsuarioLogado()==null){
            throw new IllegalStateException("O usuario logado nao foi informado");
        }
        return dao.todosPorData(startIndex, pageSize, permissoes.getUsuarioLogado());
    }

    @Override
    public int count() {
        if(permissoes.getUsuarioLogado()==null){
            throw new IllegalStateException("O usuario logado nao foi informado");
        }
        return dao.count(permissoes.getUsuarioLogado());
    }
}
