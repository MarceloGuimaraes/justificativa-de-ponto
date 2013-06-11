package com.service;

import com.dao.IJustificativaDAO;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.UsuarioLogado;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Transactional(readOnly = true)
public class JustificativaService implements IJustificativaService {

	private IJustificativaDAO dao;

    private Mapper mapper;

    public JustificativaService(IJustificativaDAO dao, Mapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
	@Transactional(readOnly = false)
	public JustificativaPonto adicionar(JustificativaPonto justificativa) {
		Serializable id = dao.adicionar(justificativa);
        return dao.recuperar(id);
	}

    @Override
	@Transactional(readOnly = false)
	public JustificativaPonto atualizar(JustificativaPonto justificativa) {
		return dao.atualizar(justificativa);
	}

    @Override
	@Transactional(readOnly = false)
	public void apagar(JustificativaPonto justificativa) {
		dao.deletar(justificativa);
	}

    @Override
    @Transactional(readOnly = false)
    public JustificativaPontoDTO adicionar(JustificativaPontoDTO justificativa) {
        if(justificativa.getId()!=null && justificativa.getId()!=0){
            throw new IllegalArgumentException("Nao eh possivel adicionar uma justificativa existente");
        }
        User user = mapper.map(justificativa.getSolicitante(), User.class);
        JustificativaPonto justificativaNova = new JustificativaPonto(user);
        mapper.map(justificativa, justificativaNova);
        justificativaNova = adicionar(justificativaNova);

        return mapper.map(justificativaNova, JustificativaPontoDTO.class);
    }

    @Override
    @Transactional(readOnly = false)
    public JustificativaPontoDTO atualizar(JustificativaPontoDTO justificativa) {
        JustificativaPonto justificativaPersistida = recuperar(justificativa);
        mapper.map(justificativa, justificativaPersistida);
        return justificativa;
    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public JustificativaPontoDTO mudaSituacao(UsuarioLogado usuarioLogado,
                                              UsuarioLogado proximoResponsavel,
                                              JustificativaPontoDTO justificativaTela,
                                              StatusEnum novoStatus,
                                              TipoEventoJustificativaPontoEnum eventoHistorico) {

        if(justificativaTela.getId()==null || justificativaTela.getId()==0){
            throw new IllegalArgumentException("Justificativa nao esta cadastrada no sistema");
        }

        if(eventoHistorico==null){
            throw new IllegalArgumentException("Nao foi informado o evento de historico");
        }

        JustificativaPonto justificativa = recuperar(justificativaTela);

        justificativa.setStatus(novoStatus);

        User user = mapper.map(usuarioLogado, User.class);
        User delegado = mapper.map(proximoResponsavel, User.class);

        justificativa.encaminha(user, delegado, eventoHistorico);

        atualizar(justificativa);

        return mapper.map(justificativa, JustificativaPontoDTO.class);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public JustificativaPontoDTO atua(UsuarioLogado usuarioLogado,
                                      JustificativaPontoDTO justificativaTela,
                                      StatusEnum novoStatus,
                                      TipoEventoJustificativaPontoEnum eventoHistorico) {

        if(justificativaTela.getId()==null || justificativaTela.getId()==0){
            throw new IllegalArgumentException("Justificativa nao esta cadastrada no sistema");
        }

        if(eventoHistorico==null){
            throw new IllegalArgumentException("Nao foi informado o evento de historico");
        }

        JustificativaPonto justificativa = recuperar(justificativaTela);

        justificativa.setStatus(novoStatus);

        User user = mapper.map(usuarioLogado, User.class);

        justificativa.adiciona(user, eventoHistorico);

        atualizar(justificativa);

        return mapper.map(justificativa, JustificativaPontoDTO.class);

    }

    @Override
	public JustificativaPonto recuperar(JustificativaPontoDTO justificativa) {
        JustificativaPonto resultado = dao.recuperar(justificativa.getId());
        dao.initialize(resultado.getHistorico());
        return resultado;
	}

    @Override
    public JustificativaPontoDTO recuperar(Serializable id) {
        JustificativaPonto j = dao.recuperar(id);
        dao.initialize(j.getHistorico());
        return mapper.map(j, JustificativaPontoDTO.class);
    }

}
