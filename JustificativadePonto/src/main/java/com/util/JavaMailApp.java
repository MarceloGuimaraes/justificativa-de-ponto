package com.util;


import com.model.User;
import com.util.mail.JavaMailConf;

import javax.mail.Address;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.LinkedList;
import java.util.List;

public class JavaMailApp {

    private Session session;

    public JavaMailApp(JavaMailConf conf) {
        this.session = conf.newSession();
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
        try {

            String strSubject = Message.getBundleMessage("mail.teste.subject");

            String corpo = Message.getBundleMessage("mail.teste.corpo");

            javax.mail.Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(remetente)); // Remetente

            Address[] toUser = new Address[destinatarios.size()];
            int index = 0;
            for(String destinatario : destinatarios){
                toUser[index++] = new InternetAddress(destinatario);
            }

            message.setRecipients(javax.mail.Message.RecipientType.TO, toUser);

            message.setSubject(strSubject); // Assunto

            message.setText(corpo);

            /** Metodo para enviar a mensagem criada */
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

}
