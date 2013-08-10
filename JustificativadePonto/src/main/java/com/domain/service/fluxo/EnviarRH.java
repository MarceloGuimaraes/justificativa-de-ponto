package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import java.util.List;
import java.util.Map;

public class EnviarRH extends ProximoPasso {

    public EnviarRH(IJustificativaService justificativaService,
                    IUserService userService,
                    IMailService mailService,
                    IPermissoesBean permissoes,
                    Mapper mapper) {
        super(
                justificativaService,
                userService,
                mailService,
                permissoes,
                mapper
        );
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        Map<TipoEventoJustificativaPontoEnum,EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativa);

        Identificacao usuarioLogado = mapper.map(permissoes.getUsuarioLogado(), Identificacao.class);

        return justificativa.getStatus().equals(StatusEnum.APROVSUPERINTENDENTE)
                && historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel().equals(usuarioLogado);
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        final JustificativaPonto justificativaPonto = justificativaService.recuperar(justificativa.getId());
        // Inserindo o Rh escolhidos
        final User rh = userService.recuperar(justificativa.getIdProximoResponsavel());
        final User solicitante = justificativaPonto.getSolicitante();
        final User usuarioLogado = userService.recuperar(permissoes.getUsuarioLogado().getId());

        mapper.map(justificativa, justificativaPonto, "decisaoSuperIntendente");
        justificativaService.atualizar(justificativaPonto);

        justificativaService.atua(usuarioLogado,
                justificativaPonto,
                StatusEnum.EXECUCAORH,
                TipoEventoJustificativaPontoEnum.APROVADO_SUPERINTENDENTE);

        justificativaService.encaminha(usuarioLogado,
                rh,
                justificativaPonto,
                StatusEnum.EXECUCAORH,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH);

        final Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativaPonto);
        User coordenador=null;
        if(historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)){
            coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
        }

        mailService.enviarRh(
                usuarioLogado,
                solicitante,
                coordenador,
                rh,
                justificativaPonto.getId());
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return userService.recuperaRH();
    }

    @Override
    public Map<String, Boolean> retornaHandler() {
        return criaViewHandler(false,true, true, false, true);
    }

}
