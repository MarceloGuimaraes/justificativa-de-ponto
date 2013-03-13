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

public class EnviarCoordenador extends ProximoPasso {

    public EnviarCoordenador(IJustificativaService justificativaService,
                             IUserService userService,
                             IMailService mailService,
                             IPermissoesBean permissoes,
                             Mapper mapper) {
        super(justificativaService, userService, mailService, permissoes, mapper);
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        return justificativa.getStatus().equals(StatusEnum.ELABORACAO)
                && !permissoes.getUsuarioLogado().getPerfil().contains(PerfilEnum.COORDENADOR);
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        // Inserindo o coordenador escolhido
        final User coordenador = userService.recuperar(justificativa.getIdProximoResponsavel());

        justificativa = justificativaService.adicionar(justificativa);

        justificativa = justificativaService.mudaSituacao(
                permissoes.getUsuarioLogado(),
                mapper.map(coordenador, UsuarioLogado.class),
                justificativa,
                StatusEnum.APROVCOORD,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR
        );

        final List<User> destinos = new LinkedList<User>();
        destinos.add(coordenador);

        mailService.enviarCoordenador(
                permissoes.getUsuarioLogado(),
                coordenador,
                justificativa.getId()
        );
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return userService.recuperaCoordenadores();
    }

    @Override
    public Map<String, Boolean> retornaHandler() {
        return criaViewHandler(true, false, true, false, false);
    }
}
