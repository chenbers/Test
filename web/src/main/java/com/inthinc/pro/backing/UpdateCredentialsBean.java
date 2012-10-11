package com.inthinc.pro.backing;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.jasypt.exceptions.EncryptionOperationNotPossibleException;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.MessageSource;

import com.inthinc.pro.dao.AccountDAO;
import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.dao.util.NumberUtil;
import com.inthinc.pro.model.Account;
import com.inthinc.pro.model.User;
import com.inthinc.pro.model.configurator.TiwiproSpeedingConstants;

public class UpdateCredentialsBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(UpdateCredentialsBean.class);
    private static final SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddDDDFFWww");

    private Integer userID= null;
    private String username = null;
    private String newPassword;
    private String confirmPassword;
    private String encryptPassword;
    private String passwordStrengthString;
    private Integer minPasswordStrength = 0;
    private Integer passwordStrength;
    private int daysValid;
    private MessageSource messageSource;
    private UserDAO userDAO;
    private AccountDAO accountDAO;

    private UIInput newPasswordInput;
    private UIInput confirmPasswordInput;

    public UpdateCredentialsBean()
    {
        super();
    }
    public void setPassKey(String passkey)
    {
        if (username == null)
        {
            StandardPBEStringEncryptor textEncryptor;
            Calendar cal = Calendar.getInstance();

            for (int i = 0; i > daysValid * -1; i--)
            {
                cal.add(Calendar.DATE, i);
                textEncryptor = new StandardPBEStringEncryptor();
                String dateString = formatter.format(cal.getTime());
                textEncryptor.setPassword(encryptPassword + dateString);
                textEncryptor.setStringOutputType("hexadecimal");
                textEncryptor.initialize();
                try
                {
                    logger.debug("Encrypted passkey: " + passkey);
                    username = textEncryptor.decrypt(passkey);
                    logger.debug("Decrypted passkey: " + username);
                    break;
                }
                catch (EncryptionOperationNotPossibleException e)
                {
                    logger.debug("Exception occured during attempt to decrypt a passkey: ", e);
                }
            }
            if (username == null)
            {
                ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
                try
                {
                    externalContext.redirect(externalContext.getRequestContextPath());
                }
                catch (IOException ie)
                {
                    throw new FacesException(ie);
                }
            }
        }
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public void setEncryptPassword(String encryptPassword)
    {
        this.encryptPassword = encryptPassword;
    }

    public void setDaysValid(int daysValid)
    {
        this.daysValid = daysValid;
    }

    public UIInput getNewPasswordInput()
    {
        return newPasswordInput;
    }

    public void setNewPasswordInput(UIInput newPasswordInput)
    {
        this.newPasswordInput = newPasswordInput;
    }

    public UIInput getConfirmPasswordInput()
    {
        return confirmPasswordInput;
    }

    public void setConfirmPasswordInput(UIInput confirmPasswordInput)
    {
        this.confirmPasswordInput = confirmPasswordInput;
    }

    public String getNewPassword()
    {
        return newPassword;
    }

    public void setNewPassword(String newPassword)
    {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword()
    {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword)
    {
        this.confirmPassword = confirmPassword;
    }

    public MessageSource getMessageSource()
    {
        return messageSource;
    }

    public void setMessageSource(MessageSource value)
    {
        this.messageSource = value;
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public void validateNewPassword(FacesContext context, UIComponent component, Object value)
    {
        // if both input components are valid at this point, meaning that they
        // have passed conversion and validation
        if (newPasswordInput.isValid() && confirmPasswordInput.isValid())
        {            
            String password = (String) newPasswordInput.getLocalValue();
            String confirmPassword = (String) value;

            if (!password.equals(confirmPassword))
            {
                FacesMessage message = new FacesMessage();
                message.setSummary(messageSource.getMessage("updatecred_passwordsDontMatch", null, FacesContext.getCurrentInstance().getExternalContext().getRequestLocale()));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public void changePasswordAction()
    {
        // encrypt new password
        String newPassword = new StrongPasswordEncryptor().encryptPassword((String) newPasswordInput.getValue());
        User user = userDAO.findByUserName(username);
        user.setPassword(newPassword);
        user.setPasswordDT(new Date());
        userDAO.update(user);
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        session.invalidate();
    }

    public void setMinPasswordStrength(Integer minPasswordStrength) {
        this.minPasswordStrength = minPasswordStrength;
    }

    private Integer findMinPasswordStrength() {
        User user ;
        try {
            user = getUser();
        } catch (ClassCastException e) {
            user = null;
        }
        
        if(user == null) {
            if(username != null && !username.equals("")) {
                user = userDAO.findByUserName(username);
            } else if(userID != null) {
                user = userDAO.findByID(userID);
            } else {
                return 0;
            }
        }
        Account account = accountDAO.findByID(user.getPerson().getAccountID());
        return NumberUtil.convertString(account.getProps().getPasswordStrength());
    }
    public Integer getMinPasswordStrength() {
        if(minPasswordStrength == null || minPasswordStrength == 0){
           minPasswordStrength = findMinPasswordStrength();
        }
        return minPasswordStrength;
    }

    public String getPasswordStrengthString() {
        return passwordStrengthString;
    }

    public void setPasswordStrengthString(String passwordStrengthString) {
        this.passwordStrengthString = passwordStrengthString;
    }

    public Integer getPasswordStrength() {
        return passwordStrength;
    }

    public void setPasswordStrength(Integer passwordStrength) {
        this.passwordStrength = passwordStrength;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public AccountDAO getAccountDAO() {
        return accountDAO;
    }

    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }
}
