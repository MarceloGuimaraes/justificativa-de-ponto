package com.service.mail;

import com.domain.dto.UsuarioLogado;
import com.model.User;
import com.util.JsfUtil;
import com.util.Message;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.LinkedList;
import java.util.List;

public class JavaMailService implements IMailService {

	private MailSender mailSender;
	private boolean desenv;
	private String assunto;
	private String corpo;
	private String urlAux;

	private static final String URL_ID = "/pages/justificativa.faces?id=";

	public JavaMailService(org.springframework.mail.MailSender mailSender,
			boolean desenv) {
		this.mailSender = mailSender;
		this.desenv = desenv;
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

	private List<String> formata(List<User> users) {
		List<String> destinatarios = new LinkedList<String>();
		if (users == null) {
			return destinatarios;
		}
		for (User u : users) {
			destinatarios.add(formata(u));
		}
		return destinatarios;
	}

	public void sendMail(String remetente, List<String> destinatarios,
			Integer idDoc, String subject, String body) {

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

	public void enviarCoordenador(UsuarioLogado remetente,
			List<User> destinatarios, Integer idDoc) {

		assunto = Message
				.getBundleMessage("mail.subject.aguardandoaprovacao.coordenador");

		urlAux = JsfUtil.getUrlToSendMail() + URL_ID + idDoc.toString();

		corpo = Message.getBundleMessage("mail.corpo.enviocoord", urlAux);

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto,
				corpo);
	}

	public void enviarSuperintendente(UsuarioLogado remetente,
			List<User> destinatarios, Integer idDoc) {

		assunto = Message
				.getBundleMessage("mail.subject.aguardandoaprovacao.superintendente");

		urlAux = JsfUtil.getUrlToSendMail() + URL_ID + idDoc.toString();
		corpo = Message.getBundleMessage("mail.corpo.enviosuper", urlAux);

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto,
				corpo);
	}

	public void enviarRh(UsuarioLogado remetente, List<User> destinatarios,
			Integer idDoc) {

		assunto = Message
				.getBundleMessage("mail.subject.aguardandoaprovacao.rh");
		urlAux = JsfUtil.getUrlToSendMail() + URL_ID + idDoc.toString();
		corpo = Message.getBundleMessage("mail.corpo.enviorh", urlAux);

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto,
				corpo);
	}

	public void concluiRh(UsuarioLogado remetente, List<User> destinatarios,
			Integer idDoc) {

		assunto = Message.getBundleMessage("mail.subject.aprovado.rh.concluir");
		urlAux = JsfUtil.getUrlToSendMail() + URL_ID + idDoc.toString();
		corpo = Message.getBundleMessage("mail.corpo.concluirh", urlAux);

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto,
				corpo);
	}

	public void cancelado(UsuarioLogado remetente, List<User> destinatarios,
			Integer idDoc) {

		assunto = Message.getBundleMessage("mail.subject.cancelado");
		urlAux = JsfUtil.getUrlToSendMail() + URL_ID + idDoc.toString();
		corpo = Message.getBundleMessage("mail.corpo.cancelado", urlAux);

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto,
				corpo);

	}

}
