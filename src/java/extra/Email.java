package extra;

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;
import javax.activation.*;

public class Email {
    private String remitente;
    private String destinatario;
    private String asunto;
    private String cuerpo;

    //<editor-fold defaultstate="collapsed" desc="Constructores">
    public Email(String remitente, String destinatario, String asunto, String cuerpo) {
        this.remitente = remitente;
        this.destinatario = destinatario;
        this.asunto = asunto;
        this.cuerpo = cuerpo;
    }

    public Email() {
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters y Setters">
    public String getRemitente() {
        return remitente;
    }

    public void setRemitente(String remitente) {
        this.remitente = remitente;
    }

    public String getDestinatario() {
        return destinatario;
    }

    public void setDestinatario(String destinatario) {
        this.destinatario = destinatario;
    }

    public String getAsunto() {
        return asunto;
    }

    public void setAsunto(String asunto) {
        this.asunto = asunto;
    }

    public String getCuerpo() {
        return cuerpo;
    }

    public void setCuerpo(String cuerpo) {
        this.cuerpo = cuerpo;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Métodos">
    public void enviarCorreo() {
        System.out.println("EM\tSetting properties");
        Properties properties = System.getProperties();
        properties.setProperty("mail.stmp.host", "localhost");
        Session session = Session.getInstance(properties, null);
        
        try {
            System.out.println("EM\tBuilding email");
            MimeMessage message = new MimeMessage(session);

            System.out.println("EM\tWritting email");
            message.setFrom(new InternetAddress(remitente));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(destinatario));
            message.setSubject(asunto);
            message.setContent(cuerpo, "text/html");

            System.out.println("EM\tSending email");
            Transport.send(message);
            System.out.println("EM\tEmail sended");
        } catch (MessagingException ex) {
            System.out.println(ex.getMessage());
        }
    }
    //</editor-fold>

}
