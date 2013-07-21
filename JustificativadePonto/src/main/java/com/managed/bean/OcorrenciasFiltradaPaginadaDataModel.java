package com.managed.bean;

import com.domain.dto.filtro.FiltroJustificativa;
import com.domain.dto.relatorio.Ocorrencia;
import com.jsf.model.PaginaFiltradaDataModel;
import com.service.IConsultaFiltradaPaginadaService;

/**
 * User: xonda
 * Date: 20/07/13
 * Time: 22:42
 */
public class OcorrenciasFiltradaPaginadaDataModel extends PaginaFiltradaDataModel<Ocorrencia, FiltroJustificativa> {
    public OcorrenciasFiltradaPaginadaDataModel(IConsultaFiltradaPaginadaService<Ocorrencia, FiltroJustificativa> service, FiltroJustificativa filtro) {
        super(service, filtro);
    }
}
