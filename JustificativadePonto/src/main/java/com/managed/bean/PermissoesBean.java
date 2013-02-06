package com.managed.bean;

import com.domain.dto.UsuarioLogado;
import com.model.PerfilEnum;

public class PermissoesBean implements IPermissoesBean {

	private static final long serialVersionUID = 1L;

	private UsuarioLogado usuarioLogado;

	private boolean isUsuarioLogado;

	private boolean support;
	private boolean admin;
    private boolean coordenador;
    private boolean superintendente;
	private boolean rh;
    private boolean cadastrador;
    private boolean usuario;

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
        coordenador = usuarioLogado.getPerfil().contains(PerfilEnum.COORDENADOR);
        superintendente = usuarioLogado.getPerfil().contains(PerfilEnum.SUPERINTENDENTE);
        rh = usuarioLogado.getPerfil().contains(PerfilEnum.RH);
        cadastrador = usuarioLogado.getPerfil().contains(PerfilEnum.CADASTRADOR);
        usuario = usuarioLogado.getPerfil().contains(PerfilEnum.USUARIO);
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

    public boolean isCadastrador() {
        return cadastrador;
    }

    public boolean isUsuario() {
        return usuario;
    }

    public boolean isCoordenador() {
        return coordenador;
    }

    public boolean isSuperintendente() {
        return superintendente;
    }
}
