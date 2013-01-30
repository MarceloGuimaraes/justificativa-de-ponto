package com.managed.bean;

import com.domain.dto.UsuarioLogado;
import com.model.PerfilEnum;

import java.io.Serializable;

public class PermissoesBean implements IPermissoesBean, Serializable {

	private static final long serialVersionUID = 1L;

	private UsuarioLogado usuarioLogado;

	private boolean isUsuarioLogado;

	private boolean support;
	private boolean admin;
	private boolean rh;

	public PermissoesBean() {
		isUsuarioLogado = false;
	}

	public UsuarioLogado getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(UsuarioLogado usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		support = usuarioLogado.getPerfil().contains(PerfilEnum.SUPORTE);
        admin = usuarioLogado.getPerfil().contains(PerfilEnum.ADMINISTRADOR);
        rh = usuarioLogado.getPerfil().contains(PerfilEnum.RH);
        this.isUsuarioLogado = true;
    }

    @Override
    public void logOut() {
        usuarioLogado = null;
        isUsuarioLogado = false;
    }

    @Override
    public boolean isLogged() {
        return isUsuarioLogado;
    }

    @Override
	public boolean isSupport() {
		return support;
	}

    @Override
	public boolean isAdmin() {
		return admin;
	}

	@Override
	public boolean isRh() {
		return rh;
	}
}
