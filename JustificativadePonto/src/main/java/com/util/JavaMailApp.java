package com.util;


import com.util.mail.JavaMailConf;

import javax.mail.*;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;

public class JavaMailApp {

    private Session session;


    public JavaMailApp() {
        JavaMailConf confMail = new JavaMailConf();
        session = confMail.Config();
    }



    public void sendMail(String remetente, List<String> destinatarios, Integer idDoc){
        try {

            String strSubject = "Enviando email com JavaMail";

            String corpo = "Enviei este email utilizando JavaMail com minha conta GMail!";

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(remetente)); // Remetente

            Address[] toUser = new Address[destinatarios.size()];
            int index = 0;
            for(String destinatario : destinatarios){
                toUser[index++] = new InternetAddress(destinatario);
            }

            message.setRecipients(Message.RecipientType.TO, toUser);

            message.setSubject(strSubject); // Assunto

            message.setText(corpo);

            /** Metodo para enviar a mensagem criada */
            Transport.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

}
