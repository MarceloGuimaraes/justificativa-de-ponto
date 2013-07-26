package com.managed.bean;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.service.IProximoPasso;
import com.domain.service.IWorkflowResolver;
import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.managed.bean.handler.HandlerMotivosManagedBean;
import com.model.JustificativaPonto;
import com.service.IJustificativaService;
import com.util.JsfUtil;
import com.util.Message;
import org.dozer.Mapper;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class JustificativaManagedBean implements Serializable {

	private static final long serialVersionUID = 1L;

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