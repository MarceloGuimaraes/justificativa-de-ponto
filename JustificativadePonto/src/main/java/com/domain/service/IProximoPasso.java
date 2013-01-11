package com.domain.service;

import com.domain.dto.JustificativaPontoDTO;
import com.model.JustificativaPonto;
import org.springframework.transaction.annotation.Transactional;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

public interface IProximoPasso extends Serializable {
    Integer getId();
    void setId(Integer id);
    List<SelectItem> getEscolhas();
    void setEscolhas(List<SelectItem> escolhas);
    boolean isTemProximoPasso();
    boolean isPermiteEditar();
    boolean isPermiteCancelar();
    boolean isConcluir();
    boolean isIntercepted(JustificativaPonto justificativa);
    @Transactional(readOnly = false)
    void proximo(JustificativaPontoDTO justificativa);
}
