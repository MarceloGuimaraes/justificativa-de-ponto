package com.service.mail;

import com.domain.dto.UsuarioLogado;
import com.model.User;

import java.io.Serializable;
import java.util.List;

public interface IMailService extends Serializable {

    void enviarCoordenador(UsuarioLogado usuarioLogado, List<User> destinatarios,
                           Integer idDoc);
    void enviarSuperintendente(UsuarioLogado usuarioLogado, List<User> destinatarios,
                               Integer idDoc);
    void enviarRh(UsuarioLogado usuarioLogado, List<User> destinatarios, Integer idDoc);
    void concluiRh(UsuarioLogado usuarioLogado, List<User> destinatarios,
                   Integer idDoc);
    void cancelado(UsuarioLogado usuarioLogado, List<User> destinatarios,
                   Integer idDoc);

}
