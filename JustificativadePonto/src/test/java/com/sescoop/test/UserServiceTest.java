package com.sescoop.test;

import com.domain.dto.CadastroUsuario;
import com.model.PerfilEnum;
import com.model.User;
import com.service.IUserService;
import org.dozer.Mapper;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: rxonda
 * Date: 1/13/13
 * Time: 3:26 PM
 * Testa os Servicos de usuario
 */
@TransactionConfiguration(transactionManager = "txManager", defaultRollback = true)
public class UserServiceTest extends TesteBase {

    @Resource(name = "mapper")
    private Mapper mapper;

    @Resource(name = "UserService")
    private IUserService userService;

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
    @Transactional
    public void deveAdicionarUsuario(){
        CadastroUsuario usuario = new CadastroUsuario();
        usuario.setNome("Evandro Mesquita");
        usuario.setEmail("emesquita@sescoop.com.br");
        usuario.setCpf("098.087.765-36");
        List<PerfilEnum> perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.SUPERINTENDENTE);
        usuario.setPerfil(perfis);

        CadastroUsuario salvo = userService.adicionar(usuario);

        Assert.assertNotNull("O usuario deve estar salvo", salvo);
        Assert.assertEquals("O nome do usuario salvo deve ser ", "Evandro Mesquita", salvo.getNome());
    }

    @Test
    @Transactional
    public void recuperaApenasCoordenadores(){

        CadastroUsuario user = new CadastroUsuario();
        user.setNome("Raphael R");
        user.setEmail("teste@teste.com");
        user.setCpf("120.120.120-10");
        List<PerfilEnum> perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.COORDENADOR);
        perfis.add(PerfilEnum.USUARIO);
        user.setPerfil(perfis);

        userService.adicionar(user);

        user = new CadastroUsuario();
        user.setNome("Maria Vasconcelos");
        user.setEmail("mvasconselos@teste.com");
        user.setCpf("120.120.121-00");

        perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.SUPERINTENDENTE);
        perfis.add(PerfilEnum.USUARIO);
        user.setPerfil(perfis);

        userService.adicionar(user);

        List<CadastroUsuario> coords = userService.recuperaCoordenadores();

        junit.framework.Assert.assertEquals("A quantidade na listagem deve ser ", 1, coords.size());
        junit.framework.Assert.assertEquals("O nome do usuario coordendor deve ser ", "Raphael R", coords.get(0).getNome());

    }

    @Test
    @Transactional
    public void deveSalvarPerfilDoUsuario() {

        CadastroUsuario user = new CadastroUsuario();
        user.setNome("Róger Çaricô");
        user.setCpf("120.120.201-09");
        user.setEmail("rcar@mail.com");
        List<PerfilEnum> perfis = new LinkedList<PerfilEnum>();
        perfis.add(PerfilEnum.COORDENADOR);
        perfis.add(PerfilEnum.USUARIO);
        user.setPerfil(perfis);

        CadastroUsuario salvo = userService.adicionar(user);

        junit.framework.Assert.assertNotNull("Deve ter salvo o usuario", salvo);

        junit.framework.Assert.assertEquals("O nome deve ser ", "Róger Çaricô", salvo.getNome());

        junit.framework.Assert.assertEquals("A qtd de perfis do usuario deve ser ", 2, user.getPerfil().size());

        List<PerfilEnum> perfisParaComparar = new LinkedList<PerfilEnum>();
        perfisParaComparar.add(PerfilEnum.COORDENADOR);
        perfisParaComparar.add(PerfilEnum.USUARIO);

        junit.framework.Assert.assertEquals("Os perfis devem ser ", perfisParaComparar, user.getPerfil());

    }
}
