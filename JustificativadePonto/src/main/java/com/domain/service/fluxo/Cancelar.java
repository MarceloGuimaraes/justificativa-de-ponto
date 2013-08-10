package com.domain.service.fluxo;

import com.domain.dto.CadastroUsuario;
import com.domain.dto.JustificativaPontoDTO;
import com.managed.bean.IPermissoesBean;
import com.model.EncaminhamentoJustificativaPonto;
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
        JustificativaPonto justificativaPersistida = justificativaService.recuperar(justificativa.getId());
        Map<TipoEventoJustificativaPontoEnum, EncaminhamentoJustificativaPonto> historicos = retornaHistoricosMapeados(justificativaPersistida);
        List<User> destinatarios = new LinkedList<User>();
        if(historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR)){
            User coordenador = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_COORDENADOR).getResponsavel(), User.class);
            destinatarios.add(coordenador);
        }

        if(historicos.containsKey(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE)){
            User superintendente = mapper.map(historicos.get(TipoEventoJustificativaPontoEnum.ENVIADO_APROVACAO_SUPERINTENDENTE).getResponsavel(), User.class);
            destinatarios.add(superintendente);
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
                usuarioLogado,
                justificativaPersistida.getSolicitante(),
                destinatarios,
                justificativaPersistida.getId(),
                justificativa.getCancelamento());
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
