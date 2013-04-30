package com.service.impl;

import com.dao.impl.ConsultaOcorrenciasDao;
import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.service.IConsultaOcorrenciasService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * User: xonda
 * Date: 4/29/13
 * Time: 11:06 PM
 */
@Transactional(readOnly = true)
public class ConsultaOcorrenciasService implements IConsultaOcorrenciasService {
    private final ConsultaOcorrenciasDao consultaOcorrenciasDao;

    public ConsultaOcorrenciasService(ConsultaOcorrenciasDao consultaOcorrenciasDao) {
        this.consultaOcorrenciasDao = consultaOcorrenciasDao;
    }

    @Override
    public List<Ocorrencia> pesquisar(final FiltroJustificativa filtro) {
        return consultaOcorrenciasDao.pesquisar(filtro);
    }
}
