package com.service.mail;

import com.domain.dto.UsuarioLogin;
import com.model.User;

import java.util.List;

public interface IMailService {

    void enviarCoordenador(User usuarioLogado, User coordenador, Integer idDoc);
    void enviarSuperintendente(User usuarioLogado, User solicitante, User superIntendente, Integer idDoc);
    void enviarRh(User usuarioLogado, User solicitante, User coordenador, User rh, Integer idDoc);
    void concluiRh(User usuarioLogado, User solicitante, User coordenador, User superintendente, Integer idDoc);
    void cancelado(User usuarioLogado, User solicitante, List<User> copyTo, Integer idDoc, String motivoCancelamento);
    void resetaSenha(UsuarioLogin userMail,String passwd);
    void reprovadoCoordenador(User remetente, User solicitante, Integer id, String cancelamento);
    void reprovadoSuperintendente(User usuarioLogado, User solicitante, Integer id, String cancelamento);
}
