package no.avexis.dropwizard.mail

import com.fasterxml.jackson.annotation.JsonProperty
import com.google.common.base.Preconditions
import io.dropwizard.setup.Environment
import org.simplejavamail.email.Email
import org.simplejavamail.email.EmailBuilder
import org.simplejavamail.mailer.Mailer
import org.simplejavamail.mailer.config.TransportStrategy
import java.net.Authenticator
import java.net.PasswordAuthentication
import java.util.*
import javax.mail.Session


class SSLMailerClient(val session: Session, private val fromName: String, private val fromAddress: String) {
    fun send() {

    }
}
