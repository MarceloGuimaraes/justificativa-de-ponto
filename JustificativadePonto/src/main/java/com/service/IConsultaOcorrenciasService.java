package com.service;

import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;

import java.util.List;

/**
 * User: xonda
 * Date: 4/29/13
 * Time: 11:07 PM
 */
public interface IConsultaOcorrenciasService {
    List<Ocorrencia> pesquisar(FiltroJustificativa filtro);
}
