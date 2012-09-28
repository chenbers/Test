package com.inthinc.pro.backing;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;
import org.jasypt.util.password.PasswordEncryptor;

import com.inthinc.pro.dao.UserDAO;
import com.inthinc.pro.model.User;
import com.inthinc.pro.util.MessageUtil;

public class ChangePasswordBean extends BaseBean
{
    private static final Logger logger = Logger.getLogger(ChangePasswordBean.class);

    private String              oldPassword;
    private String              newPassword;
    private String              confirmPassword;
    private boolean             complete;
    private UserDAO             userDAO;
    private PasswordEncryptor   passwordEncryptor;

    public void cancelEdit()
    {
        oldPassword = null;
        newPassword = null;
        confirmPassword = null;
        complete = false;
    }

    public void changePasswordAction()
    {
        complete = false;

        final FacesContext context = FacesContext.getCurrentInstance();

        final String storedPassword = getUser().getPassword();
        if (!passwordEncryptor.checkPassword(oldPassword, storedPassword))
        {
            final FacesMessage message = new FacesMessage();
            message.setSummary(MessageUtil.getMessageString("myAccount_incorrect_password"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("changePasswordForm:oldPassword", message);
            oldPassword = null;
            return;
        }

        // if both input components are valid at this point, meaning that they
        // have passed conversion and validation
        if ((newPassword != null) && (newPassword.length() > 0) && !newPassword.equals(confirmPassword))
        {
            FacesMessage message = new FacesMessage();
            message.setSummary(MessageUtil.getMessageString("myAccount_passwords_dont_match"));
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage("changePasswordForm:confirmPassword", message);
            return;
        }
        // validate password strength handled via f:validator tag reference to PasswordStrengthValidator

        final String newPasswordEncrypt = passwordEncryptor.encryptPassword(newPassword);
        final User user = getUser();
        user.setPassword(newPasswordEncrypt);
        user.setPasswordDT(new Date());
        userDAO.update(user);

        FacesMessage message = new FacesMessage();
        message.setSummary(MessageUtil.getMessageString("myAccount_changedPassword"));
        message.setSeverity(FacesMessage.SEVERITY_INFO);
        context.addMessage(null, message);

        oldPassword = null;
        newPassword = null;
        confirmPassword = null;
        complete = true;
    }

    public boolean isComplete()
    {
        final boolean ret = complete;
        complete = false;
        return ret;
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

    public UserDAO getUserDAO()
    {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO)
    {
        this.userDAO = userDAO;
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
