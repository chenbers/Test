package com.inthinc.pro.backing;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.springframework.context.MessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.inthinc.pro.dao.PersonDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.Person;
import com.inthinc.pro.model.User;
import com.inthinc.pro.validators.EmailValidator;

public class RetrieveCredentialsBean extends BaseBean
{
    private static final Logger           logger    = Logger.getLogger(RetrieveCredentialsBean.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddDDDFFWww");

    private String                        from;

    private MailSender                    mailSender;
    private MessageSource                 messageSource;
    private String                        encryptPassword;
    private String                        email;
    private UIInput                       emailInput;
    private User                          validUser;
    private UserDAO                       userDAO;
    private PersonDAO                       personDAO;

    private boolean                       success   = false;

    public boolean isSuccess()
    {
        return success;
    }

    public void setSuccess(boolean success)
    {
        this.success = success;
    }

    public RetrieveCredentialsBean()
    {
        super();
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public void setMailSender(MailSender mailSender)
    {
        this.mailSender = mailSender;
    }

    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public void setEncryptPassword(String encryptPassword)
    {
        this.encryptPassword = encryptPassword;
    }

    public void setValidUser(User validUser)
    {
        this.validUser = validUser;
    }

    public UIInput getEmailInput()
    {
        return emailInput;
    }

    public void setEmailInput(UIInput emailInput)
    {
        this.emailInput = emailInput;
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    /**
     * Fail only if the email is invalid. Don't complain if the email doesn't exist.
     * 
     * @param context
     * @param component
     * @param value
     */
    public void validateEmail(FacesContext context, UIComponent component, Object value)
    {
        if (emailInput.isValid())
        {
            new EmailValidator().validate(context, component, value);
            String emailStr = value.toString();
            Person person = personDAO.findByEmail(emailStr);
            if (person != null)
            {
                validUser = person.getUser();
            }
            
            if (validUser == null)
            {
                FacesMessage error = new FacesMessage();
                String text = messageSource.getMessage("credentials_invalid_email", null, FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
                error.setSummary(text);
                error.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(error);
            }
        }
    }

    public String sendCredentialsAction()
    {
        if (validUser != null && mailSender instanceof JavaMailSender)
        {
            success = true;
            JavaMailSender javaMailSender = (JavaMailSender) mailSender;
            
            javaMailSender.send(

            new MimeMessagePreparator()
            {

                public void prepare(MimeMessage mimeMessage) throws MessagingException
                {
                    MimeMessageHelper msgH = new MimeMessageHelper(mimeMessage, true, "UTF-8");

                    StandardPBEStringEncryptor textEncryptor = new StandardPBEStringEncryptor();
                    Calendar cal = Calendar.getInstance();
                    synchronized (cal)
                    {
                        String dateString = formatter.format(cal.getTime());
                        textEncryptor.setPassword(encryptPassword + dateString);
                        textEncryptor.setStringOutputType("hexadecimal");
                        textEncryptor.initialize();
                    }
                    String eUsername = textEncryptor.encrypt(validUser.getUsername());

                    HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    String url = request.getRequestURL().substring(0, request.getRequestURL().lastIndexOf("/") + 1);
                    url = url + "reset/" + eUsername;
                    String htmlUrl = "<html><a href=\"" + url + "\">" + url + "</a></html>";
                    String subject = messageSource.getMessage("credentials_mail_subject", null, FacesContext.getCurrentInstance().getExternalContext().getRequestLocale());
                    String text = messageSource.getMessage("credentials_mail_text", new Object[] { htmlUrl }, FacesContext.getCurrentInstance().getExternalContext()
                            .getRequestLocale());

                    msgH.setFrom(from);
                    msgH.setTo(email);
                    msgH.setSubject(subject);
                    msgH.setText(text, true);
                    // msgH.setText("my text <img src='cid:myLogo'>", true);
                    // msgH.addInline("myLogo", new ClassPathResource("img/mylogo.gif"));
                    // msgH.addAttachment("myDocument.pdf", new ClassPathResource("doc/myDocument.pdf"));
                }// prepare()

            }// MimeMessagePreparator()

                    ); // send

        }// if
        
        return "pretty:sentResetEmail";

    }

    public PersonDAO getPersonDAO()
    {
        return personDAO;
    }

    public void setPersonDAO(PersonDAO personDAO)
    {
        this.personDAO = personDAO;
    }
}
