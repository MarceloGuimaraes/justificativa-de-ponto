package com.service;

import com.dao.IJustificativaDAO;
import com.domain.dto.JustificativaPontoDTO;
import com.domain.dto.UsuarioLogado;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import org.dozer.Mapper;
import org.hibernate.Hibernate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Date;

@Transactional(readOnly = true)
public class JustificativaService implements IJustificativaService,
		Serializable {

	private static final long serialVersionUID = 1L;
	
	private IJustificativaDAO dao;


    private Mapper mapper;

    public JustificativaService(IJustificativaDAO dao, Mapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Override
	@Transactional(readOnly = false)
	public void adicionar(JustificativaPonto justificativa) {
		dao.adicionar(justificativa);
	}

    @Override
	@Transactional(readOnly = false)
	public void atualizar(JustificativaPonto justificativa) {
		dao.atualizar(justificativa);
	}

    @Override
	@Transactional(readOnly = false)
	public void apagar(JustificativaPonto justificativa) {
		dao.deletar(justificativa);
	}

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public JustificativaPontoDTO mudaSituacao(UsuarioLogado usuarioLogado,
                             UsuarioLogado proximoResponsavel,
                             JustificativaPontoDTO justificativaTela,
                             StatusEnum novoStatus,
                             TipoEventoJustificativaPontoEnum eventoHistorico) {

        JustificativaPonto justificativa = null;

        User user = mapper.map(usuarioLogado, User.class);
        User delegado = mapper.map(proximoResponsavel, User.class);

        if(justificativaTela.getId()==null || justificativaTela.getId()==0){
            justificativa = new JustificativaPonto(user);
        } else {
            justificativa = recuperar(justificativaTela);
        }

        mapper.map(justificativaTela, justificativa);

        justificativa.setStatus(novoStatus);

        if(eventoHistorico!=null){
            justificativa.encaminha(user, delegado, eventoHistorico);
        }

        if(justificativa.getId()==null || justificativa.getId()==0){
            adicionar(justificativa);
        }else{
            atualizar(justificativa);
        }

        return mapper.map(justificativa, JustificativaPontoDTO.class);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public JustificativaPontoDTO atua(UsuarioLogado usuarioLogado, JustificativaPontoDTO justificativaTela, StatusEnum novoStatus, TipoEventoJustificativaPontoEnum eventoHistorico) {
        JustificativaPonto justificativa = null;

        User user = mapper.map(usuarioLogado, User.class);


        if(justificativaTela.getId()==null || justificativaTela.getId()==0){
            justificativa = mapper.map(justificativaTela, JustificativaPonto.class);
        } else {
            justificativa = recuperar(justificativaTela);
            mapper.map(justificativaTela, justificativa);
        }

        justificativa.setStatus(novoStatus);

        if(eventoHistorico!=null){
            justificativa.adiciona(user, eventoHistorico);
        }

        if(justificativa.getId()==null || justificativa.getId()==0){
            adicionar(justificativa);
        }else{
            atualizar(justificativa);
        }

        return mapper.map(justificativa, JustificativaPontoDTO.class);

    }

    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public void cancelar(UsuarioLogado usuarioLogado, JustificativaPontoDTO justificativa) {
        atua(usuarioLogado, justificativa, StatusEnum.CANCELADO, TipoEventoJustificativaPontoEnum.CANCELADO);
    }

    @Override
    public JustificativaPontoDTO nova(UsuarioLogado usuarioLogado) {
        return new JustificativaPontoDTO(usuarioLogado);
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
	public JustificativaPonto recuperar(JustificativaPontoDTO justificativa) {
        JustificativaPonto resultado = dao.recuperar(justificativa.getId());
        dao.initialize(resultado.getHistorico());
        return resultado;
	}

    @Override
    public JustificativaPontoDTO recuperar(Serializable id) {
        JustificativaPonto j = dao.recuperar(id);
        dao.initialize(j.getHistorico());
        JustificativaPontoDTO retorno = mapper.map(j, JustificativaPontoDTO.class);
        return retorno;
    }

}
