package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.service.IProximoPasso;
import com.domain.service.IWorkflowResolver;
import com.model.JustificativaPonto;
import com.service.IJustificativaService;
import org.dozer.Mapper;

import java.util.List;
import java.util.Map;

public class WorkflowResolver implements IWorkflowResolver, IProximoPasso {

    private IJustificativaService justificativaService;
    private Mapper mapper;
    private Map<String,IProximoPasso> passos;

    public WorkflowResolver(IJustificativaService justificativaService,
                            Mapper mapper,
                            Map<String, IProximoPasso> passos) {
        this.justificativaService = justificativaService;
        this.mapper = mapper;
        this.passos = passos;
    }

    @Override
    public IProximoPasso retornaProximoPasso(JustificativaPontoDTO justificativaTela){

        JustificativaPonto justificativa;

        if(justificativaTela.getId()==null || justificativaTela.getId()==0){
            justificativa = mapper.map(justificativaTela, JustificativaPonto.class);
        }else{
            justificativa = justificativaService.recuperar(justificativaTela.getId(), "historico");
        }

        for(IProximoPasso passo : passos.values()){
            if(passo.isIntercepted(justificativa)){
                return passo;
            }
        }

        return new PassoSemAcesso();

    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        return false;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        retornaProximoPasso(justificativa).proximo(justificativa);
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return null;
    }

    @Override
    public Map<String, Boolean> retornaHandler() {
        return null;
    }
}
