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
    private boolean rh;

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
		System.out.println("editAguardaAprovCoord 1");
		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		System.out.println("editAguardaAprovCoord 2");
		if (justificativa.getStatus() == null) {
			return false;
		}
		System.out.println("editAguardaAprovCoord 3");
		System.out.println("STATUS JUSTIFICATIVA=> "
				+ justificativa.getStatus().toString());
		System.out.println("STATUS COMPARAÇÃO=> "
				+ StatusEnum.APROVCOORD.toString());
		System.out.println("COORDENADOR JUSTIFICATIVA=> "
				+ justificativa.getCoordenador().getNome().toString());
		System.out.println("USUÁRIO LOGADO COMPARAÇÃO=> "
				+ usuarioLogado.getNome().toString());

		// como o coordenador já foi selecionado, não faço validação
		if (justificativa.getStatus().equals(StatusEnum.APROVCOORD)) {
			System.out.println("editAguardaAprovCoord 33333333");
			/* return true; */
		}
		if (justificativa.getCoordenador().equals(usuarioLogado)) {
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
		System.out.println("editAguardaAprovSuperintendente 1");

		if (justificativa == null || !isUsuarioLogado) {
			return false;
		}
		System.out.println("editAguardaAprovSuperintendente 2");
		if (justificativa.getStatus() == null) {
			return false;
		}
		System.out.println("editAguardaAprovSuperintendente 3");
		if (justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE)
				&& justificativa.getSuperintendente().equals(usuarioLogado)) {
			System.out.println("editAguardaAprovSuperintendente 4");
			return true;
		} else {
			System.out.println("editAguardaAprovSuperintendente 5");
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

		if ((justificativa.getStatus().equals(StatusEnum.APROVCOORD) && justificativa
				.getCoordenador().equals(usuarioLogado))
				|| (justificativa.getStatus().equals(StatusEnum.APROVCOORD) && justificativa
						.getCoordenador().equals(usuarioLogado))
				|| (justificativa.getStatus().equals(
						StatusEnum.APROVSUPERINTENDENTE) && justificativa
						.getSuperintendente().equals(usuarioLogado))
				|| this.isAdmin()) {
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
