package no.avexis.dropwizard.mail;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Preconditions;
import io.dropwizard.setup.Environment;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.config.TransportStrategy;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

public class MailerFactory {
    @JsonProperty
    private String host;
    @JsonProperty
    private Integer port;
    @JsonProperty
    private String username;
    @JsonProperty
    private String password;
    @JsonProperty
    private TransportStrategy transportStrategy;
    @JsonProperty
    private String fromName;
    @JsonProperty
    private String fromAddress;

    public MailerFactory() {
        this.host = "localhost";
        this.port = 25;
        this.transportStrategy = TransportStrategy.SMTP_PLAIN;
    }

    public Mailer build(final Environment environment, final String name) {
        final Mailer mailer = new Mailer(host, port, username, password, transportStrategy);
        environment.healthChecks().register(name, new MailerHealthCheck(mailer));
        return mailer;
    }

    public SSLMailerClient buildSSLClient() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.socketFactory.port", "465"); //SSL Port
        props.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory"); //SSL Factory Class
        props.put("mail.smtp.auth", "true"); //Enabling SMTP Authentication
        props.put("mail.smtp.port", "465"); //SMTP Port

        Authenticator auth = new Authenticator() {
            //override the getPasswordAuthentication method
            protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromAddress, password);
            }
        };

        Session session = Session.getDefaultInstance(props, auth);

        return new SSLMailerClient(session, fromName, fromName);
    }

    public SimpleMailerClient buildSimpleClient(final Environment environment, final String name) {
        Preconditions.checkNotNull(fromName);
        Preconditions.checkNotNull(fromAddress);
        final Mailer mailer = build(environment, name);
        return new SimpleMailerClient(mailer, fromName, fromAddress);
    }

}
