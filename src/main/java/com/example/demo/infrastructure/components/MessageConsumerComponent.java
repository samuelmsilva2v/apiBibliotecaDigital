package com.example.demo.infrastructure.components;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.demo.domain.models.entities.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MessageConsumerComponent {

	@Autowired
	private ObjectMapper mapper;
	@Autowired
	private JavaMailSender javaMailSender;

	@Value("${spring.mail.username}")
	String username;

	@RabbitListener(queues = "usuarios")
	public void receive(@Payload String message) throws Exception {

		try {
			var usuario = mapper.readValue(message, Usuario.class);

			var mail = javaMailSender.createMimeMessage();
			var helper = new MimeMessageHelper(mail);
			helper.setTo(usuario.getEmail());
			helper.setFrom(username);
			helper.setSubject("Bem-vindo à Biblioteca Digital");
			helper.setText("Olá " + usuario.getNome() + ", seja bem-vindo à Biblioteca Digital!");

			javaMailSender.send(mail);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
