package com.domain.dto;

import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import javax.faces.model.SelectItem;
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
        temProximoPasso = true;
        permiteEditar = false;
        permiteCancelar = true;
        concluir = false;
    }

    @Override
    protected List<SelectItem> populaEscolhas() {
        return retornaItemAPartirDeUser(userService.recuperaSuperintendentes());
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {

        Map<TipoEventoJustificativaPontoEnum,EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativa);

        Identificacao usuarioLogado = mapper.map(permissoes.getUsuarioLogado(), Identificacao.class);

        if (justificativa.getStatus().equals(StatusEnum.APROVCOORD)
                && historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)
                .getResponsavel().equals(usuarioLogado)) {

            return true;

        }

        return false;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        // Inserindo o superintendente escolhido
        User superintendente=userService.recuperar(getId());
        User solicitante = mapper.map(justificativa.getSolicitante(), User.class);

        justificativaService.atua(
                permissoes.getUsuarioLogado(),
                justificativa,
                StatusEnum.APROVSUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.APROVADO_COORDENADOR);

        justificativaService.mudaSituacao(
                permissoes.getUsuarioLogado(),
                mapper.map(superintendente, UsuarioLogado.class),
                justificativa,
                StatusEnum.APROVSUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE);

        mailService.enviarSuperintendente(
                permissoes.getUsuarioLogado(),
                solicitante,
                superintendente,
                justificativa.getId()
        );
    }
}
