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

/**
 * User: xonda
 * Date: 07/08/13
 * Time: 00:13
 */
public class ReprovarCoordenador extends ProximoPasso {

    public ReprovarCoordenador(IJustificativaService justificativaService,
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

        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativa);

        Identificacao usuarioLogado = mapper.map(permissoes.getUsuarioLogado(), Identificacao.class);

        return justificativa.getStatus().equals(StatusEnum.APROVCOORD)
                && historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)
                .getResponsavel().equals(usuarioLogado);
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        // Inserindo o superintendente escolhido

        JustificativaPonto justificativaPonto = justificativaService.recuperar(justificativa.getId());

        User usuarioLogado = userService.recuperar(permissoes.getUsuarioLogado().getId());
        User solicitante = justificativaPonto.getSolicitante();

        justificativaService.atua(usuarioLogado,
                justificativaPonto,
                StatusEnum.ELABORACAO,
                TipoEventoJustificativaPontoEnum.REPROVADO_COORDENADOR,
                justificativa.getCancelamento());

        justificativaService.encaminha(usuarioLogado,
                solicitante,
                justificativaPonto,
                StatusEnum.ELABORACAO,
                TipoEventoJustificativaPontoEnum.ENVIADO_SOLICITANTE);

        mailService.reprovadoCoordenador(
                usuarioLogado,
                solicitante,
                justificativaPonto.getId(),
                justificativa.getCancelamento()
        );
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
