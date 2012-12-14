package com.service.mail;

import com.model.User;

import java.io.Serializable;
import java.util.List;

public interface IMailService extends Serializable {

    void enviarCoordenador(User remetente, List<User> destinatarios,
                           Integer idDoc);
    void enviarSuperintendente(User remetente, List<User> destinatarios,
                               Integer idDoc);
    void enviarRh(User remetente, List<User> destinatarios, Integer idDoc);
    void concluiRh(User remetente, List<User> destinatarios,
                   Integer idDoc);
    void cancelado(User remetente, List<User> destinatarios,
                   Integer idDoc);

}
