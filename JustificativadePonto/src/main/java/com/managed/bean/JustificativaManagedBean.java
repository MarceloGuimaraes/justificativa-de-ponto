package com.managed.bean;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.exception.BusinessException;
import com.domain.service.IProximoPasso;
import com.domain.service.IWorkflow;
import com.jsf.ds.impl.ComboTipoBancoHorasDatasourceImpl;
import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.managed.bean.handler.HandlerMotivosManagedBean;
import com.managed.bean.handler.HandlerProximoPassoManagedBean;
import com.service.IJustificativaService;
import com.spring.util.ApplicationContextProvider;
import com.util.JsfUtil;
import com.util.Message;
import org.primefaces.context.RequestContext;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;

public class JustificativaManagedBean implements IJustificativaManagedBean {

	private static final long serialVersionUID = 1L;

	private static final String SUCCESS = "welcome";

	private transient IWorkflow workflow;

    private transient IPermissoesBean permissoes;

	private HandlerMotivosManagedBean handler;

    private HandlerProximoPassoManagedBean handlerWorkflow;

    private HandlerProximoPassoManagedBean handlerCancel;

	private transient List<SelectItem> tipoBancoHorasList;

	private transient List<SelectItem> tipoDecisaoList;

	private JustificativaPontoDTO justificativa;

	public JustificativaManagedBean(IJustificativaService justificativaService,
                                    IPermissoesBean permissoes,
                                    IWorkflow workflow) {

		this.workflow = workflow;

        this.permissoes = permissoes;

        inicializaTela();

		JustificativaPontoDTO justificativaRecebida = null;

		String id = JsfUtil.getParameter("id");

		if (id != null) {
			justificativaRecebida = justificativaService.recuperar(Integer.parseInt(id));
		}

		if (justificativaRecebida == null) {
			justificativaRecebida = new JustificativaPontoDTO(
                    permissoes.getUsuarioLogado()
            );
		}

		setJustificativa(justificativaRecebida);

	}

    private void inicializaTela() {
        tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();

        tipoBancoHorasList = new ComboTipoBancoHorasDatasourceImpl().findObjects();
    }

    public HandlerMotivosManagedBean getHandler() {
		return handler;
	}

    public HandlerProximoPassoManagedBean getProximoPasso(){
        return handlerWorkflow;
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

	public JustificativaPontoDTO getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(JustificativaPontoDTO justificativa) {

        handlerWorkflow = workflow.retornaProximoPasso(justificativa);

        handlerCancel = workflow.retornaCancelamento();

        handler = new HandlerMotivosManagedBean(justificativa.getMotivo());

		this.justificativa = justificativa;
	}

    public String proximo() {
        handlerWorkflow.proximo(justificativa);
        return SUCCESS;
    }

	public void cancelado(ActionEvent event) {

		RequestContext context = RequestContext.getCurrentInstance();

        boolean cancelado = true;

        try {

            handlerCancel.proximo(justificativa);

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
		if (justificativa.getId() == null
				|| justificativa.getId() == 0) {
			return Message
					.getBundleMessage("cadastroJustificativa.label.titulo");
		} else {
			return Message
					.getBundleMessage("cadastroJustificativa.label.alteraUsuario");
		}
	}

	private void readObject(ObjectInputStream s) throws ClassNotFoundException,
			IOException {
		s.defaultReadObject();

		workflow = (IWorkflow) ApplicationContextProvider.getBean("workflow");

        permissoes = (IPermissoesBean) ApplicationContextProvider.getBean("PermissoesBean");

        inicializaTela();

	}
}