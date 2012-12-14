package com.service;

import java.io.Serializable;
import java.util.List;

import com.model.JustificativaPonto;
import com.model.User;

public interface IJustificativaService {

    JustificativaPonto recuperar(JustificativaPonto justificativa);
    JustificativaPonto recuperar(Serializable id);
    void adicionar(JustificativaPonto justificativa);
    void atualizar(JustificativaPonto justificativa);
    void apagar(JustificativaPonto justificativa);

    void cancelar(User user, JustificativaPonto justificativa);
}
