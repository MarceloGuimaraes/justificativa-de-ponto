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

public class EnviarSuperintendente extends ProximoPasso {

    public EnviarSuperintendente(IJustificativaService justificativaService,
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

        return justificativa.getStatus().equals(StatusEnum.APROVCOORD)
                && historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)
                .getResponsavel().equals(usuarioLogado);
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        JustificativaPonto justificativaPonto = justificativaService.recuperar(justificativa.getId());
        // Inserindo o superintendente escolhido
        User superintendente=userService.recuperar(justificativa.getIdProximoResponsavel());
        User solicitante = justificativaPonto.getSolicitante();
        User usuarioLogado = userService.recuperar(permissoes.getUsuarioLogado().getId());

        justificativaService.atua(usuarioLogado,
                justificativaPonto,
                StatusEnum.APROVSUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.APROVADO_COORDENADOR);

        justificativaService.encaminha(usuarioLogado,
                superintendente,
                justificativaPonto,
                StatusEnum.APROVSUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE);

        mailService.enviarSuperintendente(
                permissoes.getUsuarioLogado(),
                solicitante,
                superintendente,
                justificativaPonto.getId()
        );
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return userService.recuperaSuperintendentes();
    }

    @Override
    public Map<String, Boolean> retornaHandler() {
        return criaViewHandler(false,false,true,false,true);
    }
}
