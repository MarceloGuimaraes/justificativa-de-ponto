package com.service.impl;

import com.dao.IJustificativaDAO;
import com.model.JustificativaPonto;
import com.model.User;
import com.service.IConsultaPaginadaService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class ConsultaJustificativasPorDataPaginadaService implements IConsultaPaginadaService<JustificativaPonto> {

    IJustificativaDAO dao;

    public ConsultaJustificativasPorDataPaginadaService(IJustificativaDAO dao) {
        this.dao = dao;
    }

    @Override
    public List<JustificativaPonto> todas(int startIndex, int pageSize) {
        return dao.todosPorData(startIndex, pageSize);
    }

    @Override
    public int count() {
        return dao.count();
    }
}
