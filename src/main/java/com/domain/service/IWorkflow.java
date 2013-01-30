package com.domain.service;

import com.domain.dto.AcessoJustificativa;
import com.model.JustificativaPonto;

public interface IWorkflow {
    AcessoJustificativa verificaAcesso(JustificativaPonto justificativa);
    void enviarCoordenador(JustificativaPonto justificativa, Integer idCoordenador);
    void enviarSuperintendente(JustificativaPonto justificativa, Integer idSuperintendente);
    void enviarRh(JustificativaPonto justificativa, Integer idRh);
    void concluir(JustificativaPonto justificativa);
    void cancelar(JustificativaPonto justificativa);
}
