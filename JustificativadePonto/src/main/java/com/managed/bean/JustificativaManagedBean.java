package com.managed.bean;

import com.domain.dto.AcessoJustificativa;
import com.domain.dto.exception.BusinessException;
import com.domain.service.IWorkflow;
import com.jsf.ds.impl.ComboTipoBancoHorasDatasourceImpl;
import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.managed.bean.handler.HandlerMotivosManagedBean;
import com.model.JustificativaPonto;
import com.model.User;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.spring.util.ApplicationContextProvider;
import com.util.JsfUtil;
import com.util.Message;
import org.primefaces.context.RequestContext;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class JustificativaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String SUCCESS = "welcome";

	private transient IWorkflow workflow;

	private transient IPermissoesBean permissoes;

	private HandlerMotivosManagedBean handler;

	private List<SelectItem> tipoBancoHorasList;
	private List<SelectItem> tipoDecisaoList;
	private List<SelectItem> coordenadorList;
	private List<SelectItem> superintendenteList;
	private List<SelectItem> rhList;

	private JustificativaPonto justificativa;

	private Integer idCoordenador;
	private Integer idSuperintendente;
	private Integer idRh;

	private AcessoJustificativa acesso;

	public JustificativaManagedBean(IJustificativaService justificativaService,
                                    IUserService userService,
                                    IPermissoesBean permissoes,
                                    IWorkflow workflow) {

		this.workflow = workflow;

		this.permissoes = permissoes;

		tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();

		tipoBancoHorasList = new ComboTipoBancoHorasDatasourceImpl().findObjects();

		coordenadorList = retornaItemAPartirDeUser(userService.recuperaCoordenadores());

		superintendenteList = retornaItemAPartirDeUser(userService.recuperaSuperintendentes());

		rhList = retornaItemAPartirDeUser(userService.recuperaRH());

		JustificativaPonto justificativaRecebida = null;

		String id = JsfUtil.getParameter("id");

		if (id != null) {

			justificativaRecebida = justificativaService.recuperar(Integer.parseInt(id));

			idCoordenador = justificativaRecebida.getCoordenador().getId();

			if (justificativaRecebida.getSuperintendente() != null) {
				idSuperintendente = justificativaRecebida.getSuperintendente().getId();
			}

			if (justificativaRecebida.getRh() != null) {
				idRh = justificativaRecebida.getRh().getId();
			}

		}

		if (justificativaRecebida == null) {
			justificativaRecebida = justificativaService.nova(
                    permissoes.getUsuarioLogado()
            );
		}

		setJustificativa(justificativaRecebida);

	}

	public HandlerMotivosManagedBean getHandler() {
		return handler;
	}

	public List<SelectItem> getCoordenadorList() {
		return coordenadorList;
	}

	public void setCoordenadorList(List<SelectItem> coordenadorList) {
		this.coordenadorList = coordenadorList;
	}

	public List<SelectItem> getSuperintendenteList() {
		return superintendenteList;
	}

	public void setSuperintendenteList(List<SelectItem> superintendenteList) {
		this.superintendenteList = superintendenteList;
	}

	public List<SelectItem> getRhList() {
		return rhList;
	}

	public void setRhList(List<SelectItem> rhList) {
		this.rhList = rhList;
	}

	public List<SelectItem> getTipoBancoHorasList() {
		return tipoBancoHorasList;
	}

	public void setTipoBancoHorasList(List<SelectItem> tipoBancoHorasList) {
		this.tipoBancoHorasList = tipoBancoHorasList;
	}

	public List<SelectItem> getTipoDecisaoList() {
		return tipoDecisaoList;
	}

	public void setTipoDecisaoList(List<SelectItem> tipoDecisaoList) {
		this.tipoDecisaoList = tipoDecisaoList;
	}

	public JustificativaPonto getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(JustificativaPonto justificativa) {

		acesso = workflow.verificaAcesso(justificativa);

		handler = new HandlerMotivosManagedBean(justificativa.getMotivo());

		this.justificativa = justificativa;
	}

	public Integer getIdCoordenador() {
		return idCoordenador;
	}

	public void setIdCoordenador(Integer idCoordenador) {
		this.idCoordenador = idCoordenador;
	}

	public Integer getIdSuperintendente() {
		return idSuperintendente;
	}

	public void setIdSuperintendente(Integer idSuperintendente) {
		this.idSuperintendente = idSuperintendente;
	}

	public Integer getIdRh() {
		return idRh;
	}

	public void setIdRh(Integer idRh) {
		this.idRh = idRh;
	}

	// AUTOR SOLICITANTE
	public String enviarCoordenador() {

		workflow.enviarCoordenador(justificativa, idCoordenador);

		return SUCCESS;

	}

	// AUTOR COORDENADOR
	public String enviarSuperintendente() {

		workflow.enviarSuperintendente(justificativa, idSuperintendente);

		return SUCCESS;

	}

	// AUTOR SUPERINTENDENTE
	public String enviarRh() {

		workflow.enviarRh(justificativa, idRh);

		return SUCCESS;
	}

	// AUTOR RH
	public String concluiRh() {

		workflow.concluir(justificativa);

		return SUCCESS;
	}

	public void cancelado(ActionEvent event) {

		RequestContext context = RequestContext.getCurrentInstance();

        boolean cancelado = true;

        try {

            workflow.cancelar(justificativa);

		} catch (BusinessException be) {
			Message.addMessage(be.getMessage(), permissoes.getUsuarioLogado().getNome());
            cancelado = false;
		} catch (Exception e){
            Message.addMessage("dialog.cancelar.erro.inesperado", permissoes.getUsuarioLogado().getNome());
            cancelado = false;
        }

		context.addCallbackParam("cancelado", cancelado);

	}

	public String getLabelCadastro() {
		if (justificativa.getJustificativaId() == null
				|| justificativa.getJustificativaId() == 0) {
			return Message
					.getBundleMessage("cadastroJustificativa.label.titulo");
		} else {
			return Message
					.getBundleMessage("cadastroJustificativa.label.alteraUsuario");
		}
	}

	private List<SelectItem> retornaItemAPartirDeUser(List<User> users) {
		if (users == null) {
			return null;
		}

		List<SelectItem> resultado = new LinkedList<SelectItem>();

		for (User u : users) {
			resultado.add(new SelectItem(u.getId(), u.getNome()));
		}

		return resultado;
	}

	public AcessoJustificativa getAcesso() {
		return acesso;
	}

	public void setAcesso(AcessoJustificativa acesso) {
		this.acesso = acesso;
	}

	private void readObject(ObjectInputStream s) throws ClassNotFoundException,
			IOException {
		s.defaultReadObject();

		workflow = (IWorkflow) ApplicationContextProvider.getBean("workflow");

        permissoes = (IPermissoesBean) ApplicationContextProvider.getBean("PermissoesBean");

	}
}