package com.managed.bean;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.exception.BusinessException;
import com.domain.service.IProximoPasso;
import com.util.Message;
import org.primefaces.context.RequestContext;

import java.io.Serializable;

/**
 * User: xonda
 * Date: 25/07/13
 * Time: 13:49
 */
public class FluxoJustificativaManagedBean implements Serializable {
    private final IPermissoesBean permissoes;
    private JustificativaPontoDTO justificativa;
    private IProximoPasso proximoPasso;

    public FluxoJustificativaManagedBean(IPermissoesBean permissoes) {
        this.permissoes = permissoes;
        this.justificativa = new JustificativaPontoDTO(permissoes.getUsuarioLogado());
    }

    public JustificativaPontoDTO getJustificativa() {
        return justificativa;
    }

    public void setJustificativa(JustificativaPontoDTO justificativa) {
        this.justificativa = justificativa;
    }

    public IProximoPasso getProximoPasso() {
        return proximoPasso;
    }

    public void setProximoPasso(IProximoPasso proximoPasso) {
        this.proximoPasso = proximoPasso;
    }

    public String proximo() {
        final RequestContext context = RequestContext.getCurrentInstance();

        boolean sucesso = true;

        try {
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
        return null;
    }
}
