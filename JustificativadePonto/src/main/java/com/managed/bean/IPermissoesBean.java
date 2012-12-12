package com.managed.bean;

import com.model.JustificativaPonto;
import com.model.User;
import com.util.JavaMailApp;

public interface IPermissoesBean {
    User getUsuarioLogado();
    void setUsuarioLogado(User usuarioLogado);
    boolean isSupport();
    boolean isAdmin();
    boolean isRh();

    boolean editElaboracao(JustificativaPonto justificativa);
    boolean editAguardaAprovCoord(JustificativaPonto justificativa);
    boolean editAguardaAprovSuperintendente(
            JustificativaPonto justificativa);
    boolean editAguardaAprovRh(JustificativaPonto justificativa);
    boolean showFldCancelar(JustificativaPonto justificativa);
    boolean showFldConcluir(JustificativaPonto justificativa);
    boolean isShowMenuCadUser();
}
