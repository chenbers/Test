#!/bin/bash
U_USER="tomcat2"
U_GROUP="tiwipro"
U_USER_HOME="/usr/local/tomcat2"
chown -R ${U_USER}:${U_GROUP} ${U_USER_HOME}
chown -R ${U_USER}:${U_GROUP} ${U_USER_HOME}/
CONFIG_FILES="${U_USER_HOME}/conf/tiwipro.properties ${U_USER_HOME}/conf/cas/cas.properties ${U_USER_HOME}/conf/cas/database.properties ${U_USER_HOME}/bin/monitor.sh /etc/nginx/sites-available/tiwipro /etc/ssl/private/inthinc-private-key-2013.pem /etc/ssl/certs/inthinc.com.crt /etc/tiwipro/nginx-nossl/roadhazard.conf /etc/tiwipro/nginx-ssl/hoskiosk.conf /etc/tiwipro/nginx-ssl/cas.conf /etc/tiwipro/nginx-ssl/service.conf /etc/tiwipro/nginx-ssl/tiwipro.conf /etc/tiwipro/nginx-ssl/tiwiproutil.conf /var/www/index.html /var/www/404.html"

if [ -f "/etc/user-data.sh" ]
then
    source /etc/user-data.sh
else
    if [ -x "${U_USER_HOME}/bin/parse_user_data.sh" ]
    then
        ${U_USER_HOME}/bin/parse_user_data.sh
        chmod ug+x /etc/user-data.sh
        source /etc/user-data.sh
    else
        echo "Skipping setup, no user-data.sh and no parse_user_data.sh"
        exit 1
    fi
fi

if [ -x "${U_USER_HOME}/bin/setup_config_file.sh" ] && [ -x "/etc/user-data.sh" ]
then
    for CONFIG_FILE in ${CONFIG_FILES}
    do
        if [ -f "${CONFIG_FILE}" ]
        then
            echo "Parsing ${CONFIG_FILE}"
            ${U_USER_HOME}/bin/setup_config_file.sh ${CONFIG_FILE}
        else
            echo "Missing ${CONFIG_FILE}"
        fi
    done
else
    echo "Missing setup_config_file.sh or /etc/user-data.sh, exiting at $(date)"
    exit 1
fi

echo "User home ${U_USER_HOME}"
if [ -f "${U_USER_HOME}/cron" ]
 then
	crontab -u ${U_USER} ${U_USER_HOME}/cron
fi
if [ -d /var/www/ ]
then
    touch /var/www/web.html
    chown ${U_USER}:${U_GROUP} /var/www/web.html
fi

