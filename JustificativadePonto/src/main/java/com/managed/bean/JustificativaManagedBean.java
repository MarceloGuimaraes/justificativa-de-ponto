package com.managed.bean;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.exception.BusinessException;
import com.domain.service.IProximoPasso;
import com.domain.service.IWorkflow;
import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.managed.bean.handler.HandlerMotivosManagedBean;
import com.service.IJustificativaService;
import com.spring.util.ApplicationContextProvider;
import com.util.JsfUtil;
import com.util.Message;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.context.RequestContext;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JustificativaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

    private transient IPermissoesBean permissoes;

    private transient List<SelectItem> tipoDecisaoList;

    private transient List<SelectItem> escolhas;

    private String titulo;

    private JustificativaPontoDTO justificativa;

    private Map<String, Boolean> handleViewFluxo;

	public JustificativaManagedBean(final IJustificativaService justificativaService,
                                    final IPermissoesBean permissoes,
                                    final IWorkflow workflow,
                                    final HandlerMotivosManagedBean motivosManagedBean) {
        this.permissoes = permissoes;
		String id = JsfUtil.getParameter("id");
		if (id != null) {
			justificativa = justificativaService.recuperar(Integer.parseInt(id));
            titulo = Message.getBundleMessage("cadastroJustificativa.label.alteraUsuario");
		}
        if (justificativa == null) {
            justificativa = new JustificativaPontoDTO(permissoes.getUsuarioLogado());
            titulo = Message.getBundleMessage("cadastroJustificativa.label.titulo");
        }
        tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();
        IProximoPasso proximoPasso = workflow.retornaProximoPasso(justificativa);
        escolhas = retornaItemAPartirDeUser(proximoPasso.listaCandidatos());
        handleViewFluxo = proximoPasso.retornaHandler();
        motivosManagedBean.setJustificativa(justificativa);
    }

    public Map<String,Boolean> getView(){
        return handleViewFluxo;
    }

	public List<SelectItem> getTipoDecisaoList() {
		return tipoDecisaoList;
	}

	public void setTipoDecisaoList(List<SelectItem> tipoDecisaoList) {
		this.tipoDecisaoList = tipoDecisaoList;
	}

    public List<SelectItem> getEscolhas() {
        return escolhas;
    }

    public void setEscolhas(List<SelectItem> escolhas) {
        this.escolhas = escolhas;
    }

	public JustificativaPontoDTO getJustificativa() {
		return justificativa;
	}

	public void setJustificativa(JustificativaPontoDTO justificativa) {
		this.justificativa = justificativa;
	}

    public void proximo(ActionEvent event) {
        final RequestContext context = RequestContext.getCurrentInstance();

        boolean sucesso = true;

        try {
            CommandButton o  = (CommandButton) event.getSource();
            String tipo = (String) o.getAttributes().get("fluxo");
            final IWorkflow workflow = (IWorkflow) ApplicationContextProvider.getBean("workflow");
            IProximoPasso proximoPasso = null;
            if("cancelar".equalsIgnoreCase(tipo)){
                proximoPasso = workflow.recupera(IWorkflow.PASSO_CANCELAR);
            }else if("proximo".equalsIgnoreCase(tipo)){
                proximoPasso = workflow.retornaProximoPasso(justificativa);
            }
            proximoPasso.proximo(justificativa);
        } catch (BusinessException be) {
            Message.addMessage(be.getMessage(), permissoes.getUsuarioLogado().getNome());
            sucesso = false;
        } catch (Exception e){
            Message.addMessage("dialog.cancelar.erro.inesperado", permissoes.getUsuarioLogado().getNome());
            sucesso = false;
        }

        context.addCallbackParam("sucesso", sucesso);
    }

	public String getLabelCadastro() {
        return titulo;
	}

    private List<SelectItem> retornaItemAPartirDeUser(final List<CadastroUsuario> users) {
        if (users == null) {
            return null;
        }
        final List<SelectItem> resultado = new LinkedList<SelectItem>();
        for (CadastroUsuario u : users) {
            resultado.add(new SelectItem(u.getId(), u.getNome()));
        }
        return resultado;

    }

	private void readObject(ObjectInputStream s) throws ClassNotFoundException, IOException {
		s.defaultReadObject();
		final IWorkflow workflow = (IWorkflow) ApplicationContextProvider.getBean("workflow");
        final IProximoPasso proximoPasso = workflow.retornaProximoPasso(justificativa);
        escolhas = retornaItemAPartirDeUser(proximoPasso.listaCandidatos());
        tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();
        permissoes = (IPermissoesBean) ApplicationContextProvider.getBean("PermissoesBean");
	}
}