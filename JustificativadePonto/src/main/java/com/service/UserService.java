package com.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import com.util.PassPhrase;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import com.dao.IUserDAO;
import com.domain.dto.CadastroSenha;
import com.domain.dto.CadastroUsuario;
import com.domain.dto.UsuarioLogado;
import com.domain.dto.UsuarioLogin;
import com.domain.dto.exception.BusinessException;
import com.model.PerfilEnum;
import com.model.User;
import com.util.Criptografia;
import com.service.mail.IMailService;

@Transactional(readOnly = true)
public class UserService implements IUserService {


	private static final long serialVersionUID = 1L;

	private IUserDAO dao;
	private Mapper mapper;
	protected transient IMailService mailService;

	public UserService(IUserDAO dao, Mapper mapper, IMailService mailService) {
		this.dao = dao;
		this.mapper = mapper;
		this.mailService = mailService;
	}

	@Transactional(readOnly = false)
	public void addUser(User user) {
		dao.adicionar(user); 		// verifica se existe o ID cadastrado
	}

	@Override
	public boolean isExisteUser(CadastroUsuario usuario) {
		User userAmostra = new User();
		userAmostra.setCpf(usuario.getCpf());
		final boolean resultado = dao.encontraPorAmostra(userAmostra)!=null;
		if(!resultado){
			userAmostra.setCpf(null);
			userAmostra.setEmail(usuario.getEmail());
			return dao.encontraPorAmostra(userAmostra)!=null;
		}
		return true;
	}

    @Override
	public UsuarioLogado buscaPorLogin(UsuarioLogin usuarioLogin) {
		User user = new User();
		user.setEmail(usuarioLogin.getEmail());
		user.setSenha(Criptografia.encodePassword(usuarioLogin.getSenha()));
		user = dao.encontraPorAmostra(user);
		if(user==null){
			return null;
		}
		return mapper.map(user, UsuarioLogado.class);
	}


	@Override
    @Transactional(readOnly = false)
	public String resetaSenha(UsuarioLogin usuarioLogin) {
		final User userAmostra = new User();
		userAmostra.setEmail(usuarioLogin.getEmail());
		final User encontrado = dao.encontraPorAmostra(userAmostra);
        if(encontrado!=null){
            final String novaSenha = PassPhrase.getNext();
            encontrado.setSenha(Criptografia.encodePassword(novaSenha));
            return novaSenha;
        }
		return null;
	}


	@Override
	public List<CadastroUsuario> recuperaCoordenadores() {
		return retornaUsuariosPorPerfil(EnumSet.of(PerfilEnum.COORDENADOR));
	}

	@Override
	public List<CadastroUsuario> recuperaSuperintendentes() {
		return retornaUsuariosPorPerfil(EnumSet.of(PerfilEnum.SUPERINTENDENTE));
	}

	@Override
	public List<CadastroUsuario> recuperaRH() {
		return retornaUsuariosPorPerfil(EnumSet.of(PerfilEnum.RH));
	}

	private List<CadastroUsuario> retornaUsuariosPorPerfil(EnumSet<PerfilEnum> perfis){
		List<User> source = dao.listar(perfis);
		List<CadastroUsuario> resultado = new ArrayList<CadastroUsuario>(3);
		for(User u : source){
			resultado.add(mapper.map(u, CadastroUsuario.class));
		}
		return resultado;
	}

	@Override
	@Transactional(readOnly = false)
	public void alteraSenha(CadastroSenha cadastroSenha) {

		User user = dao.recuperar(cadastroSenha.getId());

		if(user==null){
			throw new IllegalArgumentException("Usu�rio n�o existe no sistema!");
		}

		if (!user.getSenha().equals(
				Criptografia.encodePassword(cadastroSenha.getSenha()))) {
			throw new BusinessException("login.passw.confirm");
		}

		user.setSenha(Criptografia.encodePassword(cadastroSenha.getNovaSenha()));

	}

	@Override
	public User recuperar(Integer id) {
		return dao.recuperar(id);
	}

	@Transactional(readOnly = false)
	public void updateUser(User user) {
		dao.atualizar(user);
	}

	@Override
	@Transactional(readOnly = false)
	public void atualizar(CadastroUsuario usuario) {
		User usuarioPersistido = dao.recuperar(usuario.getId());
		mapper.map(usuario, usuarioPersistido);
		dao.atualizar(usuarioPersistido);
	}

	@Override
	@Transactional(readOnly = false)
	public CadastroUsuario adicionar(CadastroUsuario usuario) {
		User user = mapper.map(usuario, User.class);
		Serializable id = dao.adicionar(user);

		user.setSenha(Criptografia.encodePassword(getDefaultPassword(user
				.getCpf())));

		usuario.setId((Integer) id);

		return usuario;
	}

	@Override
	public CadastroUsuario recuperar(Serializable id) {
		User user = dao.recuperar(id);
		CadastroUsuario usuario = mapper.map(user, CadastroUsuario.class);
		return usuario;
	}

	@Override
	@Transactional(readOnly = false)
	public void apagar(CadastroUsuario usuario) {
		User user = dao.recuperar(usuario.getId());
		dao.deletar(user);
	}

	@Override
	public List<CadastroUsuario> todos() {
		List<CadastroUsuario> resultado = new ArrayList<CadastroUsuario>(5);
		List<User> usuarios = dao.todos();
		for(User u : usuarios){
			resultado.add(mapper.map(u, CadastroUsuario.class));
		}
		return resultado;
	}

	/**Senha default
	 * composto pelos 5 primeiros numeros do CPF
	 * */
	 public String getDefaultPassword(final String cpf){
		 return cpf.replace(".","").replace("-", "").substring(0, 5);
	 }


}
