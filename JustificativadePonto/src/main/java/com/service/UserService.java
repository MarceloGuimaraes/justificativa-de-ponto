package com.service;

import com.dao.IUserDAO;
import com.domain.dto.CadastroSenha;
import com.domain.dto.CadastroUsuario;
import com.domain.dto.UsuarioLogado;
import com.domain.dto.UsuarioLogin;
import com.domain.dto.exception.BusinessException;
import com.model.PerfilEnum;
import com.model.User;
import org.dozer.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

@Transactional(readOnly = true)
public class UserService implements IUserService,Serializable {


    private static final long serialVersionUID = 1L;

    IUserDAO dao;

    Mapper mapper;

    public UserService(IUserDAO dao, Mapper mapper) {
        this.dao = dao;
        this.mapper = mapper;
    }

    @Transactional(readOnly = false)
    public void addUser(User user) {
        dao.adicionar(user); 		// verifica se existe o ID cadastrado
    }

    @Override
    public boolean isExisteUser(CadastroUsuario usuario) {
        User userAmostra = new User();
        userAmostra.setCpf(usuario.getCpf());
        userAmostra.setEmail(usuario.getEmail());
        return (dao.encontraPorAmostra(userAmostra)!=null);
    }

    @Override
    public UsuarioLogado buscaPorLogin(UsuarioLogin usuarioLogin) {
        User user = new User();
        user.setEmail(usuarioLogin.getEmail());
        user.setSenha(usuarioLogin.getSenha());
        user = dao.encontraPorAmostra(user);
        if(user==null){
            return null;
        }
        return mapper.map(user, UsuarioLogado.class);
    }

    @Override
    public List<User> recuperaCoordenadores() {
        return dao.listar(EnumSet.of(PerfilEnum.COORDENADOR));
    }

    @Override
    public List<User> recuperaSuperintendentes() {
        return dao.listar(EnumSet.of(PerfilEnum.SUPERINTENDENTE));
    }

    @Override
    public List<User> recuperaRH() {
        return dao.listar(EnumSet.of(PerfilEnum.RH));
    }

    @Override
    @Transactional(readOnly = false)
    public void alteraSenha(CadastroSenha cadastroSenha) {

        User user = dao.recuperar(cadastroSenha.getId());

        if(user==null){
            throw new IllegalArgumentException("Usuário não existe no sistema!");
        }

        if(!user.getSenha().equals(cadastroSenha.getSenha())){
            throw new BusinessException("login.passw.confirm");
        }

        user.setSenha(cadastroSenha.getNovaSenha());

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

        user.setSenha(getDefaultPassword(user.getCpf()));

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
    private String getDefaultPassword(String strCpf){
        return strCpf.replace(".","").replace("-", "").substring(0, 5);
    }

}
