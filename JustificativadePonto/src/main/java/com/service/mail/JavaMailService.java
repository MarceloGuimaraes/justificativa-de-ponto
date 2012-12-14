package com.service.mail;

import com.model.User;
import com.util.Message;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import java.util.LinkedList;
import java.util.List;

public class JavaMailService {

	private MailSender mailSender;
	private boolean desenv;
	private String assunto;
	private String corpo;

	public JavaMailService(org.springframework.mail.MailSender mailSender,
			boolean desenv) {
		this.mailSender = mailSender;
		this.desenv = desenv;
	}

	private String formata(User user) {
		StringBuilder endereco = new StringBuilder(user.getNome());
		endereco.append("<");
		endereco.append(user.getEmail());
		endereco.append(">");
		return endereco.toString();
	}

    private List<String> formata(List<User> users){
        List<String> destinatarios = new LinkedList<String>();
        if(users==null){
            return destinatarios;
        }
        for (User u : users){
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

	public void enviarCoordenador(User remetente, List<User> destinatarios,
			Integer idDoc) {

		assunto = Message
				.getBundleMessage("mail.teste.subject"); //mail.subject.aguardandoaprovacao.coordenador");
		corpo = Message.getBundleMessage("mail.teste.corpo"); //mail.corpo.enviocoord");

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto, corpo);
	}

	public void enviarSuperintendente(User remetente, List<User> destinatarios,
			Integer idDoc) {

		assunto = Message
				.getBundleMessage("mail.subject.aguardandoaprovacao.superintendente");
		corpo = Message.getBundleMessage("mail.corpo.enviosuper");

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto, corpo);
	}

	public void enviarRh(User remetente, List<User> destinatarios, Integer idDoc) {

		assunto = Message
				.getBundleMessage("mail.subject.aguardandoaprovacao.rh");
		corpo = Message.getBundleMessage("mail.corpo.enviorh");

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto, corpo);
	}

	
	public void concluiRh(User remetente, List<User> destinatarios,
			Integer idDoc) {

		assunto = Message
				.getBundleMessage("mail.subject.aprovado.rh.concluir");
		corpo = Message.getBundleMessage("mail.corpo.concluirh");

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto, corpo);
	}

	
	public void cancelado(User remetente, List<User> destinatarios,
			Integer idDoc) {

		assunto = Message
				.getBundleMessage("mail.subject.cancelado");
		corpo = Message.getBundleMessage("mail.corpo.cancelado");

		sendMail(formata(remetente), formata(destinatarios), idDoc, assunto, corpo);

	}

}
