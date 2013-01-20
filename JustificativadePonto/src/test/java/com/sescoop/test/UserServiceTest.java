package com.sescoop.test;

import com.domain.dto.CadastroUsuario;
import com.model.PerfilEnum;
import com.model.User;
import com.service.IUserService;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rxonda
 * Date: 1/13/13
 * Time: 3:26 PM
 * Testa os Servicos de usuario
 */
public class UserServiceTest extends TesteBase {

    @Autowired
    private Mapper mapper;

    @Autowired
    private IUserService UserService;

    @Test
    public void deveMapearUsuario(){
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

        CadastroUsuario cadUser = mapper.map(user, CadastroUsuario.class);

        Assert.assertEquals("O nome do usuario cadastrado deve ser ", "Raphael R", cadUser.getNome());
    }

    @Test
    public void deveAdicionarUsuario(){
        CadastroUsuario usuario = new CadastroUsuario();
        usuario.setNome("Evandro Mesquita");
        usuario.setEmail("emesquita@sescoop.com.br");
        usuario.setCpf("098.087.765-36");
        List<PerfilEnum> perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.SUPERINTENDENTE);
        usuario.setPerfil(perfis);

        CadastroUsuario salvo = UserService.adicionar(usuario);

        Assert.assertNotNull("O usuario deve estar salvo", salvo);
        Assert.assertEquals("O nome do usuario salvo deve ser ", "Evandro Mesquita", salvo.getNome());
    }
}
