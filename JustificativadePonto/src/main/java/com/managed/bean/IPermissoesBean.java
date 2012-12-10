package com.managed.bean;

import com.model.User;

public interface IPermissoesBean {
    User getUsuarioLogado();
    void setUsuarioLogado(User usuarioLogado);
    boolean isSupport();
    boolean isAdmin();
}
