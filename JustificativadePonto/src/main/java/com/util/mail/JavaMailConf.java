package com.util.mail;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;



public class JavaMailConf {

    Properties props;

    public JavaMailConf(Properties props){
        this.props = props;
    }

    public Session newSession(){

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication("contafakemail@gmail.com",
                                "justificativa");
                    }
                });

        /** Ativa Debug para sessao */
        if(props.containsKey("app.environment") &&
                props.get("app.environment").equals("true")){
            session.setDebug(true);
        }

        return session;
    }
}
