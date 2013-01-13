package com.managed.bean;

import com.domain.dto.UsuarioLogado;

import java.io.Serializable;

public interface IPermissoesBean extends Serializable {
    UsuarioLogado getUsuarioLogado();
    void setUsuarioLogado(UsuarioLogado usuarioLogado);
    void logOut();
    boolean isLogged();
    boolean isSupport();
    boolean isAdmin();
    boolean isRh();
}
