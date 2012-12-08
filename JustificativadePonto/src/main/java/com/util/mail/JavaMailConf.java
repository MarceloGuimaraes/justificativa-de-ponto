package com.util.mail;

import java.util.Properties;

import javax.mail.PasswordAuthentication;
import javax.mail.Session;



public class JavaMailConf {

    Properties props;

    boolean desenv;

    public JavaMailConf(Properties props){
        this.props = props;
        if(props.containsKey("app.environment.desenv") &&
                props.get("app.environment.desenv").equals("true")){
            desenv = true;
        }else{
            desenv = false;
        }
    }

    public boolean isDesenv() {
        return desenv;
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
        if(props.containsKey("app.environment.debug") &&
                props.get("app.environment.debug").equals("true")){
            session.setDebug(true);
        }

        return session;
    }
}
