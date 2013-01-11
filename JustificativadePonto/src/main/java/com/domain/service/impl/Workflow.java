package com.domain.service.impl;

import com.domain.dto.JustificativaPontoDTO;
import com.domain.service.fluxo.PassoSemAcesso;
import com.domain.service.IProximoPasso;
import com.domain.service.IWorkflow;
import com.model.JustificativaPonto;
import com.service.IJustificativaService;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

public class Workflow implements IWorkflow {

    private static final Integer PASSO_ENVIARCOORDENADOR = 1;
    private static final Integer PASSO_ENVIARSUPERINTENDENTE = 2;
    private static final Integer PASSO_ENVIARRH = 3;
    private static final Integer PASSO_CONCLUIR = 4;
    private static final Integer PASSO_CANCELAR = 5;

    private IJustificativaService justificativaService;

    private Mapper mapper;
    private Map<Integer,IProximoPasso> passos;

    public Workflow(IJustificativaService justificativaService,
                    Mapper mapper,
                    Map<Integer,IProximoPasso> passos) {
        this.justificativaService = justificativaService;
        this.mapper = mapper;
        this.passos = passos;
    }

    @Override
    public IProximoPasso retornaProximoPasso(JustificativaPontoDTO justificativaTela){

        JustificativaPonto justificativa = null;

        if(justificativaTela.getId()==null || justificativaTela.getId()==0){
            justificativa = mapper.map(justificativaTela, JustificativaPonto.class);
        }else{
            justificativa = justificativaService.recuperar(justificativaTela);
        }

        for(IProximoPasso passo : passos.values()){
            if(passo.isIntercepted(justificativa)){
                return passo;
            }
        }

        return new PassoSemAcesso();

    }

    @Override
    @Transactional(readOnly = false)
    public void cancelar(JustificativaPontoDTO justificativaTela) {

        passos.get(PASSO_CANCELAR).proximo(justificativaTela);

    }

}
