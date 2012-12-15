package com.service;

import com.domain.dto.UsuarioLogado;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;

import java.io.Serializable;

public interface IJustificativaService {

    JustificativaPonto nova(UsuarioLogado usuarioLogado);
    JustificativaPonto recuperar(JustificativaPonto justificativa);
    JustificativaPonto recuperar(Serializable id);
    void adicionar(JustificativaPonto justificativa);
    void atualizar(JustificativaPonto justificativa);
    void apagar(JustificativaPonto justificativa);
    void mudaSituacao(JustificativaPonto justificativaPonto, UsuarioLogado usuarioLogado, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum... eventoHistorico);

    void cancelar(UsuarioLogado usuarioLogado, JustificativaPonto justificativa);
}
