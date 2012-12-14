package com.managed.bean;

import com.model.JustificativaPonto;
import com.model.PerfilEnum;
import com.model.StatusEnum;
import com.model.User;

import java.io.Serializable;

public class PermissoesBean implements IPermissoesBean, Serializable {

	private static final long serialVersionUID = 1L;

	private User usuarioLogado;

	private boolean isUsuarioLogado;

	private boolean support;
	private boolean admin;
	private boolean rh;

	public PermissoesBean() {
		isUsuarioLogado = false;
	}

	public User getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(User usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		this.isUsuarioLogado = usuarioLogado != null;
		support = usuarioLogado.getPerfil().contains(PerfilEnum.SUPORTE);
		admin = usuarioLogado.getPerfil().contains(PerfilEnum.ADMINISTRADOR);
		rh = usuarioLogado.getPerfil().contains(PerfilEnum.RH);
	}

	/************************* CONTROLE DE EDIÇÃO ************************************************/

	/*
	 * Solicitante poderá: - ação 'Enviar para o coordenador' - corpo da
	 * justificativa de ponto até o campo Justificativa -
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
	 * Coordenador poderá: - ação 'Enviar para superintendente" e "cancelar"' -
	 * selecionar o superintendente
	 */
	public boolean editAguardaAprovCoord(JustificativaPonto justificativa) {

		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}

		if (justificativa.getStatus() == null) {
			return false;
		}

		// como o coordenador já foi selecionado, não faço validação
		if (justificativa.getStatus().equals(StatusEnum.APROVCOORD)
				&& justificativa.getCoordenador().equals(usuarioLogado)) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Superintendente poderá: - ação 'Enviar para
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
	 * Rh poderá: - ação 'executar(concluir)" e "cancelar"' serão exidas
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

		if ((justificativa.getStatus().equals(StatusEnum.APROVCOORD) && justificativa
				.getCoordenador().equals(usuarioLogado))
				|| (justificativa.getStatus().equals(StatusEnum.APROVCOORD) && justificativa
						.getCoordenador().equals(usuarioLogado))
				|| (justificativa.getStatus().equals(
						StatusEnum.APROVSUPERINTENDENTE) && justificativa
						.getSuperintendente().equals(usuarioLogado))
				|| ((!justificativa.getStatus().equals(StatusEnum.ELABORACAO)) && this
						.isAdmin())) {
			return true;
		} else {
			return false;
		}

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
				|| justificativa.getStatus().equals(StatusEnum.CONCLUIDO)) {
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

	public boolean isShowMenuCadUser() {
		if (!isUsuarioLogado) {
			return false;
		}
		if (usuarioLogado.getPerfil().contains(PerfilEnum.CADASTRADOR)
				|| usuarioLogado.getPerfil().contains(PerfilEnum.SUPORTE)
				|| isAdmin()) {
			return true;
		} else {
			return false;
		}
	}

	public boolean isSupport() {
		return support;
	}

	public boolean isAdmin() {
		return admin;
	}

	@Override
	public boolean isRh() {
		return rh;
	}
}
