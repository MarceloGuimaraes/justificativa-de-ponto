package com.domain.service;

import com.domain.dto.JustificativaPontoDTO;

public interface IWorkflowResolver {
    IProximoPasso retornaProximoPasso(JustificativaPontoDTO justificativa);
}
