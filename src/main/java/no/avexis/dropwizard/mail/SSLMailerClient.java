package no.avexis.dropwizard.mail;

import org.simplejavamail.email.EmailBuilder;

import javax.mail.Message;
import javax.mail.Session;

public class SSLMailerClient {
    Session session;

    SSLMailerClient(Session session) {
        this.session = session;

    }

    public void send(final String to, String subject, String text, boolean html) {
        EmailUtils.sendEmail(session, to, subject, text);
    }

}
