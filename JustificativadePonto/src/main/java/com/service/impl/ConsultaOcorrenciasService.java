package com.service.impl;

import com.dao.impl.ConsultaOcorrenciasDao;
import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.service.IConsultaFiltradaPaginadaService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: xonda
 * Date: 4/29/13
 * Time: 11:06 PM
 */
public class ConsultaOcorrenciasService implements IConsultaFiltradaPaginadaService<Ocorrencia, FiltroJustificativa> {
    private final ConsultaOcorrenciasDao consultaOcorrenciasDao;

    public ConsultaOcorrenciasService(ConsultaOcorrenciasDao consultaOcorrenciasDao) {
        this.consultaOcorrenciasDao = consultaOcorrenciasDao;
    }

    @Override
    public List<Ocorrencia> todas(FiltroJustificativa filtro, int startIndex, int pageSize) {
        return consultaOcorrenciasDao.todos(filtro, startIndex, pageSize);
    }

    @Override
    public long count(FiltroJustificativa filtro) {
        return consultaOcorrenciasDao.count(filtro);
    }
}
