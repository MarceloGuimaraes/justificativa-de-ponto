package com.sescoop.test.sql;

import com.dao.IUserDAO;
import com.model.PerfilEnum;
import com.model.User;
import com.sescoop.test.TesteBase;
import junit.framework.Assert;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;
import java.util.EnumSet;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * User: rxonda
 * Date: 1/7/13
 * Time: 1:02 AM
 * Testes do Dao de Usuarios
 */
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
@Transactional
public class UserDaoTest extends TesteBase {

    @Resource(name = "UserDAO")
    private IUserDAO userDAO;

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

        userDAO.adicionar(user);

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

        userDAO.adicionar(user);

        List<User> coords = userDAO.listar(EnumSet.of(PerfilEnum.COORDENADOR));

        Assert.assertEquals("A quantidade na listagem deve ser ",1 , coords.size());
        Assert.assertEquals("O nome do usuario coordendor deve ser ", "Raphael R", coords.get(0).getNome());

    }

    @Test
    public void deveSalvarPerfilDoUsuario() {

        User user = new User();
        user.setNome("Róger Çaricô");
        user.setAtivo(true);
        user.setCpf("120.120.201-09");
        user.setEmail("rcar@mail.com");
        user.setSenha("senha");
        List<PerfilEnum> perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.COORDENADOR);
        perfis.add(PerfilEnum.USUARIO);
        user.setPerfil(perfis);

        Serializable id = userDAO.adicionar(user);

        User salvo = userDAO.recuperar(id);

        Assert.assertNotNull("Deve ter salvo o usuario", salvo);

        Assert.assertEquals("O nome deve ser ", "Róger Çaricô", salvo.getNome());

        Assert.assertEquals("A qtd de perfis do usuario deve ser ", 2, user.getPerfil().size());

        List<PerfilEnum> perfisParaComparar = new LinkedList<PerfilEnum>();
        perfisParaComparar.add(PerfilEnum.COORDENADOR);
        perfisParaComparar.add(PerfilEnum.USUARIO);

        Assert.assertEquals("Os perfis devem ser ", perfisParaComparar, user.getPerfil());

    }

}
