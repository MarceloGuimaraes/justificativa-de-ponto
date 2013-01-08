package com.domain.service;

import com.domain.dto.AcessoJustificativa;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.ProximoPasso;
import com.model.JustificativaPonto;
import com.service.IUserService;

public interface IWorkflow {
    void enviarCoordenador(JustificativaPontoDTO justificativa, Integer idCoordenador);
    void enviarSuperintendente(JustificativaPontoDTO justificativa, Integer idSuperintendente);
    void enviarRh(JustificativaPontoDTO justificativa, Integer idRh);
    void concluir(JustificativaPontoDTO justificativa);
    void cancelar(JustificativaPontoDTO justificativa);
    IUserService getUserService();
    ProximoPasso retornaProximoPasso(JustificativaPontoDTO justificativa);
}
