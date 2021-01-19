package com.basis.sge.service.util;

import com.basis.sge.service.servico.DTO.EmailDTO;
import com.basis.sge.service.servico.EmailServico;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import javax.mail.internet.MimeMessage;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
@Getter
@Setter
@RequiredArgsConstructor
public final class EmailUtil {

    private static EmailServico servico = new EmailServico(new JavaMailSender() {
        @Override
        public MimeMessage createMimeMessage() {
            return null;
        }

        @Override
        public MimeMessage createMimeMessage(InputStream inputStream) throws MailException {
            return null;
        }

        @Override
        public void send(MimeMessage mimeMessage) throws MailException {

        }

        @Override
        public void send(MimeMessage[] mimeMessages) throws MailException {

        }

        @Override
        public void send(MimeMessagePreparator mimeMessagePreparator) throws MailException {

        }

        @Override
        public void send(MimeMessagePreparator[] mimeMessagePreparators) throws MailException {

        }

        @Override
        public void send(SimpleMailMessage simpleMailMessage) throws MailException {

        }

        @Override
        public void send(SimpleMailMessage[] simpleMailMessages) throws MailException {

        }
    });

    public void criarEmailCadastro(String email){

        EmailDTO emailDTO = new EmailDTO();
        emailDTO.setAssunto("Cadastro SGE");
        emailDTO.setCorpo("Parabéns você se cadastrou no SGE com SUCESSO!");
        emailDTO.setDestinatario(email);
        emailDTO.setCopias(new ArrayList<String>());
        emailDTO.getCopias().add(emailDTO.getDestinatario());
        servico.sendMail(emailDTO);

    }
}
