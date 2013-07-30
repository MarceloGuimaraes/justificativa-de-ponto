package com.managed.bean.wrapper;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.service.IProximoPasso;
import com.domain.service.IWorkflowResolver;
import com.jsf.ds.impl.ComboTipoDecisaoDatasourceImpl;
import com.managed.bean.handler.HandlerMotivosManagedBean;

import javax.faces.model.SelectItem;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * User: xonda
 * Date: 26/07/13
 * Time: 13:47
 */
public class WrapperJustificativa implements Serializable {

    private JustificativaPontoDTO justificativa;
    private List<SelectItem> tipoDecisaoList;
    private List<SelectItem> escolhas;
    private Map<String, Boolean> view;
    private HandlerMotivosManagedBean handler;

    public WrapperJustificativa(JustificativaPontoDTO justificativa,
                                IWorkflowResolver workflow) {
        this.justificativa = justificativa;
        tipoDecisaoList = new ComboTipoDecisaoDatasourceImpl().findObjects();
        IProximoPasso proximoPasso = workflow.retornaProximoPasso(justificativa);
        escolhas = retornaItemAPartirDeUser(proximoPasso.listaCandidatos());
        view = proximoPasso.retornaHandler();
        handler = new HandlerMotivosManagedBean(justificativa.getMotivo());

    }

    public JustificativaPontoDTO getJustificativa() {
        return justificativa;
    }

    public Map<String, Boolean> getView() {
        return view;
    }

    public List<SelectItem> getTipoDecisaoList() {
        return tipoDecisaoList;
    }

    public List<SelectItem> getEscolhas() {
        return escolhas;
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

    public HandlerMotivosManagedBean getHandler() {
        return handler;
    }
}
