package com.sescoop.test.sql;

import com.dao.impl.UserDAO;
import com.model.PerfilEnum;
import com.model.User;
import com.sescoop.test.BaseTest;
import junit.framework.Assert;
import org.junit.Test;
import org.unitils.spring.annotation.SpringBeanByName;

import java.io.Serializable;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rxonda
 * Date: 1/7/13
 * Time: 1:02 AM
 * To change this template use File | Settings | File Templates.
 */
public class TestaCharEncodingTest extends BaseTest {

    @SpringBeanByName
    private UserDAO UserDAO;

    @Test
    public void verificandoEncoding(){

        User user = new User();
        user.setNome("Róger Çaricô");
        user.setAtivo(true);
        user.setCpf("120.120.201-09");
        user.setEmail("rcar@mail.com");
        user.setSenha("senha");

        Serializable id = UserDAO.adicionar(user);

        User userPersistido = UserDAO.recuperar(id);

        Assert.assertNotNull("Deveria ter recuperado o usuario", userPersistido);

        Assert.assertEquals("O nome deve ser ", "Róger Çaricô", userPersistido.getNome());

    }

    @Test
    public void recuperaApenasCoordenadores(){

        User user = new User();
        user.setNome("Raphael R");
        user.setAtivo(true);
        user.setEmail("teste@teste.com");
        user.setSenha("senha123");
        user.setCpf("120.120.120-10");
        List<PerfilEnum> perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.COORDENADOR);
        perfis.add(PerfilEnum.USUARIO);
        user.setPerfil(perfis);

        UserDAO.adicionar(user);

        user = new User();
        user.setNome("Maria Vasconcelos");
        user.setAtivo(true);
        user.setEmail("mvasconselos@teste.com");
        user.setSenha("senha123");
        user.setCpf("120.120.121-00");

        perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.SUPERINTENDENTE);
        perfis.add(PerfilEnum.USUARIO);
        user.setPerfil(perfis);

        UserDAO.adicionar(user);

        List<User> coords = UserDAO.listar(EnumSet.of(PerfilEnum.COORDENADOR));

        Assert.assertEquals("A quantidade na listagem deve ser ",1 , coords.size());

    }

    @Test
    public void deveSalvarPerfilDoUsuario() {

        User user = new User();
        user.setNome("Raphael R");
        user.setAtivo(true);
        user.setEmail("teste@teste.com");
        user.setSenha("senha123");
        user.setCpf("120.120.120.10");
        List<PerfilEnum> perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.COORDENADOR);
        perfis.add(PerfilEnum.USUARIO);
        user.setPerfil(perfis);

        Serializable id = UserDAO.adicionar(user);

        User salvo = UserDAO.recuperar(id);

        Assert.assertNotNull("Deve ter salvo o usuario", salvo);

        Assert.assertEquals("A qtd de perfis do usuario deve ser ", 2, user.getPerfil().size());

        List<PerfilEnum> perfisParaComparar = new LinkedList<PerfilEnum>();
        perfisParaComparar.add(PerfilEnum.COORDENADOR);
        perfisParaComparar.add(PerfilEnum.USUARIO);
        Assert.assertEquals("Os perfis devem ser ", perfisParaComparar, user.getPerfil());

    }

}
