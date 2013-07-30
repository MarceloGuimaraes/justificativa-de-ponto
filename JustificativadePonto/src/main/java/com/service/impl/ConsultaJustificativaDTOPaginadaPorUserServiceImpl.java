package com.service.impl;

import com.dao.IConsultaFiltradaPaginadaDao;
import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.IPermissoesBean;
import com.model.User;
import org.dozer.Mapper;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 16:18
 */
public class ConsultaJustificativaDTOPaginadaPorUserServiceImpl extends ConsultaPaginadaPorUserService<JustificativaPontoDTO> {
    public ConsultaJustificativaDTOPaginadaPorUserServiceImpl(IConsultaFiltradaPaginadaDao<JustificativaPontoDTO, User> dao,
                                                              IPermissoesBean permissoes,
                                                              Mapper mapper) {
        super(dao, permissoes, mapper);
    }
}
