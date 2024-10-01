package devspace.devspaceback.services;

import devspace.devspaceback.email.EmailTemplates;
import devspace.devspaceback.exceptions.SendEmailErrorException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Value("${spring.mail.username}")
    private String serverEmail;
    @Value("${domain.address}")
    private String domain;

    private final JavaMailSender mailSender;

    @Async
    public void sendEmail(String destinationAddress, String title, String body) {

        try {

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(body, true);
            helper.setTo(destinationAddress);
            helper.setSubject(title);
            helper.setFrom(serverEmail);

            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new SendEmailErrorException();
        }

    }

    public void sendVerificationEmail (String email, String name, String token){
        String body = EmailTemplates.verificationTemplate(name, domain + "/confirmation/" + token);
        sendEmail(email, "Verify your registration", body);
    }

//    @Async
//    public void sendEmail (String to, String username, EmailTemplateName emailTemplateName, String confirmationUrl,
//    String activationCode, String Subject) throws MessagingException {
//        String templateName;
//        if (emailTemplateName == null){
//            templateName = "confirm-email";
//        } else{
//            templateName = emailTemplateName.name();
//        }
//
//        MimeMessage mimeMessage = mailSender.createMimeMessage();
//        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, MULTIPART_MODE_MIXED, UTF_8.name());
//
//        Map<String, Object> properties= new HashMap<>();
//        properties.put("username", username);
//        properties.put("confirmationUrl", confirmationUrl);
//        properties.put("activationCode", activationCode);
//    }


}
