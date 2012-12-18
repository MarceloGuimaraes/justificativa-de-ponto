package com.service;

import com.dao.IJustificativaDAO;
import com.domain.dto.UsuarioLogado;
import com.model.JustificativaPonto;
import com.model.StatusEnum;
import com.model.TipoEventoJustificativaPontoEnum;
import com.model.User;
import org.dozer.Mapper;
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
    @Transactional(readOnly = false)
    public void mudaSituacao(JustificativaPonto justificativa,
                             UsuarioLogado usuarioLogado,
                             StatusEnum novoStatus,
                             TipoEventoJustificativaPontoEnum... eventoHistorico) {
        justificativa.setStatus(novoStatus);
        User user = mapper.map(usuarioLogado, User.class);
        if(eventoHistorico!=null){
            for(TipoEventoJustificativaPontoEnum tipoEvento : eventoHistorico){
                justificativa.adiciona(user, tipoEvento);
            }
        }
        if(justificativa.getJustificativaId()==null || justificativa.getJustificativaId()==0){
            adicionar(justificativa);
        }else{
            atualizar(justificativa);
        }
    }

    @Override
    @Transactional(readOnly = false)
    public void cancelar(UsuarioLogado usuarioLogado, JustificativaPonto justificativa) {
        justificativa.setDtCancelamento(new Date());
        mudaSituacao(justificativa, usuarioLogado, StatusEnum.CANCELADO, TipoEventoJustificativaPontoEnum.CANCELADO);
    }

    @Override
    public JustificativaPonto nova(UsuarioLogado usuarioLogado) {
        User user = mapper.map(usuarioLogado, User.class);
        return new JustificativaPonto(user);
    }

    @Override
	public JustificativaPonto recuperar(
            JustificativaPonto justificativa) {
		return dao.recuperar(justificativa);
	}

    @Override
    public JustificativaPonto recuperar(Serializable id) {
        JustificativaPonto j = dao.recuperar(id);
        dao.initialize(j.getHistorico());
        return j;
    }

}
