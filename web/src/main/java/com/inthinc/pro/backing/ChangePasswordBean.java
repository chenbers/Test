package com.inthinc.pro.backing;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;

import org.apache.log4j.Logger;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.context.MessageSource;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;

public class ChangePasswordBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(ChangePasswordBean.class);

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
    private MessageSource messageSource;
    private UserDAO userDAO;
    private PasswordEncryptor passwordEncryptor;

    private UIInput oldPasswordInput;
    private UIInput newPasswordInput;
    private UIInput confirmPasswordInput;

    public ChangePasswordBean()
    {
        super();
    }
    
    public void validateMatchingPasswords(FacesContext context, UIComponent component, Object value)
    {
        // if both input components are valid at this point, meaning that they
        // have passed conversion and validation
        if (newPasswordInput.isValid() && confirmPasswordInput.isValid())
        {
            String newPassword = (String) newPasswordInput.getSubmittedValue();
            String confirmPassword = (String) confirmPasswordInput.getSubmittedValue();
            if (!newPassword.equals(confirmPassword))
            {
                FacesMessage message = new FacesMessage();
                message.setSummary(messageSource.getMessage("myAccount_passwords_dont_match", null, FacesContext.getCurrentInstance().getExternalContext().getRequestLocale()));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
        
    }
    
    public void validateNewPassword(FacesContext context, UIComponent component, Object value)
    {
        // if both input components are valid at this point, meaning that they
        // have passed conversion and validation
        String submittedPassword = (String) oldPasswordInput.getSubmittedValue();
            
        User user = getUser();
       // StrongPasswordEncryptor passwordEncryptor2 = new StrongPasswordEncryptor();
       // String oldPasswordEncrypted = passwordEncryptor2.encryptPassword(submittedPassword);
        String storedPassword = user.getPassword();
        if (!passwordEncryptor.checkPassword(submittedPassword, storedPassword))
        {
            FacesMessage message = new FacesMessage();
            message.setSummary(messageSource.getMessage("myAccount_incorrect_password", null, FacesContext.getCurrentInstance().getExternalContext().getRequestLocale()));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    public String changePasswordAction()
    {
        String newPasswordEncrypt = passwordEncryptor.encryptPassword((String) newPasswordInput.getValue());
        User user = getUser();
        user.setPassword(newPasswordEncrypt);
        userDAO.update(user);
        return "go_myAccount";
    }

    public String getOldPassword()
    {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword)
    {
        this.oldPassword = oldPassword;
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

    public void setMessageSource(MessageSource messageSource)
    {
        this.messageSource = messageSource;
    }

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
    }

    public UIInput getOldPasswordInput()
    {
        return oldPasswordInput;
    }

    public void setOldPasswordInput(UIInput oldPasswordInput)
    {
        this.oldPasswordInput = oldPasswordInput;
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

    public PasswordEncryptor getPasswordEncryptor()
    {
        return passwordEncryptor;
    }

    public void setPasswordEncryptor(PasswordEncryptor passwordEncryptor)
    {
        this.passwordEncryptor = passwordEncryptor;
    }
}
