#!/bin/bash
USER_DATA_FILE="/etc/user-data.sh"
CHECKAPP_FILE="/etc/tiwipro/monitor/checkapp.txt"
#DEBUG=1

MY_VARS="AMI FS_BASE COMMANDS DISK_DEVICE EC2_RUN_OPTS F_INSTALL_PACKAGES GOOGLE_ID GROUP HOST_NAME INSTANCE_TYPE J_INSTALL_PHASE2_PACKAGES K_SSH_CMD LOCATION MY_ELB_ADDRESS MY_FORMS_PASSWORD MY_FORMS_USERNAME MY_GIS_ADDRESS MY_GIS_PASSWORD MY_GIS_USER MY_MAPSERVER_ADDRESS MY_MAPSERVER_PORT MY_POSTGRES_SERVER MYSQL_ROOT_PASSWORD MY_TEEN_CENT_PORT MY_TEEN_SILO_PORT MY_VOXEO_ADDRESS NS_SERVERS O_VOXEO_TOKEN POSTGRES_PASSWORD POSTGRES_USER PRIV_KEY_NAME Q_GOOGLE_ANALYTICS_KEY REGION SILO_NAME TIWIPRO_CLIENT_CENT_SERVER TIWIPRO_CLIENT_DB_SERVER TIWIPRO_SERVER_ID USER_HOME U_USER VOLUME_SIZE VOXEO_TOKEN WEB_DB_PASSWORD WEB_DB_SERVER WEB_DB_USER Y_SILO_ID AWS_ZONE CHECK_USER_PASSWORD CHECK_USER"

function create_default_user_data {
    MY_INPUT_FILE="${1}"
    get_checkapp_info
    setup_defaults
    check_values
    if [ "${DEBUG}" ]; then debug_print; fi
    for VAR in ${MY_VARS}
    do
        echo "declare -x ${VAR}=\"${VAR}\"" >> ${USER_DATA_FILE}
    done
    parse_file ${USER_DATA_FILE}
}

function get_checkapp_info {
    if [ -f "${CHECKAPP_FILE}" ] && [ "${SILO_NAME}" ]
    then
        echo "Reading check app users from ${CHECKAPP_FILE}"
        CHECK_USER=$(grep ${SILO_NAME} ${CHECKAPP_FILE} | head -n 1 | awk '{ print $2 }')
        CHECK_USER_PASSWORD=$(grep ${SILO_NAME} ${CHECKAPP_FILE} | head -n 1 | awk '{ print $3 }')
        if [ ! "${CHECK_USER}" ] || [ ! "${CHECK_USER_PASSWORD}" ]
        then
            echo "Failed to get CHECK_USER or CHECK_USER_PASSWORD from ${CHECKAPP_FILE}"
            echo "DEBUG : Have user ${CHECK_USER} and pass ${CHECK_USER_PASSWORD}"
        fi
    else
        echo "Missing ${CHECKAPP_FILE} or SILO_NAME"
    fi
}

