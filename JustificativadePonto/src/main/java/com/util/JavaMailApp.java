package com.util;



import java.util.List;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.mchange.v1.util.StringTokenizerUtils;
import com.util.mail.JavaMailConf;

public class JavaMailApp {

    private Session session;

    
    public JavaMailApp() {
        JavaMailConf confMail = new JavaMailConf();
        session = confMail.Config();
    }
    
        

   public void sendMail(String remetente,List<String> destinatarioList,Integer idDoc){
        try {

            if (remetente == null){
                remetente = remetente;}
            
            String strSubject = new String(); 
            strSubject ="Enviando email com JavaMail";
            
            String corpo = new String();
            
            if (corpo == null){
            	corpo = "Enviei este email utilizando JavaMail com minha conta GMail!";
            	}

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress(remetente)); // Remetente

            String strDestinatario = new String();
           // strDestinatario = // .join(destinatarioList,",");
            
         //   strDestinatario= destinatarioList. (destinatarioList, ", ");
            
            
            
         Address[] toUser = InternetAddress.parse(strDestinatario);

            
            
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
