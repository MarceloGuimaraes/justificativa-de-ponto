package com.service.impl;

import com.dao.IConsultaFiltradaPaginadaDao;
import com.domain.dto.JustificativaPontoGrid;
import com.managed.bean.IPermissoesBean;
import com.model.User;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public class ConsultaJustificativasPaginadaPorUserService extends ConsultaPaginadaPorUserService<JustificativaPontoGrid> {
    public ConsultaJustificativasPaginadaPorUserService(IConsultaFiltradaPaginadaDao<JustificativaPontoGrid, User> dao,
                                                        IPermissoesBean permissoes,
                                                        Mapper mapper) {
        super(dao, permissoes, mapper);
    }
}
