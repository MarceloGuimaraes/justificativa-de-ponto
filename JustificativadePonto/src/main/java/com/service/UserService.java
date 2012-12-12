package com.service;

import com.dao.IUserDAO;
import com.domain.dto.CadastroUsuario;
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
    public User buscaPorLogin(User user) {
        return dao.buscaPorLogin(user);
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
    public User recuperar(Integer id) {
        return dao.recuperar(id);
    }

    @Transactional(readOnly = false)
    public void deleteUser(User user) {
        dao.deletar(user);
    }

    @Transactional(readOnly = false)
    public void updateUser(User user) {
        dao.atualizar(user);
    }

    public User getUserById(User user) {
        return dao.recuperar(user);
    }

    private User getUserByCpf(User user) {
        return dao.recuperarPorCpf(user);
    }

    private User getUserByEmail(User user) {
        return dao.recuperarPorEmail(user);
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
