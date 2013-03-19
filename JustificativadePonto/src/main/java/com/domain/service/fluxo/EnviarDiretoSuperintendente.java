package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.UsuarioLogado;
import com.managed.bean.IPermissoesBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * EnviarCoordenador q manda direto para o Superintendente
 */
public class EnviarDiretoSuperintendente extends ProximoPasso {

    public EnviarDiretoSuperintendente(IJustificativaService justificativaService,
                                       IUserService userService,
                                       IMailService mailService,
                                       IPermissoesBean permissoes,
                                       Mapper mapper) {
        super(justificativaService, userService, mailService, permissoes, mapper);
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        return justificativa.getStatus().equals(StatusEnum.ELABORACAO)
                && permissoes.getUsuarioLogado().getPerfil().contains(PerfilEnum.COORDENADOR);
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        final User superintendente = userService.recuperar(justificativa.getIdProximoResponsavel());

        justificativa = justificativaService.adicionar(justificativa);

        justificativa = justificativaService.mudaSituacao(
                permissoes.getUsuarioLogado(),
                mapper.map(superintendente, UsuarioLogado.class),
                justificativa,
                StatusEnum.APROVSUPERINTENDENTE,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE
        );

        final List<User> destinos = new LinkedList<User>();
        destinos.add(superintendente);

        mailService.enviarSuperintendente(
                permissoes.getUsuarioLogado(),
                null,
                superintendente,
                justificativa.getId());
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return userService.recuperaSuperintendentes();
    }

    @Override
    public Map<String, Boolean> retornaHandler() {
        return criaViewHandler(true, false, true, false, false);
    }
}
