package com.managed.bean;

import com.domain.dto.UsuarioLogado;

public interface IPermissoesBean {
    UsuarioLogado getUsuarioLogado();
    void setUsuarioLogado(UsuarioLogado usuarioLogado);
    void logOut();
    boolean isLogged();
    boolean isSupport();
    boolean isAdmin();
    boolean isRh();
}
