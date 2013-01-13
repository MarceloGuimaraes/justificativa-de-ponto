package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.exception.BusinessException;
import com.managed.bean.IPermissoesBean;
import com.managed.bean.handler.HandlerProximoPassoManagedBean;
import com.model.*;
import com.service.IJustificativaService;
import com.service.IUserService;
import com.service.mail.IMailService;
import org.dozer.Mapper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Cancelar extends ProximoPasso {

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        return false;
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return null;
    }

    @Override
    public HandlerProximoPassoManagedBean retornaHandler() {
        return new HandlerProximoPassoManagedBean(false,false,false,false,"cancelar");
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa, Integer id) {

        Identificacao userCorrente = mapper.map(permissoes.getUsuarioLogado(), Identificacao.class);

        List<User> destinos = new LinkedList<User>();

        JustificativaPonto justificativaPersistida = justificativaService.recuperar(justificativa);

        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativaPersistida);

        if (StatusEnum.APROVCOORD.equals(justificativaPersistida.getStatus()) &&
                historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel().equals(userCorrente)) {
            // AUTOR COORDENADOR

        } else if (StatusEnum.APROVSUPERINTENDENTE.equals(justificativaPersistida.getStatus()) &&
                (historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel().equals(userCorrente)
                        || permissoes.isAdmin())) {
            // AUTOR SUPERINTENDENTE
            User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
            destinos.add(coordenador);

        } else if (StatusEnum.EXECUCAORH.equals(justificativaPersistida.getStatus()) &&
                (historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH).getResponsavel().equals(userCorrente)
                        || permissoes.isAdmin())) {
            // AUTOR RH
            User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
            User superintendente = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel(), User.class);
            destinos.add(coordenador);
            destinos.add(superintendente);

        } else if (permissoes.isAdmin()) {
            //Administrador

        } else {

            throw new BusinessException("dialog.cancelar.valida.usuarioinvalido");

        }

        JustificativaPontoDTO justificativaAtualizada = justificativaService.atualizar(justificativa);

        justificativaService.atua(
                permissoes.getUsuarioLogado(),
                justificativaAtualizada,
                StatusEnum.CANCELADO,
                TipoEventoJustificativaPontoEnum.CANCELADO
        );

        mailService.cancelado(
                permissoes.getUsuarioLogado(),
                justificativaPersistida.getSolicitante(),
                destinos,
                justificativaPersistida.getId()
        );

    }

    public Cancelar(IJustificativaService justificativaService,
                    IUserService userService,
                    IMailService mailService,
                    IPermissoesBean permissoes,
                    Mapper mapper) {
        super(justificativaService, userService, mailService, permissoes, mapper);
    }
}
