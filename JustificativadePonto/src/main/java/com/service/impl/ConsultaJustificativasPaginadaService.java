package com.service.impl;

import com.dao.IConsultaJustificativaPontoDao;
import com.dao.IJustificativaDAO;
import com.model.JustificativaPonto;
import com.model.User;
import com.service.IConsultaPaginadaService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public class ConsultaJustificativasPaginadaService implements IConsultaPaginadaService<JustificativaPonto> {

    IConsultaJustificativaPontoDao dao;

    public ConsultaJustificativasPaginadaService(IConsultaJustificativaPontoDao dao) {
        this.dao = dao;
    }

    @Override
    public List<JustificativaPonto> todas(int startIndex, int pageSize) {
        return dao.todos(startIndex, pageSize);
    }

    @Override
    public int count() {
        return dao.count();
    }
}
