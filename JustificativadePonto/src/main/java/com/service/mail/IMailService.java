package com.service.mail;

import com.domain.dto.UsuarioLogado;
import com.domain.dto.UsuarioLogin;
import com.model.User;

import java.io.Serializable;
import java.util.List;

public interface IMailService {

    void enviarCoordenador(UsuarioLogado usuarioLogado, User coordenador, Integer idDoc);
    void enviarSuperintendente(UsuarioLogado usuarioLogado, User solicitante, User superIntendente, Integer idDoc);
    void enviarRh(UsuarioLogado usuarioLogado, User solicitante, User coordenador, User rh, Integer idDoc);
    void concluiRh(UsuarioLogado usuarioLogado, User solicitante, User coordenador, User superintendente, Integer idDoc);
    void cancelado(UsuarioLogado usuarioLogado, User solicitante,  List<User> copyTo, Integer idDoc);
    void resetaSenha(UsuarioLogin userMail,String passwd);
    void reprovadoCoordenador(User remetente, User solicitante, Integer id, String cancelamento);
    void reprovadoSuperintendente(User usuarioLogado, User solicitante, User coordenador, Integer id, String cancelamento);
}
