package com.util;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.util.mail.JavaMailConf;

public class JavaMailApp {

    private Session session;

    public JavaMailApp() {

        /* public static void main(String[] args) { */
        /*
           * Properties props = new Properties();
           *//** Parametros de conexao com servidor Gmail */
        /*
           * props.put("mail.smtp.host", "smtp.gmail.com");
           * props.put("mail.smtp.socketFactory.port", "465");
           * props.put("mail.smtp.socketFactory.class",
           * "javax.net.ssl.SSLSocketFactory"); props.put("mail.smtp.auth",
           * "true"); props.put("mail.smtp.port", "465");
           *
           * Session session = Session.getDefaultInstance(props, new
           * javax.mail.Authenticator() { protected PasswordAuthentication
           * getPasswordAuthentication() { return new
           * PasswordAuthentication("EMAILDOGMAILm;", "SENHADOGMAIL"); } });
           *//** Ativa Debug para sessao */
        /*
           * session.setDebug(true);
           */

        JavaMailConf confMail = new JavaMailConf();
        session = confMail.Config();
    }

    public void sendMail(String remetente,String strSubject,String texto){

        try {

            if (remetente == null){
                remetente = "celoguimaraes@gmail.com";}
            if (strSubject == null){
                strSubject = "Enviando email com JavaMail";}
            if (texto == null){
                texto = "Enviei este email utilizando JavaMail com minha conta GMail!";}


            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(remetente)); // Remetente

            Address[] toUser = InternetAddress // Destinatario(s)
                    .parse("guimarcelo@yahoo.com.br, seucolega@hotmail.com, seuparente@yahoo.com.br");

            message.setRecipients(Message.RecipientType.TO, toUser);

            message.setSubject(strSubject);// Assunto
            message.setText(texto);
            /** Metodo para enviar a mensagem criada */
            Transport.send(message);

            System.out.println("Feito!!!");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }


    }

}
