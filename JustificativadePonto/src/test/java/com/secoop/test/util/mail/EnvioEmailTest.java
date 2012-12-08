package com.secoop.test.util.mail;

import com.model.User;
import com.util.JavaMailApp;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

public class EnvioEmailTest {

    @Test
    public void enviaUmEmailDeTesteParamaisDeUmDestinatario(){
        ApplicationContext context= new ClassPathXmlApplicationContext("applicationContext.xml");

        JavaMailApp mailApp = (JavaMailApp) context.getBean("mailApp");
        List<User> destinatarios = new LinkedList<User>();
        User u1 = new User();
        User u2 = new User();
        User u3 = new User();
        u1.setNome("Raphael Rodrigues");
        u1.setEmail("rxonda@gmail.com");
        u2.setNome("Marcelo Gêlo");
        u2.setEmail("celoguimaraes@gmail.com");
        u3.setNome("Beltrano da Silva");
        u3.setEmail("beltrano@secoop.com.br");
        destinatarios.add(u1);
        mailApp.sendMail(u3, destinatarios, 23984);
    }
}
