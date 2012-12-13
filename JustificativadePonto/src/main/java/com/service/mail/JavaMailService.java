package com.service.mail;


import com.model.User;
import com.util.Message;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.LinkedList;
import java.util.List;

public class JavaMailService {

    private MailSender mailSender;
    private boolean desenv;

    public JavaMailService(org.springframework.mail.MailSender mailSender, boolean desenv) {
        this.mailSender = mailSender;
        this.desenv = desenv;
    }

    public void sendMail(User remetente, List<User> destinatarios, Integer idDoc){

        List<String> formatados = new LinkedList<String>();

        for(User u : destinatarios){
            formatados.add(formata(u));
        }

        sendMail(formata(remetente), formatados, idDoc);

    }

    private String formata(User user){
        StringBuilder endereco = new StringBuilder(user.getNome());
        endereco.append("<");
        endereco.append(user.getEmail());
        endereco.append(">");
        return endereco.toString();
    }


    public void sendMail(String remetente, List<String> destinatarios, Integer idDoc){
            SimpleMailMessage message = new SimpleMailMessage();

            String [] toUser = new String[destinatarios.size()];
            int index = 0;
            for(String destinatario : destinatarios){
                toUser[index++] = destinatario;
            }

            message.setFrom(remetente);
            message.setTo(toUser);
            message.setSubject(Message.getBundleMessage("mail.teste.subject"));
            message.setText(Message.getBundleMessage("mail.teste.corpo"));

            //previne de ficar enviando email enquanto em desenvolvimento
            //para comecar a enviar email, acesse o arquivo config.properties dentro de WEB-INF/config
            //e coloque a variavel app.environment.desenv como false
            if (!desenv) {
                mailSender.send((SimpleMailMessage) message);
            }

    }

}
