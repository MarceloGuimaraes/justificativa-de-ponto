package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.exception.BusinessException;
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

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Cancelar extends ProximoPasso {

    @Override
    public boolean isIntercepted(JustificativaPonto justificativa) {
        return true;
    }

    @Override
    public void proximo(JustificativaPontoDTO justificativa) {
        Identificacao userCorrente = mapper.map(permissoes.getUsuarioLogado(), Identificacao.class);

        List<User> destinos = new LinkedList<User>();

        JustificativaPonto justificativaPersistida = justificativaService.recuperar(justificativa.getId());

        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativaPersistida);

        if (StatusEnum.APROVCOORD.equals(justificativaPersistida.getStatus()) &&
                (historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR) &&
                historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel().equals(userCorrente))) {
            // AUTOR COORDENADOR

        } else if (StatusEnum.APROVSUPERINTENDENTE.equals(justificativaPersistida.getStatus()) &&
                ((historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE) &&
                        historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel().equals(userCorrente))
                        || permissoes.isAdmin())) {
            // AUTOR SUPERINTENDENTE
            if(historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)){
                final User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
                destinos.add(coordenador);
            }

        } else if (StatusEnum.EXECUCAORH.equals(justificativaPersistida.getStatus()) &&
                ((historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH) &&
                        historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_RH).getResponsavel().equals(userCorrente))
                        || permissoes.isAdmin())) {
            // AUTOR RH
            if(historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)){
                User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
                destinos.add(coordenador);
            }

            if(historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE)){
                User superintendente = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel(), User.class);
                destinos.add(superintendente);
            }

        } else if (permissoes.isAdmin()) {
            //Administrador

        } else {
            throw new BusinessException("dialog.cancelar.valida.usuarioinvalido");
        }
        User usuarioLogado = userService.recuperar(permissoes.getUsuarioLogado().getId());
        justificativaService.atua(
                usuarioLogado,
                justificativaPersistida,
                StatusEnum.CANCELADO,
                TipoEventoJustificativaPontoEnum.CANCELADO,
                justificativa.getCancelamento()
        );

        mailService.cancelado(
                permissoes.getUsuarioLogado(),
                justificativaPersistida.getSolicitante(),
                destinos,
                justificativaPersistida.getId()
        );
    }

    @Override
    public List<CadastroUsuario> listaCandidatos() {
        return null;
    }

    public Cancelar(IJustificativaService justificativaService,
                    IUserService userService,
                    IMailService mailService,
                    IPermissoesBean permissoes,
                    Mapper mapper) {
        super(justificativaService, userService, mailService, permissoes, mapper);
    }

}
