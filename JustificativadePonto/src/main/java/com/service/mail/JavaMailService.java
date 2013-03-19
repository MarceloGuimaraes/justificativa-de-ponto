package com.service.mail;

import com.domain.dto.UsuarioLogado;
import com.domain.dto.UsuarioLogin;
import com.model.User;
import com.util.JsfUtil;
import com.util.Message;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.LinkedList;
import java.util.List;

public class JavaMailService implements IMailService {
    private static final String URL_ID = "/pages/justificativa.faces?id=";
    private static final String REMETENTE_SISTEMA = "Sistema de Ponto<sistemadeponto@sescoop.com.br>";
    private MailSender mailSender;
    private boolean desenv;

    public JavaMailService(MailSender mailSender, boolean desenv) {
        this.mailSender = mailSender;
        this.desenv = desenv;
    }

    @Override
    public void enviarCoordenador(UsuarioLogado remetente, User coordenador, Integer idDoc) {
        final String assunto = Message.getBundleMessage("mail.subject.aguardandoaprovacao.coordenador");
        final String urlAux = formataUrlAcessaJustificativa(idDoc);
        final String corpo = Message.getBundleMessage("mail.corpo.enviocoord", coordenador.getNome(), idDoc.toString(), urlAux);
        final List<String> destinatarios = new LinkedList<String>();
        destinatarios.add(formata(coordenador));
        sendMail(formata(remetente), destinatarios, assunto, corpo);
    }

    @Override
    public void enviarSuperintendente(UsuarioLogado remetente, User solicitante, User superIntendente, Integer idDoc) {
        final String assunto = Message.getBundleMessage("mail.subject.aguardandoaprovacao.superintendente");
        final String urlAux = formataUrlAcessaJustificativa(idDoc);
        final String corpo = Message.getBundleMessage("mail.corpo.enviosuper", superIntendente.getNome(), idDoc.toString(), urlAux);
        final List<String> destinatarios = retornaListaFormatada(superIntendente);
        if (solicitante != null) {
            destinatarios.add(formata(solicitante));
        }
        sendMail(formata(remetente), destinatarios, assunto, corpo);
    }

    @Override
    public void enviarRh(UsuarioLogado remetente, User solicitante, User coordenador, User rh, Integer idDoc) {
        final String assunto = Message.getBundleMessage("mail.subject.aguardandoaprovacao.rh");
        final String urlAux = formataUrlAcessaJustificativa(idDoc);
        final String corpo = Message.getBundleMessage("mail.corpo.enviorh", rh.getNome(), idDoc.toString(), urlAux);
        final List<String> destinatarios = retornaListaFormatada(solicitante);
        if (coordenador != null) {
            destinatarios.add(formata(coordenador));
        }
        destinatarios.add(formata(rh));
        sendMail(formata(remetente), destinatarios, assunto, corpo);
    }

    @Override
    public void concluiRh(UsuarioLogado remetente, User solicitante, User coordenador, User superintendente, Integer idDoc) {
        final String assunto = Message.getBundleMessage("mail.subject.aprovado.rh.concluir");
        final String urlAux = formataUrlAcessaJustificativa(idDoc);
        final String corpo = Message.getBundleMessage("mail.corpo.concluirh", solicitante.getNome(), idDoc.toString(), urlAux);
        final List<String> destinatarios = retornaListaFormatada(solicitante);
        if (coordenador != null) {
            destinatarios.add(formata(coordenador));
        }
        destinatarios.add(formata(superintendente));
        sendMail(formata(remetente), destinatarios, assunto, corpo);
    }

    @Override
    public void cancelado(UsuarioLogado remetente, User solicitante, List<User> copyTo, Integer idDoc) {
        final String assunto = Message.getBundleMessage("mail.subject.cancelado");
        final String urlAux = formataUrlAcessaJustificativa(idDoc);
        final String corpo = Message.getBundleMessage("mail.corpo.cancelado", solicitante.getNome(), idDoc.toString(), urlAux);
        final List<String> destinatarios = retornaListaFormatada(solicitante);
        destinatarios.addAll(formata(copyTo));
        sendMail(formata(remetente), destinatarios, assunto, corpo);
    }

    @Override
    public void resetaSenha(final UsuarioLogin userMail, final String passwd) {
        final String assunto = Message.getBundleMessage("mail.subject.resetaSenha");
        final String corpo = Message.getBundleMessage("mail.corpo.resetaSenha", passwd);
        final List<String> destinatarios = new LinkedList<String>();
        destinatarios.add(userMail.getEmail());
        sendMail(REMETENTE_SISTEMA, destinatarios, assunto, corpo);
    }


    private String formataUrlAcessaJustificativa(Integer idDoc) {
        return JsfUtil.getUrlToSendMail() + URL_ID + idDoc.toString();
    }

    private String formata(String nome, String email) {
        StringBuilder endereco = new StringBuilder(nome);
        endereco.append("<");
        endereco.append(email);
        endereco.append(">");
        return endereco.toString();
    }

    private String formata(User user) {
        return formata(user.getNome(), user.getEmail());
    }

    private String formata(UsuarioLogado user) {
        return formata(user.getNome(), user.getEmail());
    }

    private List<String> formata(final List<User> users) {
        final List<String> destinatarios = new LinkedList<String>();
        if (users == null) {
            return destinatarios;
        }
        for (User u : users) {
            destinatarios.add(formata(u));
        }
        return destinatarios;
    }

    private List<String> retornaListaFormatada(User... users) {
        final List<User> resultado = new LinkedList<User>();
        for (User u : users) {
            resultado.add(u);
        }
        return formata(resultado);
    }

    private void sendMail(final String remetente,
                          final List<String> destinatarios,
                          final String subject,
                          final String body) {

        Runnable mailThread = new Runnable() {
            @Override
            public void run() {
                SimpleMailMessage message = new SimpleMailMessage();

                String[] toUser = new String[destinatarios.size()];
                int index = 0;
                for (String destinatario : destinatarios) {
                    toUser[index++] = destinatario;
                }

                message.setFrom(remetente);
                message.setTo(toUser);
                message.setSubject(subject);
                message.setText(body);

                // previne de ficar enviando email enquanto em desenvolvimento
                // para comecar a enviar email, acesse o arquivo config.properties
                // dentro de WEB-INF/config
                // e coloque a variavel app.environment.desenv como false
                if (!desenv) {
                    mailSender.send((SimpleMailMessage) message);
                }
            }
        };

        new Thread(mailThread).start();

    }
}
