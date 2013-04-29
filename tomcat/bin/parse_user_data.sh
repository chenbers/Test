#!/bin/bash

############################################################################################
#
# Start of parse_user_data.sh
#
############################################################################################
# VARIABLES
USER_DATA_FILE="/etc/user-data.sh"
LATEST_URL="http://169.254.169.254/latest/user-data"
TMP_FILE="/tmp/latest-data-`date +%F-%s`-tmp"
MY_GROUP="inthinc"
PRIVATE_IP=$(hostname -I)
#PUBLIC_IP=$(curl http://169.254.169.254/latest/meta-data/public-ipv4)
GROUP_EXISTS=$(grep ${MY_GROUP} /etc/group 2>/dev/null)
if [ "${GROUP_EXISTS}" ]
then
    echo "Group ${MY_GROUP} already exists"
else
    echo "Group ${MY_GROUP} does not exist, creating"
    groupadd ${MY_GROUP}
fi
#${U_HOME}/parse_user_data.sh
if [ -s "${USER_DATA_FILE}" ]
 then
    HAVE_404=$(grep '404 - Not Found' ${USER_DATA_FILE})
    if [ "${HAVE_404}" ]
    then
        echo "User data has a 404, ignoring"
        exit 0
    else
	echo "User data exists, does not appear to have a 404, sourcing"
	source ${USER_DATA_FILE}
    fi
 else
	echo "User data does not exist, creating"
	touch ${USER_DATA_FILE}
	chown root:${MY_GROUP} ${USER_DATA_FILE}
	curl http://169.254.169.254/latest/user-data/ | tee ${USER_DATA_FILE}
	chown root:${MY_GROUP} ${USER_DATA_FILE}
	perl -pi -e 's/^([A-z0-9_]*) (.*$)/declare -x ${1}="${2}"/' ${USER_DATA_FILE}
	chmod ug+rx,o-rwx ${USER_DATA_FILE}
	source ${USER_DATA_FILE}
fi

############################################################################################
#
# End of parse_user_data.sh
#
############################################################################################
