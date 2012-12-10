package com.managed.bean;

import com.model.*;
import com.util.JavaMailApp;

import javax.faces.bean.ManagedProperty;
import java.io.Serializable;

public class PermissoesBean implements IPermissoesBean, Serializable {

	private static final long serialVersionUID = 1L;

	private User usuarioLogado;

	private boolean isUsuarioLogado;

    @ManagedProperty(value = "#{mailApp}")
	private JavaMailApp javaMail;

    private boolean support;
    private boolean admin;

    public void setJavaMail(JavaMailApp javaMail) {
		this.javaMail = javaMail;
	}

	public PermissoesBean() {
        isUsuarioLogado = false;
	}

	public User getUsuarioLogado() {
		return usuarioLogado;
	}

    public void setUsuarioLogado(User usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
        this.isUsuarioLogado = usuarioLogado != null;
        support = PerfilEnum.SUPORTE.equals(usuarioLogado.getPerfil());
        admin = PerfilEnum.ADMINISTRADOR.equals(usuarioLogado.getPerfil());
    }

    /************************* CONTROLE DE EDI��O ************************************************/

	/*
	 * Solicitante poder�: - a��o 'Enviar para o coordenador' - corpo da
	 * justificativa de ponto at� o campo Justificativa -
	 */
	public boolean editElaboracao(JustificativaPonto justificativa) {

		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		if (justificativa.getStatus() == null) {
			return false;
		}
		if (justificativa.getStatus().equals(StatusEnum.ELABORACAO)
				&& justificativa.getSolicitante().equals(usuarioLogado)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Coordenador poder�: - a��o 'Enviar para superintendente" e "cancelar"' -
	 * selecionar o superintendente
	 */
	public boolean editAguardaAprovCoord(JustificativaPonto justificativa) {
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		if (justificativa.getStatus() == null) {
			return false;
		}
		// como o coordenador j� foi selecionado, n�o fa�o valida��o
		if (justificativa.getStatus().equals(StatusEnum.APROVCOORD)
				&& justificativa.getCoordenador().equals(usuarioLogado)) {
			System.out.println("editAguardaAprovCoord 4");
			return true;
		} else {
			System.out.println("editAguardaAprovCoord 5");
			return false;
		}
	}

	/*
	 * Superintendente poder�: - a��o 'Enviar para
	 * superintendente" e "cancelar"' - selecionar o superintendente
	 */
	public boolean editAguardaAprovSuperintendente(
			JustificativaPonto justificativa) {
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		if (justificativa.getStatus() == null) {
			return false;
		}
		if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE)
				&& justificativa.getSuperintendente().equals(usuarioLogado)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Rh poder�: - a��o 'executar(concluir)" e "cancelar"' ser�o exidas
	 */
	public boolean editAguardaAprovRh(JustificativaPonto justificativa) {
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		if (justificativa.getStatus() == null) {
			return false;
		}
		if (justificativa.getStatus().equals(StatusEnum.EXECUCAORH)
				&& justificativa.getRh().equals(usuarioLogado)) {
			return true;
		} else {
			return false;
		}
	}

	/***************** REGRAs DE OCULTA��O *********************/

	/*
	 * campo cancelar
	 */
	public boolean showFldCancelar(JustificativaPonto justificativa) {
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}

		if (justificativa.getStatus() == null) {
			return false;
		}

		if (justificativa.getStatus().equals(StatusEnum.ELABORACAO)
				|| justificativa.getStatus().equals(StatusEnum.CANCELADO)
				|| justificativa.getStatus().equals(StatusEnum.CANCELADO)) {
			return false;
		}

		if (isAdmin()) {
			return true;
		}

		return true;

	}
	
	/*
	 * campo CONCLUIR
	 */
	public boolean showFldConcluir(JustificativaPonto justificativa) {
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}

		if (justificativa.getStatus() == null) {
			return false;
		}

		if (justificativa.getStatus().equals(StatusEnum.ELABORACAO)
				|| justificativa.getStatus().equals(StatusEnum.CANCELADO)
				|| justificativa.getStatus().equals(StatusEnum.CANCELADO)) {
			return false;
		}

		if (isAdmin()) {
			return true;
		}

		return true;

	}

	

	/*
	 * MENU DE CADASTRO DE USUARIO
	 */
	public boolean showMenuCadUser() {
		if (!isUsuarioLogado) {
			return false;
		}
		if (usuarioLogado.getPerfil().equals(PerfilEnum.CADASTRADOR)
				|| usuarioLogado.getPerfil().equals(PerfilEnum.SUPORTE)) {
			return true;
		} else {
			return false;
		}

	}

	/*
	 * CAMPO 'Causa da falta:'
	 */
	public boolean showFldCausadaFalta(JustificativaPonto justificativa) {
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		if (justificativa.getMotivo().equals(MotivoEnum.FALTAS)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * CAMPO 'Hora final'
	 */
	public boolean hideFldHoraFinal(JustificativaPonto justificativa) {
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		if (justificativa.getMotivo().equals(MotivoEnum.FALTADEMARCACAO)) {
			return false;
		} else {
			return true;
		}
	}

	/*
	 * EXIBE AS OP��ES DO MOTIVO SELECIONADO BANCO DE HORAS 'HORA EXTRA' E
	 * 'GOZO'
	 */
	public boolean showOpcoesBancoHoras(JustificativaPonto justificativa) {
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		if (justificativa.getMotivo().equals(MotivoEnum.BANCODEHORAS)) {
			return true;
		} else {
			return false;
		}
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

    public boolean isSupport() {
        return support;
    }

    public boolean isAdmin() {
        return admin;
    }
}
