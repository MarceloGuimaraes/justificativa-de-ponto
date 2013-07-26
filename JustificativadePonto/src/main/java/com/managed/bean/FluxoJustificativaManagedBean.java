package com.managed.bean;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.exception.BusinessException;
import com.domain.service.IProximoPasso;
import com.util.Message;
import org.primefaces.context.RequestContext;

import javax.faces.event.ActionEvent;
import java.io.Serializable;

/**
 * User: xonda
 * Date: 25/07/13
 * Time: 13:49
 */
public class FluxoJustificativaManagedBean implements Serializable {
    public static final String ATRIBUTO_FLUXO = "fluxo";
    public static final String ATRIBUTO_JUSTIFICATIVA = "justificativa";
    private final IPermissoesBean permissoes;

    public FluxoJustificativaManagedBean(IPermissoesBean permissoes) {
        this.permissoes = permissoes;
    }

    public void proximo(ActionEvent event) {
        final RequestContext context = RequestContext.getCurrentInstance();

        boolean sucesso = true;

        try {
            final JustificativaPontoDTO justificativa = (JustificativaPontoDTO) event.getComponent().getAttributes().get(ATRIBUTO_JUSTIFICATIVA);
            final IProximoPasso proximoPasso = (IProximoPasso) event.getComponent().getAttributes().get(ATRIBUTO_FLUXO);
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
}
