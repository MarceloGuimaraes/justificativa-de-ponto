package com.domain.service;

import com.domain.dto.JustificativaPontoDTO;

public interface IWorkflow {
    void cancelar(JustificativaPontoDTO justificativa);
    IProximoPasso retornaProximoPasso(JustificativaPontoDTO justificativa);
}
