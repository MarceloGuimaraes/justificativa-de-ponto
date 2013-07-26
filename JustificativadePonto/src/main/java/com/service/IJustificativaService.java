package com.service;

import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;

import java.io.Serializable;

public interface IJustificativaService {

    JustificativaPonto adicionar(JustificativaPonto justificativa);
    JustificativaPonto atualizar(JustificativaPonto justificativa);
    void encaminha(User usuarioLogado, User proximoResponsavel, JustificativaPonto justificativaPonto, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum eventoHistorico);
    void atua(User usuarioLogado, JustificativaPonto justificativaPonto, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum tipoEvento);

    JustificativaPonto recuperar(Serializable id);
    JustificativaPonto recuperar(Serializable id, String ... atributos);
}
