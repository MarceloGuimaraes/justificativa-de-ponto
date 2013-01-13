package com.domain.service;

import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.handler.HandlerProximoPassoManagedBean;

public interface IWorkflow {
    HandlerProximoPassoManagedBean retornaProximoPasso(JustificativaPontoDTO justificativa);

    HandlerProximoPassoManagedBean retornaCancelamento();
}
