package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
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
        User usuarioLogado = mapper.map(permissoes.getUsuarioLogado(), User.class);
        return justificativa.getStatus().equals(StatusEnum.ELABORACAO)
                && !permissoes.getUsuarioLogado().getPerfil().contains(PerfilEnum.COORDENADOR)
                && justificativa.getSolicitante().equals(usuarioLogado);
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        // Inserindo o coordenador escolhido
        final User coordenador = userService.recuperar(justificativa.getIdProximoResponsavel());
        final User solicitante = userService.recuperar(permissoes.getUsuarioLogado().getId());

        JustificativaPonto justificativaNova;

        if(justificativa.getId() == null || justificativa.getId() == 0){
            justificativaNova = new JustificativaPonto(solicitante);
            mapper.map(justificativa, justificativaNova);
            justificativaNova = justificativaService.adicionar(justificativaNova);
        } else {
            justificativaNova = justificativaService.recuperar(justificativa.getId());
            mapper.map(justificativa, justificativaNova);
            justificativaNova = justificativaService.atualizar(justificativaNova);
        }

        justificativaService.encaminha(solicitante,
                coordenador,
                justificativaNova,
                StatusEnum.APROVCOORD,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR);

        final List<User> destinos = new LinkedList<User>();
        destinos.add(coordenador);

        mailService.enviarCoordenador(
                solicitante,
                coordenador,
                justificativaNova.getId()
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
