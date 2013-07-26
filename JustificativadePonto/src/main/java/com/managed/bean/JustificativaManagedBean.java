package com.managed.bean;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.exception.BusinessException;
import com.domain.service.IProximoPasso;
import com.domain.service.IWorkflowResolver;
import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.managed.bean.handler.HandlerMotivosManagedBean;
import com.model.JustificativaPonto;
import com.service.IJustificativaService;
import com.util.JsfUtil;
import com.util.Message;
import org.dozer.Mapper;
import org.primefaces.context.RequestContext;

import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JustificativaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

    private IPermissoesBean permissoes;

    private List<SelectItem> tipoDecisaoList;

    private List<SelectItem> escolhas;

    private String titulo;

    private JustificativaPontoDTO justificativa;

    private Map<String, Boolean> handleViewFluxo;

	public JustificativaManagedBean(final IJustificativaService justificativaService,
                                    final IPermissoesBean permissoes,
                                    final IWorkflowResolver workflow,
                                    final HandlerMotivosManagedBean motivosManagedBean,
                                    final Mapper mapper) {
        this.permissoes = permissoes;
		String id = JsfUtil.getParameter("id");
		if (id != null) {
			JustificativaPonto j = justificativaService.recuperar(Integer.parseInt(id), "historico");
            justificativa = mapper.map(j, JustificativaPontoDTO.class);
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
            IProximoPasso proximoPasso = (IProximoPasso) event.getComponent().getAttributes().get("fluxo");
            proximoPasso.proximo(justificativa);
        } catch (BusinessException be) {
            Message.addMessage(be.getMessage(), permissoes.getUsuarioLogado().getNome());
            sucesso = false;
        } catch (Exception e){
            Message.addMessage("dialog.cancelar.erro.inesperado", permissoes.getUsuarioLogado().getNome());
            e.printStackTrace();
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
}