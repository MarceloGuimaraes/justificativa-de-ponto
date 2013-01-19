package com.domain.service;

import com.domain.dto.JustificativaPontoDTO;

public interface IWorkflow {
    public static final String PASSO_ENVIARCOORDENADOR = "1";
    public static final String PASSO_ENVIARSUPERINTENDENTE = "2";
    public static final String PASSO_ENVIARRH = "3";
    public static final String PASSO_CONCLUIR = "4";
    public static final String PASSO_CANCELAR = "5";

    IProximoPasso retornaProximoPasso(JustificativaPontoDTO justificativa);
    IProximoPasso recupera(String identifier);
}