############################################################################################
#
# Start of default_vars_check.sh
#
############################################################################################
function setup_defaults {
    if [ ! "${AMI}" ]; then AMI="ami-de0d9eb7"; fi
    if [ ! "${FS_BASE}" ]; then FS_BASE=/usr/local/inthinc; fi
    if [ ! "${COMMANDS}" ]; then COMMANDS="ec2-allocate-address date ec2-run-instances ec2-associate-address host ec2-create-volume /usr/bin/ssh touch chmod /bin/rm echo ec2-create-tags ec2-attach-volume ec2-describe-volumes pwgen"; fi
    if [ ! "${SD_DISK_DEVICE}" ]; then SD_DISK_DEVICE="/dev/sdi1"; fi
    if [ ! "${EC2_RUN_OPTS}" ]; then EC2_RUN_OPTS="-m --disable-api-termination --instance-initiated-shutdown-behavior stop"; fi
    if [ ! "${F_INSTALL_PACKAGES}" ]; then F_INSTALL_PACKAGES="zabbixAgent"; fi
    if [ ! "${GOOGLE_ID}" ]; then GOOGLE_ID="UA-2634129-14"; fi
    if [ ! "${GROUP}" ]; then GROUP="default"; fi
    if [ ! "${HOST_NAME}" ]; then HOST_NAME="ubuntu-blank"; fi
    if [ ! "${INSTANCE_TYPE}" ]; then INSTANCE_TYPE="m1.large"; fi
    if [ ! "${J_INSTALL_PHASE2_PACKAGES}" ]; then J_INSTALL_PHASE2_PACKAGES="NONE"; fi
    if [ ! "${K_SSH_CMD}" ]; then K_SSH_CMD="/usr/bin/ssh -o StrictHostKeyChecking=no"; fi
    if [ ! "${LOCATION}" ]; then LOCATION="QA"; fi
    if [ ! "${MY_FORMS_PASSWORD}" ]; then MY_FORMS_PASSWORD="jhoward"; fi
    if [ ! "${MY_FORMS_USERNAME}" ]; then MY_FORMS_USERNAME="password"; fi
    if [ ! "${MY_GIS_ADDRESS}" ]; then MY_GIS_ADDRESS="tp-mapserv0"; fi
    if [ ! "${MY_GIS_PASSWORD}" ]; then MY_GIS_PASSWORD="du0Oifo5aeth9ei"; fi
    if [ ! "${MY_GIS_USER}" ]; then MY_GIS_USER="proprod"; fi
    if [ ! "${MY_MAPSERVER_ADDRESS}" ]; then MY_MAPSERVER_ADDRESS="67.208.132.73"; fi
    if [ ! "${MY_MAPSERVER_PORT}" ]; then MY_MAPSERVER_PORT="8081"; fi
    if [ ! "${MY_POSTGRES_SERVER}" ]; then MY_POSTGRES_SERVER="tp-mapserv0"; fi
    if [ ! "${MYSQL_ROOT_PASSWORD}" ]; then MYSQL_ROOT_PASSWORD="asdfasdfasdf"; fi
    if [ ! "${MY_TEEN_CENT_PORT}" ]; then MY_TEEN_CENT_PORT="8095"; fi
    if [ ! "${MY_TEEN_SILO_PORT}" ]; then MY_TEEN_SILO_PORT="8099"; fi
    if [ ! "${MY_VOXEO_ADDRESS}" ]; then MY_VOXEO_ADDRESS="webhosting.voxeo.net"; fi
    if [ ! "${NS_SERVERS}" ]; then NS_SERVERS="ns1.tiwipro.com ns2.tiwipro.com ns3.tiwipro.com ns4.tiwipro.com"; fi
    if [ ! "${O_VOXEO_TOKEN}" ]; then O_VOXEO_TOKEN="0f360880c6d90348b6043f3473ef70027097b9d3d82dcbe69bf99b3d231861e0dcaf8aa5e4de1b22ef723700"; fi
    if [ ! "${POSTGRES_PASSWORD}" ]; then POSTGRES_PASSWORD="du0Oifo5aeth9ei"; fi
    if [ ! "${POSTGRES_USER}" ]; then POSTGRES_USER="proprod"; fi
    if [ ! "${PRIV_KEY_NAME}" ]; then PRIV_KEY_NAME="jzkeys"; fi
    if [ ! "${Q_GOOGLE_ANALYTICS_KEY}" ]; then Q_GOOGLE_ANALYTICS_KEY="UA-2634129-14"; fi
    if [ ! "${REGION}" ]; then REGION="us-east-1"; fi
    if [ ! "${SILO_NAME}" ]; then SILO_NAME="tp"; fi
    if [ ! "${MY_ELB_ADDRESS}" ]; then MY_ELB_ADDRESS="${SILO_NAME}.inthinc.com"; fi
    if [ ! "${TIWIPRO_CLIENT_CENT_SERVER}" ]; then TIWIPRO_CLIENT_CENT_SERVER="67.208.132.73"; fi
    if [ ! "${TIWIPRO_CLIENT_DB_SERVER}" ]; then TIWIPRO_CLIENT_DB_SERVER="${SILO_NAME}-dbnode0.tiwipro.com"; fi
    if [ ! "${TIWIPRO_SERVER_ID}" ]; then TIWIPRO_SERVER_ID="500"; fi
    if [ ! "${USER_HOME}" ]; then USER_HOME="/usr/local/tiwipro"; fi
    if [ ! "${U_USER}" ]; then U_USER="tiwipro"; fi
    if [ ! "${VOLUME_SIZE}" ]; then VOLUME_SIZE="30"; fi
    if [ ! "${VOXEO_TOKEN}" ]; then VOXEO_TOKEN="${O_VOXEO_TOKEN}"; fi
    if [ ! "${WEB_DB_PASSWORD}" ]; then WEB_DB_PASSWORD="portalPass"; fi
    if [ ! "${WEB_DB_SERVER}" ]; then WEB_DB_SERVER="${TIWIPRO_CLIENT_DB_SERVER}"; fi
    if [ ! "${WEB_DB_USER}" ]; then WEB_DB_USER="portalUser"; fi
    if [ ! "${Y_SILO_ID}" ]; then Y_SILO_ID="0"; fi
    if [ ! "${AWS_ZONE}" ]; then AWS_ZONE="us-east-1b"; fi
    if [ ! "${CHECK_USER_PASSWORD}" ]; then CHECK_USER_PASSWORD="ohs8aiTh5Ug0"; fi
    if [ ! "${CHECK_USER}" ]; then CHECK_USER="checkapp"; fi
}
############################################################################################
#
# End of default_vars_check.sh
#
############################################################################################

