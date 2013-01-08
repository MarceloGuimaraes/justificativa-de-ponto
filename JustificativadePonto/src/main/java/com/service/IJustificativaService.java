package com.service;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.UsuarioLogado;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;

import java.io.Serializable;

public interface IJustificativaService {

    JustificativaPontoDTO nova(UsuarioLogado usuarioLogado);
    JustificativaPonto recuperar(JustificativaPontoDTO justificativa);
    JustificativaPontoDTO recuperar(Serializable id);
    void adicionar(JustificativaPonto justificativa);
    void atualizar(JustificativaPonto justificativa);
    void apagar(JustificativaPonto justificativa);

    JustificativaPontoDTO mudaSituacao(UsuarioLogado usuarioLogado, UsuarioLogado proximoResponsavel, JustificativaPontoDTO justificativaPonto, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum eventoHistorico);
    JustificativaPontoDTO atua(UsuarioLogado usuarioLogado, JustificativaPontoDTO justificativaPonto, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum tipoEvento);

    void cancelar(UsuarioLogado usuarioLogado, JustificativaPontoDTO justificativa);
}
