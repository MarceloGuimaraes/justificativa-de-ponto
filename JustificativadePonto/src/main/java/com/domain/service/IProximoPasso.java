package com.domain.service;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.handler.HandlerProximoPassoManagedBean;
import com.model.JustificativaPonto;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

public interface IProximoPasso extends Serializable {
    boolean isIntercepted(JustificativaPonto justificativa);
    @Transactional(readOnly = false)
    void proximo(JustificativaPontoDTO justificativa, Integer id);
    List<CadastroUsuario> listaCandidatos();
    HandlerProximoPassoManagedBean retornaHandler();
}