function debug_print {
    echo "AMI is $AMI"
    echo "FS_BASE is $FS_BASE"
    echo "COMMANDS is $COMMANDS"
    echo "SD_DISK_DEVICE is $SD_DISK_DEVICE"
    echo "EC2_RUN_OPTS is $EC2_RUN_OPTS"
    echo "F_INSTALL_PACKAGES is $F_INSTALL_PACKAGES"
    echo "GOOGLE_ID is $GOOGLE_ID"
    echo "GROUP is $GROUP"
    echo "HOST_NAME is $HOST_NAME"
    echo "INSTANCE_TYPE is $INSTANCE_TYPE"
    echo "J_INSTALL_PHASE2_PACKAGES is $J_INSTALL_PHASE2_PACKAGES"
    echo "K_SSH_CMD is $K_SSH_CMD"
    echo "LOCATION is $LOCATION"
    echo "MY_FORMS_PASSWORD is $MY_FORMS_PASSWORD"
    echo "MY_FORMS_USERNAME is $MY_FORMS_USERNAME"
    echo "MY_GIS_ADDRESS is $MY_GIS_ADDRESS"
    echo "MY_GIS_PASSWORD is $MY_GIS_PASSWORD"
    echo "MY_GIS_USER is $MY_GIS_USER"
    echo "MY_MAPSERVER_ADDRESS is $MY_MAPSERVER_ADDRESS"
    echo "MY_MAPSERVER_PORTis $MY_MAPSERVER_PORT"
    echo "MY_POSTGRES_SERVER is $MY_POSTGRES_SERVER"
    echo "MYSQL_ROOT_PASSWORD is $MYSQL_ROOT_PASSWORD"
    echo "MY_TEEN_CENT_PORT is $MY_TEEN_CENT_PORT"
    echo "MY_TEEN_SILO_PORT is $MY_TEEN_SILO_PORT"
    echo "MY_VOXEO_ADDRESS is $MY_VOXEO_ADDRESS"
    echo "NS_SERVERS is $NS_SERVERS"
    echo "O_VOXEO_TOKEN is $O_VOXEO_TOKEN"
    echo "POSTGRES_PASSWORD is $POSTGRES_PASSWORD"
    echo "POSTGRES_USER is $POSTGRES_USER"
    echo "PRIV_KEY_NAME is $PRIV_KEY_NAME"
    echo "Q_GOOGLE_ANALYTICS_KEY is $Q_GOOGLE_ANALYTICS_KEY"
    echo "REGION is $REGION"
    echo "SILO_NAME is $SILO_NAME"
    echo "MY_ELB_ADDRESS is $MY_ELB_ADDRESS"
    echo "TIWIPRO_CLIENT_CENT_SERVER is $TIWIPRO_CLIENT_CENT_SERVER"
    echo "TIWIPRO_CLIENT_DB_SERVER is $TIWIPRO_CLIENT_DB_SERVER"
    echo "TIWIPRO_SERVER_ID is $TIWIPRO_SERVER_ID"
    echo "USER_HOME is $USER_HOME"
    echo "U_USER is $U_USER"
    echo "VOLUME_SIZE is $VOLUME_SIZE"
    echo "VOXEO_TOKEN is $VOXEO_TOKEN"
    echo "WEB_DB_PASSWORD is $WEB_DB_PASSWORD"
    echo "WEB_DB_SERVER is $WEB_DB_SERVER"
    echo "WEB_DB_USER is $WEB_DB_USER"
    echo "Y_SILO_ID is $Y_SILO_ID"
    echo "AWS_ZONE is $AWS_ZONE"
    echo "CHECK_USER_PASSWORD is $CHECK_USER_PASSWORD"
    echo "CHECK_USER is $CHECK_USER"
}

