package com.service;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.UsuarioLogado;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;

import java.io.Serializable;

public interface IJustificativaService {

    JustificativaPonto recuperar(JustificativaPontoDTO justificativa);
    JustificativaPontoDTO recuperar(Serializable id);
    JustificativaPonto adicionar(JustificativaPonto justificativa);
    JustificativaPonto atualizar(JustificativaPonto justificativa);
    void apagar(JustificativaPonto justificativa);

    JustificativaPontoDTO adicionar(JustificativaPontoDTO justificativa);
    JustificativaPontoDTO atualizar(JustificativaPontoDTO justificativa);
    JustificativaPontoDTO mudaSituacao(UsuarioLogado usuarioLogado, UsuarioLogado proximoResponsavel, JustificativaPontoDTO justificativaPonto, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum eventoHistorico);
    JustificativaPontoDTO atua(UsuarioLogado usuarioLogado, JustificativaPontoDTO justificativaPonto, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum tipoEvento);

}
