package com.managed.bean;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

import com.model.JustificativaPonto;
import com.model.MotivoEnum;
import com.model.PerfilEnum;
import com.model.StatusEnum;
import com.model.User;
import com.util.JavaMailApp;
import com.util.JsfUtil;

@SessionScoped
@ManagedBean(name = "PermissoesBean")
public class PermissoesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private User usuarioLogado;

	private boolean isUsuarioLogado;

    @ManagedProperty(value = "#{mailApp}")
	private JavaMailApp javaMail;

	public void setJavaMail(JavaMailApp javaMail) {
		this.javaMail = javaMail;
	}

	public PermissoesBean() {
		this.usuarioLogado = JsfUtil.getValueExpression(User.class,
				"#{usuarioLogado}");
		this.isUsuarioLogado = usuarioLogado != null;
	}

	public User getUsuarioLogado() {
		return usuarioLogado;
	}

	public Boolean isAdmin() {
		if (!isUsuarioLogado) {
			return false;
		}
		if (usuarioLogado.getPerfil().contains(PerfilEnum.ADMINISTRADOR)) {
			return true;
		} else {
			return false;
		}

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
			System.out.println("editAguardaAprovCoord 4");
			return true;
		} else {
			System.out.println("editAguardaAprovCoord 5");
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

	/***************** REGRAs DE OCULTAÇÃO *********************/

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
	 * EXIBE AS OPÇÕES DO MOTIVO SELECIONADO BANCO DE HORAS 'HORA EXTRA' E
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

}