function check_values {
if [  "${AMI}" ] && [  "${FS_BASE}" ] && [  "${COMMANDS}" ] && [  "${SD_DISK_DEVICE}" ] && [  "${EC2_RUN_OPTS}" ] && [  "${F_INSTALL_PACKAGES}" ] && [  "${GOOGLE_ID}" ] && [  "${GROUP}" ] && [  "${HOST_NAME}" ] && [  "${INSTANCE_TYPE}" ] && [  "${J_INSTALL_PHASE2_PACKAGES}" ] && [  "${K_SSH_CMD}" ] && [  "${LOCATION}" ] && [  "${MY_FORMS_PASSWORD}" ] && [  "${MY_FORMS_USERNAME}" ] && [  "${MY_GIS_ADDRESS}" ] && [  "${MY_GIS_PASSWORD}" ] && [  "${MY_GIS_USER}" ] && [  "${MY_MAPSERVER_ADDRESS}" ] && [  "${MY_MAPSERVER_PORT}" ] && [  "${MY_POSTGRES_SERVER}" ] && [  "${MYSQL_ROOT_PASSWORD}" ] && [  "${MY_TEEN_CENT_PORT}" ] && [  "${MY_TEEN_SILO_PORT}" ] && [  "${MY_VOXEO_ADDRESS}" ] && [  "${NS_SERVERS}" ] && [  "${O_VOXEO_TOKEN}" ] && [  "${POSTGRES_PASSWORD}" ] && [  "${POSTGRES_USER}" ] && [  "${PRIV_KEY_NAME}" ] && [  "${Q_GOOGLE_ANALYTICS_KEY}" ] && [  "${REGION}" ] && [  "${SILO_NAME}" ] && [  "${MY_ELB_ADDRESS}" ] && [  "${TIWIPRO_CLIENT_CENT_SERVER}" ] && [  "${TIWIPRO_CLIENT_DB_SERVER}" ] && [  "${TIWIPRO_SERVER_ID}" ] && [  "${USER_HOME}" ] && [  "${U_USER}" ] && [  "${VOLUME_SIZE}" ] && [  "${VOXEO_TOKEN}" ] && [  "${WEB_DB_PASSWORD}" ] && [  "${WEB_DB_SERVER}" ] && [  "${WEB_DB_USER}" ] && [  "${Y_SILO_ID}" ] && [  "${AWS_ZONE}" ] && [ "${CHECK_USER_PASSWORD}" ] && [ "${CHECK_USER}" ]
then
    echo "All variables set"
else
    echo "Missing variables, debug and exit at $(date)"
    if [ "${DEBUG}" ]; then debug_print; fi
    exit 1
fi
}

