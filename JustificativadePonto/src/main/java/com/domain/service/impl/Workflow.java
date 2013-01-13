package com.domain.service.impl;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.service.IProximoPasso;
import com.domain.service.IWorkflow;
import com.domain.service.fluxo.PassoSemAcesso;
import com.managed.bean.handler.HandlerProximoPassoManagedBean;
import com.model.JustificativaPonto;
import com.service.IJustificativaService;
import org.dozer.Mapper;

import java.util.Map;

public class Workflow implements IWorkflow {

    private static final String PASSO_ENVIARCOORDENADOR = "1";
    private static final String PASSO_ENVIARSUPERINTENDENTE = "2";
    private static final String PASSO_ENVIARRH = "3";
    private static final String PASSO_CONCLUIR = "4";
    private static final String PASSO_CANCELAR = "5";

    private IJustificativaService justificativaService;

    private Mapper mapper;
    private Map<String,IProximoPasso> passos;

    public Workflow(IJustificativaService justificativaService,
                    Mapper mapper,
                    Map<String,IProximoPasso> passos) {
        this.justificativaService = justificativaService;
        this.mapper = mapper;
        this.passos = passos;
    }

    @Override
    public HandlerProximoPassoManagedBean retornaProximoPasso(JustificativaPontoDTO justificativaTela){

        JustificativaPonto justificativa = null;

        if(justificativaTela.getId()==null || justificativaTela.getId()==0){
            justificativa = mapper.map(justificativaTela, JustificativaPonto.class);
        }else{
            justificativa = justificativaService.recuperar(justificativaTela);
        }

        for(IProximoPasso passo : passos.values()){
            if(passo.isIntercepted(justificativa)){
                return passo.retornaHandler();
            }
        }

        return new PassoSemAcesso().retornaHandler();

    }

    @Override
    public HandlerProximoPassoManagedBean retornaCancelamento() {
        return this.passos.get(PASSO_CANCELAR).retornaHandler();
    }

}
