package com.basis.sge.service.servico;

import com.basis.sge.service.servico.dto.EmailDTO;
import com.basis.sge.service.servico.exception.RegraNegocioException;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.messaging.MessagingException;
import org.springframework.stereotype.Service;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

@Service
@RequiredArgsConstructor
public class EmailServico {

    private final JavaMailSender javaMailSender;

    public void sendMail(EmailDTO emailDTO) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper message = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            message.setTo(emailDTO.getDestinatario());
            message.setFrom("sge.basis@gmail.com", "SGE");
            message.setSubject(emailDTO.getAssunto());
            for (String s : emailDTO.getCopias()) {
                message.addCc(s);
            }
            message.setText(emailDTO.getCorpo(), true);
            javaMailSender.send(mimeMessage);

        } catch (MessagingException | UnsupportedEncodingException | javax.mail.MessagingException e) {
            throw new RegraNegocioException( "error.title");
        }
    }
}

