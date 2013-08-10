package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.IPermissoesBean;
import com.model.EncaminhamentoJustificativaPonto;
import com.model.Identificacao;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import java.util.List;
import java.util.Map;

public class ReprovarSuperintendente extends ProximoPasso {
    public ReprovarSuperintendente(IJustificativaService justificativaService,
                                   IUserService userService,
                                   IMailService mailService,
                                   IPermissoesBean permissoes,
                                   Mapper mapper) {
        super(justificativaService, userService, mailService, permissoes, mapper);
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
        final User solicitante = justificativaPonto.getSolicitante();
        final User usuarioLogado = userService.recuperar(permissoes.getUsuarioLogado().getId());

        justificativaService.atua(usuarioLogado,
                justificativaPonto,
                StatusEnum.ELABORACAO,
                TipoEventoJustificativaPontoEnum.REPROVADO_SUPERINTENDENTE,
                justificativa.getCancelamento());

        justificativaService.encaminha(usuarioLogado,
                solicitante,
                justificativaPonto,
                StatusEnum.ELABORACAO,
                TipoEventoJustificativaPontoEnum.ENVIADO_SOLICITANTE);

        mailService.reprovadoSuperintendente(
                usuarioLogado,
                solicitante,
                justificativaPonto.getId(),
                justificativa.getCancelamento());
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        throw new UnsupportedOperationException("Fluxo de cancelamento nao lista candidatos");
    }

    @Override
    public Map<String, Boolean> retornaHandler() {
        throw new UnsupportedOperationException("Fluxo de cancelamento nao controla view");
    }
}
