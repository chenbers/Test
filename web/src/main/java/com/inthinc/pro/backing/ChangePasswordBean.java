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
    
    public void validateNewPassword(FacesContext context, UIComponent component, Object value)
    {
        // if both input components are valid at this point, meaning that they
        // have passed conversion and validation
        if (newPasswordInput.isValid() && confirmPasswordInput.isValid())
        {
            String oldPassword = (String) value;//oldPasswordInput.getLocalValue();
            String newPassword = (String) newPasswordInput.getLocalValue();
            String confirmPassword = (String) confirmPasswordInput.getLocalValue();
            if (newPassword == null) newPassword = (String) newPasswordInput.getValue();
            if (confirmPassword == null) confirmPassword = (String) confirmPasswordInput.getValue();

            if (!newPassword.equals(confirmPassword))
            {
                FacesMessage message = new FacesMessage();
                message.setSummary(messageSource.getMessage("updatecred_passwordsDontMatch", null, FacesContext.getCurrentInstance().getExternalContext().getRequestLocale()));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
            
            User user = getUser();
            String oldPasswordEncrypted = passwordEncryptor.encryptPassword(oldPassword);
            String storedPasswordEncryped = user.getPassword();
            if (!oldPasswordEncrypted.equals(storedPasswordEncryped))
            {
                FacesMessage message = new FacesMessage();
                message.setSummary(messageSource.getMessage("updatecred_passwordsDontMatch", null, FacesContext.getCurrentInstance().getExternalContext().getRequestLocale()));
                message.setSeverity(FacesMessage.SEVERITY_ERROR);
                throw new ValidatorException(message);
            }
        }
    }

    public String changePasswordAction()
    {
        User user = getUser();
        String newPasword = (String) newPasswordInput.getValue();
        String newPasswordEncrypt = passwordEncryptor.encryptPassword(newPasword);
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
