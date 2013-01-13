package com.managed.bean;

import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.handler.HandlerMotivosManagedBean;
import com.managed.bean.handler.HandlerProximoPassoManagedBean;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.List;

public interface IJustificativaManagedBean extends Serializable {
    JustificativaPontoDTO getJustificativa();
    void setJustificativa(JustificativaPontoDTO justificativa);

    HandlerProximoPassoManagedBean getProximoPasso();
    HandlerMotivosManagedBean getHandler();
    List<SelectItem> getTipoBancoHorasList();
    List<SelectItem> getTipoDecisaoList();
    void setTipoBancoHorasList(List<SelectItem> tipoBancoHorasList);
    void setTipoDecisaoList(List<SelectItem> tipoDecisaoList);
    String proximo();
    void cancelado(ActionEvent event);
    String getLabelCadastro();
}
