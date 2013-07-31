package com.service.impl;

import com.dao.IConsultaPaginadaDao;
import com.domain.dto.JustificativaPontoGrid;
import com.service.IConsultaPaginadaService;

import java.util.List;

public class ConsultaJustificativasPaginadaService implements IConsultaPaginadaService<JustificativaPontoGrid> {

    private IConsultaPaginadaDao<JustificativaPontoGrid> dao;

    public ConsultaJustificativasPaginadaService(IConsultaPaginadaDao<JustificativaPontoGrid> dao) {
        this.dao = dao;
    }

    @Override
    public List<JustificativaPontoGrid> todas(int startIndex, int pageSize) {
        return dao.todos(startIndex, pageSize);
    }

    @Override
    public long count() {
        return dao.count();
    }
}