function parse_file () {
    MY_FILE="${1}"
if [ -f "${MY_FILE}" ]
then
    perl -pi -e "s:AMI:${AMI}:g" ${MY_FILE}
    perl -pi -e "s:FS_BASE:${FS_BASE}:g" ${MY_FILE}
    perl -pi -e "s:COMMANDS:${COMMANDS}:g" ${MY_FILE}
    perl -pi -e "s:SD_DISK_DEVICE:${SD_DISK_DEVICE}:g" ${MY_FILE}
    perl -pi -e "s:EC2_RUN_OPTS:${EC2_RUN_OPTS}:g" ${MY_FILE}
    perl -pi -e "s:F_INSTALL_PACKAGES:${F_INSTALL_PACKAGES}:g" ${MY_FILE}
    perl -pi -e "s:GOOGLE_ID:${GOOGLE_ID}:g" ${MY_FILE}
    perl -pi -e "s:GROUP:${GROUP}:g" ${MY_FILE}
    perl -pi -e "s:HOST_NAME:${HOST_NAME}:g" ${MY_FILE}
    perl -pi -e "s:INSTANCE_TYPE:${INSTANCE_TYPE}:g" ${MY_FILE}
    perl -pi -e "s:J_INSTALL_PHASE2_PACKAGES:${J_INSTALL_PHASE2_PACKAGES}:g" ${MY_FILE}
    perl -pi -e "s:K_SSH_CMD:${K_SSH_CMD}:g" ${MY_FILE}
    perl -pi -e "s:LOCATION:${LOCATION}:g" ${MY_FILE}
    perl -pi -e "s:MY_FORMS_PASSWORD:${MY_FORMS_PASSWORD}:g" ${MY_FILE}
    perl -pi -e "s:MY_FORMS_USERNAME:${MY_FORMS_USERNAME}:g" ${MY_FILE}
    perl -pi -e "s:MY_GIS_ADDRESS:${MY_GIS_ADDRESS}:g" ${MY_FILE}
    perl -pi -e "s:MY_GIS_PASSWORD:${MY_GIS_PASSWORD}:g" ${MY_FILE}
    perl -pi -e "s:MY_GIS_USER:${MY_GIS_USER}:g" ${MY_FILE}
    perl -pi -e "s:MY_MAPSERVER_ADDRESS:${MY_MAPSERVER_ADDRESS}:g" ${MY_FILE}
    perl -pi -e "s:MY_MAPSERVER_PORT:${MY_MAPSERVER_PORT}:g" ${MY_FILE}
    perl -pi -e "s:MY_POSTGRES_SERVER:${MY_POSTGRES_SERVER}:g" ${MY_FILE}
    perl -pi -e "s:MYSQL_ROOT_PASSWORD:${MYSQL_ROOT_PASSWORD}:g" ${MY_FILE}
    perl -pi -e "s:MY_TEEN_CENT_PORT:${MY_TEEN_CENT_PORT}:g" ${MY_FILE}
    perl -pi -e "s:MY_TEEN_SILO_PORT:${MY_TEEN_SILO_PORT}:g" ${MY_FILE}
    perl -pi -e "s:MY_VOXEO_ADDRESS:${MY_VOXEO_ADDRESS}:g" ${MY_FILE}
    perl -pi -e "s:NS_SERVERS:${NS_SERVERS}:g" ${MY_FILE}
    perl -pi -e "s:O_VOXEO_TOKEN:${O_VOXEO_TOKEN}:g" ${MY_FILE}
    perl -pi -e "s:POSTGRES_PASSWORD:${POSTGRES_PASSWORD}:g" ${MY_FILE}
    perl -pi -e "s:POSTGRES_USER:${POSTGRES_USER}:g" ${MY_FILE}
    perl -pi -e "s:PRIV_KEY_NAME:${PRIV_KEY_NAME}:g" ${MY_FILE}
    perl -pi -e "s:Q_GOOGLE_ANALYTICS_KEY:${Q_GOOGLE_ANALYTICS_KEY}:g" ${MY_FILE}
    perl -pi -e "s:REGION:${REGION}:g" ${MY_FILE}
    perl -pi -e "s:SILO_NAME:${SILO_NAME}:g" ${MY_FILE}
    perl -pi -e "s:MY_ELB_ADDRESS:${MY_ELB_ADDRESS}:g" ${MY_FILE}
    perl -pi -e "s:TIWIPRO_CLIENT_CENT_SERVER:${TIWIPRO_CLIENT_CENT_SERVER}:g" ${MY_FILE}
    perl -pi -e "s:TIWIPRO_CLIENT_DB_SERVER:${TIWIPRO_CLIENT_DB_SERVER}:g" ${MY_FILE}
    perl -pi -e "s:TIWIPRO_SERVER_ID:${TIWIPRO_SERVER_ID}:g" ${MY_FILE}
    perl -pi -e "s:USER_HOME:${USER_HOME}:g" ${MY_FILE}
    perl -pi -e "s:U_USER:${U_USER}:g" ${MY_FILE}
    perl -pi -e "s:VOLUME_SIZE:${VOLUME_SIZE}:g" ${MY_FILE}
    perl -pi -e "s:VOXEO_TOKEN:${VOXEO_TOKEN}:g" ${MY_FILE}
    perl -pi -e "s:WEB_DB_PASSWORD:${WEB_DB_PASSWORD}:g" ${MY_FILE}
    perl -pi -e "s:WEB_DB_SERVER:${WEB_DB_SERVER}:g" ${MY_FILE}
    perl -pi -e "s:WEB_DB_USER:${WEB_DB_USER}:g" ${MY_FILE}
    perl -pi -e "s:Y_SILO_ID:${Y_SILO_ID}:g" ${MY_FILE}
    perl -pi -e "s:AWS_ZONE:${AWS_ZONE}:g" ${MY_FILE}
    perl -pi -e "s:CHECK_USER_PASSWORD:${CHECK_USER_PASSWORD}:g" ${MY_FILE}
    perl -pi -e "s:CHECK_USER:${CHECK_USER}:g" ${MY_FILE}
else
    echo "Missing file ${MY_FILE}, exiting at $(date)"
    exit 1
fi
}

if [ ! -f "${USER_DATA_FILE}" ]
then
    echo "Missing ${USER_DATA_FILE}, cannot continue"
    exit 1
fi
# do 404 check
HAVE_404=$(grep '404 - Not Found' ${USER_DATA_FILE})
if [ "${HAVE_404}" ]
then
    echo "User data is a 404, replacing with defaults"
    create_default_user_data
fi

source ${USER_DATA_FILE}

if [ "${1}" ] && [ -f "${1}" ]
then
    MY_INPUT_FILE="${1}"
    get_checkapp_info
    setup_defaults
    check_values
    if [ "${DEBUG}" ]; then debug_print; fi
    parse_file ${MY_INPUT_FILE}
else
    echo "Usage ${0} filename"
    exit 1
fi
