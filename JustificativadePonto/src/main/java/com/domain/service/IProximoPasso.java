package com.domain.service;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.model.JustificativaPonto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

public interface IProximoPasso {
    boolean isIntercepted(JustificativaPonto justificativa);
    @Transactional(readOnly = false)
    void proximo(JustificativaPontoDTO justificativa);
    List<CadastroUsuario> listaCandidatos();
    Map<String, Boolean> retornaHandler();
}
