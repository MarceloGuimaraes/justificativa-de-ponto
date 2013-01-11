package com.domain.dto;

import com.managed.bean.IPermissoesBean;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import javax.faces.model.SelectItem;
import java.util.LinkedList;
import java.util.List;

public class EnviarCoordenador extends ProximoPasso {

    public EnviarCoordenador(IJustificativaService justificativaService,
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
        permiteCancelar = false;
        permiteEditar = true;
        concluir = false;
    }

    @Override
    protected List<SelectItem> populaEscolhas() {
        return retornaItemAPartirDeUser(userService.recuperaCoordenadores());
    }

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        if (justificativa.getStatus().equals(StatusEnum.ELABORACAO)
                && justificativa.getSolicitante().equals(mapper.map(permissoes.getUsuarioLogado(), User.class))) {

            return true;

        }
        return false;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        // Inserindo o coordenador escolhido
        User coordenador = userService.recuperar(getId());

        justificativa = justificativaService.adicionar(justificativa);

        justificativa = justificativaService.mudaSituacao(
                permissoes.getUsuarioLogado(),
                mapper.map(coordenador, UsuarioLogado.class),
                justificativa,
                StatusEnum.APROVCOORD,
                TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR
        );

        List<User> destinos = new LinkedList<User>();
        destinos.add(coordenador);

        mailService.enviarCoordenador(
                permissoes.getUsuarioLogado(),
                coordenador,
                justificativa.getId()
        );
    }

}
