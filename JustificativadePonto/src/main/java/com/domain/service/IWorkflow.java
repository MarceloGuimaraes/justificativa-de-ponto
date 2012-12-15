package com.domain.service;

import com.domain.dto.AcessoJustificativa;
import com.model.JustificativaPonto;

public interface IWorkflow {
    AcessoJustificativa verificaAcesso(JustificativaPonto justificativa);
}
