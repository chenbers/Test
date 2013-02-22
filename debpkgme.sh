#!/bin/bash

source ${HOME}/.bashrc
if [ -f "${WORKSPACE}/env.bashrc" ]
then
    /bin/rm ${WORKSPACE}/env.bashrc
fi

if [ -z "${TARBALL_FILENAME}" ] || [ ! "${TARBALL_FILENAME}" ]; then TARBALL_FILENAME="tp$(date +%Y%m%d)"; echo "TARBALL_FILENAME not specified, using default ${TARBALL_FILENAME}"; fi
#######################################
#
# FUNCTIONS
#
#######################################

function check_jenkins_variables {
    echo "Enter function check_jenkins_variables"
    REQUIRED_JENKINS_ENV_VARS="BUILD_NUMBER BUILD_ID JOB_NAME BUILD_TAG EXECUTOR_NUMBER NODE_LABELS WORKSPACE JENKINS_HOME JENKINS_URL BUILD_URL JOB_URL"
    OPTIONAL_JENKINS_ENV_VARS="DEB_S3_bucket DEB_repository_dir DEB_Package DEB_Source DEB_Version DEB_Architecture DEB_Maintainer DEB_InstalledSize DEB_PreDepends DEB_Depends DEB_Recommends DEB_Suggests DEB_Conflicts DEB_Replaces DEB_Provides DEB_Section DEB_Priority DEB_Homepage DEB_Description DEB_Conffiles NODE_NAME TOMCAT6_REPO"
    echo -n "Checking Jenkins ENV Variables : "
    for MY_VAR in ${REQUIRED_JENKINS_ENV_VARS}
    do
        eval MY_VARS_VALUE=\$$MY_VAR
        echo -n "${MY_VAR} : "
        if [ "${MY_VARS_VALUE}" ]
        then
            echo "declare -x ${MY_VAR}=${MY_VARS_VALUE}" | tee -a ${WORKSPACE}/env.bashrc
            #echo "DEBUG : ${MY_VAR} exists, is set to ${MY_VARS_VALUE}"
        else
            echo "####### ERROR ######"
            echo "ERROR : Required setting ${MY_VAR} is not set, exiting at $(date)"
            exit 1
        fi
    done
    echo ""
    unset MY_VAR
    for MY_VAR in ${OPTIONAL_JENKINS_ENV_VARS}
    do
        eval MY_VARS_VALUE=\$$MY_VAR
        echo -n "${MY_VAR} : "
        if [ "${MY_VARS_VALUE}" ]
        then
            echo "declare -x ${MY_VAR}=${MY_VARS_VALUE}" | tee -a ${WORKSPACE}/env.bashrc
        else
            echo "${MY_VAR} is not set in env, skipping"
        fi
    done
    echo ""
    perl -pi -e 's/=/="/' ${WORKSPACE}/env.bashrc
    perl -pi -e 's/$/"/' ${WORKSPACE}/env.bashrc
}

function reset_control_in_temp {
    echo "Enter function reset_control_in_temp"
    if [ -d "${TMP_DIR}/control" ]
    then
        echo "Resetting control dir"
        /bin/rm -Rf ${TMP_DIR}/control
        cp -R ${WORKSPACE}/control ${TMP_DIR}
    else
        echo "No ${TMP_DIR}/control to wipe"
    fi
}

function get_tomcat6_blank {
    echo "Enter function get_tomcat6_blank"
    if [ -d "${WORKSPACE}/tomcat6" ]
    then
        echo "Tomcat6 dir exists"
    else
        echo "Cloning Tomcat6 from repo"
        cd ${WORKSPACE}
        git clone --depth=1 ${TOMCAT6_REPO} tomcat6
        rm -Rf tomcat6/.git*
        for D in ${T6_DIRS}
        do
            if [ -d "tomcat6/${D}" ]
            then
                echo "Have ${D}"
            else
                echo "Creating ${D}"
                mkdir -p tomcat6/${D}
            fi
        done
    fi
}

function merge_tomcat6_blank_with_tmp {
    echo "Enter function merge_tomcat6_blank_with_tmp"
    if [ -d "${TMP_DIR}/${TARBALL_FILENAME}" ] && [ -d "${WORKSPACE}/tomcat6" ] && [ ! -d "${WORKSPACE}/tomcat6/webapps" ]
    then
        echo "Merging wars into tomcat6"
        /bin/mv ${TMP_DIR}/${TARBALL_FILENAME} ${WORKSPACE}/tomcat6/webapps
        if [ -d "${WORKSPACE}/tomcat6/webapps" ] && [ ! -d "${TMP_DIR}/${TARBALL_FILENAME}" ] && [ -d "${TMP_DIR}" ]
        then
            /bin/mv ${WORKSPACE}/tomcat6 ${TMP_DIR}/${TARBALL_FILENAME}
        else
            echo "ERROR: Failed to move tomcat6 and ${TMP_DIR}/${TARBALL_FILENAME} or temp dir missing, exiting at $(date)"
            exit 1
        fi
    else
        echo "ERROR: Missing tomcat6 or temp dir, exiting at $(date)"
        exit 1
    fi
}

function setup_tomcat2_variables {
    echo "Enter function setup_tomcat2_variables"
echo "DEBUG : Entered setup_tomcat2_variables at $(date)"
    DEB_Version="${SRC_VERSION}-${BUILD_NUMBER}~${DISTRIB_ID}~${DISTRIB_CODENAME}"
    U_GID="1090"
    MY_GROUP_USER="tiwipro"
    TOMCAT_USER="tomcat2"
    MY_USER_HOME="/usr/local/${TOMCAT_USER}"
    TOMCAT_WEBAPPS_DIR="${MY_USER_HOME}/webapps"
    WARS="hoskiosk tiwiproutil tiwipro service"
    BASE_INSTALL_DIR="${MY_USER_HOME}"
    DEB_Depends="libdbi-perl, perl (>= 5.6), libc6 (>= 2.10), libmysqlclient16 (>= 5.1.21-1), libstdc++6 (>= 4.4), libwrap0 (>= 7.6-4~), zlib1g (>= 1:1.2.0), debconf (>= 0.5) | debconf-2.0, psmisc, passwd, lsb-base (>= 3.0-10), sun-java6-jdk (>= 6.3), memcached (>1.4), nginx-full"
    DEB_Package="inthinc-${JOB_NAME}-tomcat2"
    DEB_Package_u=$(echo -n ${DEB_Package} | sed -e 's/_/-/g')
    DEB_Conflicts="tomcat2, tiwipro-wars"
    DEB_Package_Filename="${WORKSPACE}/${TOMCAT_USER}_${JOB_NAME}_${ARCH_UBU}.deb"
    DEB_Provides="tomcat2, tiwipro-wars"
    echo "Setup tomcat2 variables :"
    echo "U_GID ${U_GID}"
    echo "MY_GROUP_USER ${MY_GROUP_USER}"
    echo "TOMCAT_USER ${TOMCAT_USER}"
    echo "MY_USER_HOME ${MY_USER_HOME}"
    echo "TOMCAT_WEBAPPS_DIR ${TOMCAT_WEBAPPS_DIR}"
    echo "WARS ${WARS}"
    echo "BASE_INSTALL_DIR ${BASE_INSTALL_DIR}"
    echo "DEB_Depends ${DEB_Depends}"
    echo "DEB_Package ${DEB_Package}"
    echo "DEB_Package_u ${DEB_Package_u}"
    echo "DEB_Conflicts ${DEB_Conflicts}"
    echo "DEB_Package_Filename ${DEB_Package_Filename}"
    echo "DEB_Provides ${DEB_Provides}"
}

function update_control_scripts {
    echo "Enter function update_control_scripts"
    CONTROL_SCRIPTS=$(find ${TMP_DIR}/control/ -maxdepth 1 -type f)
    for CONTROL_SCRIPT in ${CONTROL_SCRIPTS}
    do
        echo "Modifying ${CONTROL_SCRIPT} in ${TMP_DIR}/control at $(date)"
        #perl -pi -e "s/^U_UID=.*/U_UID=\"${U_UID}\"/" ${TMP_DIR}/control/${CONTROL_SCRIPT}
        echo "DEBUG : group ${MY_GROUP_USER} tomcat ${TOMCAT_USER} home ${MY_USER_HOME} webapps ${TOMCAT_WEBAPPS_DIR} wars ${WARS}"
        MY_GROUP_USER_esc=$(echo ${MY_GROUP_USER} | sed -e 's/\//\\\//g')
	TOMCAT_USER_esc=$(echo ${TOMCAT_USER} | sed -e 's/\//\\\//g')
	MY_USER_HOME_esc=$(echo ${MY_USER_HOME} | sed -e 's/\//\\\//g')
	TOMCAT_WEBAPPS_DIR_esc=$(echo ${TOMCAT_WEBAPPS_DIR} | sed -e 's/\//\\\//g')
	WARS_esc=$(echo ${WARS} | sed -e 's/\//\\\//g')
#	DEB_Package_u=$(echo -n ${DEB_Package} | sed -e 's/_/-/g')
        echo "perl -pi -e \"s/^MY_GROUP_USER=.*/MY_GROUP_USER=${MY_GROUP_USER_esc}/\" ${CONTROL_SCRIPT}"
        PERLOUT=$(perl -pi -e "s/^MY_GROUP_USER=.*/MY_GROUP_USER=\"${MY_GROUP_USER_esc}\"/" ${CONTROL_SCRIPT})
	echo "perl out is ${PERLOUT} and retval is $?"
        echo "perl -pi -e \"s/^TOMCAT_USER=.*/TOMCAT_USER=${TOMCAT_USER_esc}/\" ${CONTROL_SCRIPT}"
        PERLOUT=$(perl -pi -e "s/^TOMCAT_USER=.*/TOMCAT_USER=\"${TOMCAT_USER_esc}\"/" ${CONTROL_SCRIPT})
	echo "perl out is ${PERLOUT} and retval is $?"
        echo "perl -pi -e \"s/^MY_USER_HOME=.*/MY_USER_HOME=${MY_USER_HOME_esc}/\" ${CONTROL_SCRIPT}"
        PERLOUT=$(perl -pi -e "s/^MY_USER_HOME=.*/MY_USER_HOME=\"${MY_USER_HOME_esc}\"/" ${CONTROL_SCRIPT})
	echo "perl out is ${PERLOUT} and retval is $?"
        echo "perl -pi -e \"s/^TOMCAT_WEBAPPS_DIR=.*/TOMCAT_WEBAPPS_DIR=${TOMCAT_WEBAPPS_DIR_esc}/\" ${CONTROL_SCRIPT}"
        PERLOUT=$(perl -pi -e "s/^TOMCAT_WEBAPPS_DIR=.*/TOMCAT_WEBAPPS_DIR=\"${TOMCAT_WEBAPPS_DIR_esc}\"/" ${CONTROL_SCRIPT})
	echo "perl out is ${PERLOUT} and retval is $?"
        echo "perl -pi -e \"s/^WARS=.*/WARS=${WARS_esc}/\" ${CONTROL_SCRIPT}"
        PERLOUT=$(perl -pi -e "s/^WARS=.*/WARS=\"${WARS_esc}\"/" ${CONTROL_SCRIPT})
	echo "perl out is ${PERLOUT} and retval is $?"
        #debug print postinst, if we add more install scripts or other things the previous control perl subs change we should make this part smarter
        cat ${CONTROL_SCRIPT}
    done
}

function setup_variables {
    echo "Enter function setup_variables"
    if [ -f "$(pwd)/lsb-release" ]
    then
        LSB_RELEASE="$(pwd)/lsb-release"
    else
        LSB_RELEASE="/etc/lsb-release"
    fi
    if [ -f "${LSB_RELEASE}" ]
    then
        if [ -f "version.txt" ]
        then
            SRC_VERSION=$(cat version.txt)
        else
            SRC_VERSION="1.0"
        fi

        ARCH=$(uname -m)
        if [ "${ARCH}" = "x86_64" ]
        then
            ARCH_UBU="amd64"
            echo "ARCH_UBU is ${ARCH_UBU}"
        else
            ARCH_UBU="x86"
            echo "ARCH_UBU is ${ARCH_UBU}"
        fi
        source ${LSB_RELEASE}

    #######################################
    #
    # Jenkins passed values
    #
    #######################################
        #if [ ! "${X}" ]; then X="DEFAULT_VALUE"; echo "X not specified, using default ${X}"; fi
        if [ -z "${TARBALL_FILENAME}" ] || [ ! "${TARBALL_FILENAME}" ]; then TARBALL_FILENAME="tp$(date +%Y%m%d)"; echo "TARBALL_FILENAME not specified, using default ${TARBALL_FILENAME}"; export TARBALL_FILENAME; fi
        if [ ! "${BASE_INSTALL_DIR}" ] || [ ! "${BASE_INSTALL_DIR}" ]; then BASE_INSTALL_DIR="/usr/local/tiwipro"; echo "BASE_INSTALL_DIR not specified, using default ${BASE_INSTALL_DIR}"; export BASE_INSTALL_DIR; fi
        if [ ! "${DEB_S3_bucket}" ] || [ ! "${DEB_S3_bucket}" ]; then DEB_S3_bucket="ci-inthinc-com"; echo "DEB_S3_bucket not specified, using default ${DEB_S3_bucket}"; export DEB_S3_bucket; fi
        if [ ! "${DEB_repository_dir}" ]; then DEB_repository_dir="/var/www/debian"; echo "DEB_repository_dir not specified, using default ${DEB_repository_dir}"; fi
        if [ ! "${DEB_Package}" ]; then DEB_Package="inthinc-${JOB_NAME}"; echo "DEB_Package not specified, using default ${DEB_Package}"; fi
        DEB_Package_u=$(echo -n ${DEB_Package} | sed -e 's/_/-/g')
        echo "Set DEB_Package_u to ${DEB_Package_u}"
        if [ ! "${DEB_Source}" ]; then DEB_Source="${JOB_NAME}"; echo "DEB_Source not specified, using default ${DEB_Source}"; fi
        if [ ! "${DEB_Version}" ]; then DEB_Version="${SRC_VERSION}-${BUILD_NUMBER}~${DISTRIB_ID}~${DISTRIB_CODENAME}"; echo "DEB_Version not specified, using default ${DEB_Version}"; fi
        if [ ! "${DEB_Architecture}" ]; then DEB_Architecture="${ARCH_UBU}"; echo "DEB_Architecture not specified, using default ${DEB_Architecture}"; fi
        if [ ! "${DEB_Maintainer}" ]; then DEB_Maintainer="Inthinc Jenkins <jenkins@inthinc.com>"; echo "DEB_Maintainer not specified, using default ${DEB_Maintainer}"; fi
        if [ ! "${DEB_InstalledSize}" ]; then DEB_InstalledSize="${SIZE_KB}"; echo "DEB_InstalledSize not specified, using figured value ${DEB_InstalledSize}"; fi
        if [ ! "${DEB_Section}" ]; then DEB_Section="misc"; echo "DEB_Section not specified, using default ${DEB_Section}"; fi
        if [ ! "${DEB_Priority}" ]; then DEB_Priority="optional"; echo "DEB_Priority not specified, using default ${DEB_Priority}"; fi
        if [ ! "${DEB_Homepage}" ]; then DEB_Homepage="http://www.inthinc.com/"; echo "DEB_Homepage not specified, using default ${DEB_Homepage}"; fi
        if [ ! "${DEB_Description}" ]; then DEB_Description="Inthinc Generic Package Description : ${BUILD_TAG} ${BUILD_ID} ${NODE_NAME} built at ${JENKINS_URL} on $(date)"; echo "DEB_Description not specified, using default : "; echo "${DEB_Description}"; fi
        if [ ! "${DEB_Conffiles}" ]; then DEB_Conffiles=".conf settings.py .properties .xml .policy"; echo "DEB_Conffiles not specified, using default : "; echo "${DEB_Conffiles}"; fi
        if [ ! "${DEB_PreDepends}" ]; then echo "DEB_PreDepends not specified, and no default skipping"; fi
        if [ ! "${DEB_Depends}" ]; then echo "DEB_Depends not specified, and no default skipping"; fi
        if [ ! "${DEB_Recommends}" ]; then echo "DEB_Recommends not specified, and no default skipping"; fi
        if [ ! "${DEB_Suggests}" ]; then echo "DEB_Suggests not specified, and no default skipping"; fi
        if [ ! "${DEB_Conflicts}" ]; then echo "DEB_Conflicts not specified, and no default skipping"; fi
        if [ ! "${DEB_Replaces}" ]; then echo "DEB_Replaces not specified, and no default skipping"; fi
        if [ ! "${DEB_Provides}" ]; then echo "DEB_Provides not specified, and no default skipping"; fi
        if [ ! "${DEB_Package_Filename}" ]; then DEB_Package_Filename="${WORKSPACE}/${JOB_NAME}_${ARCH_UBU}.deb"; echo "DEB_Package_Filename not specified, using default ${DEB_Package_Filename}"; else echo "Using DEB_Package_Filename ${DEB_Package_Filename}"; fi
        if [ ! "${TOMCAT6_REPO}" ]; then TOMCAT6_REPO="git://github.com/jonzobrist/tomcat6.git"; echo "TOMCAT6_REPO not specified, using default ${TOMCAT6_REPO}"; fi
        if [ ! "${T6_DIRS}" ]; then T6_DIRS="bkup endorsed logarchive logs temp tmp work"; echo "T6_DIRS not specified, using default ${T6_DIRS}"; fi

        # We should get these from our pre-compile build script
        # DEB_PreDepends
        # DEB_Depends
        # DEB_Recommends
        # DEB_Suggests
        # DEB_Conflicts
        # DEB_Replaces
        # DEB_Provides
        # DEB_Section
        # DEB_Priority
        # DEB_Homepage
        # DEB_Description
    fi
}

    function cleanup {
    echo "Enter function cleanup"
        echo "Cleaning up ${TMP_DIR} at $(date)"
        /bin/rm -Rf ${TMP_DIR}
}

function reprepro_clean {
    echo "Enter function reprepro_clean"
# Example line 
# Remove old package : 
# reprepro --ask-passphrase -Vb /var/www/debian removematched precise portal-backend-dev-deb
# Delete from file
# reprepro --ask-passphrase -Vb /var/www/debian deleteunreferenced
    if [ ! "${DEB_Package_u}" ]
     then
        echo "Missing DEB_Package_u, setting at $(date)"
        DEB_Package_u=$(echo -n ${DEB_Package} | sed -e 's/_/-/g')
    fi
    echo "reprepro -Vb ${DEB_repository_dir} removematched  ${DISTRIB_CODENAME} ${DEB_Package_u}"
    reprepro -Vb ${DEB_repository_dir} removematched  ${DISTRIB_CODENAME} ${DEB_Package_u}
    echo "reprepro -Vb ${DEB_repository_dir} deleteunreferenced"
    reprepro -Vb ${DEB_repository_dir} deleteunreferenced
}

function reprepro_publish {
    echo "Enter function reprepro_publish"
# Example line 
# reprepro --ask-passphrase -Vb /var/www/debian includedeb precise /var/lib/jenkins/jobs/portal_backend_dev_deb/workspace/portal_backend_dev_deb_amd64.deb
# With passphrase removed from key 
# reprepro -Vb /var/www/debian includedeb precise /var/lib/jenkins/jobs/portal_backend_dev_deb/workspace/portal_backend_dev_deb_amd64.deb
echo "reprepro -Vb ${DEB_repository_dir} includedeb ${DISTRIB_CODENAME} ${DEB_Package_Filename}"
reprepro -Vb ${DEB_repository_dir} includedeb ${DISTRIB_CODENAME} ${DEB_Package_Filename}
}

function s3_sync {
    echo "Enter function s3_sync"
    S3_CMD=$(which s3cmd)
    if [ -x "${S3_CMD}" ]
     then
        echo "Syncing local debian repository at ${DEB_repository_dir} to S3 bucket ${DEB_S3_bucket} at $(date)"
        echo "s3cmd --verbose --delete-removed  sync ${DEB_repository_dir} s3://${DEB_S3_bucket}/"
        s3cmd --verbose --delete-removed  sync ${DEB_repository_dir} s3://${DEB_S3_bucket}/
    else
        echo "Missing s3cmd, not uploading to S3"
    fi
}

function setup_temp_dir {
    echo "Enter function setup_temp_dir"
    TMP_DIR="${WORKSPACE}/${TARBALL_FILENAME}-debs"
    if [ ! -d "${TMP_DIR}" ]
     then
        echo "TMP_DIR ${TMP_DIR} does not exist, creating"
        mkdir -p ${TMP_DIR}
    fi
    if [ -d "${TMP_DIR}" ]
     then
        cd ${TMP_DIR}
    else
        echo "Failed to create TMP_DIR ${TMP_DIR} exiting at $(date)"
        exit 1
    fi
}

function update_control_file {
    echo "Enter function update_control_file"
    SIZE_KB=$(du -sk ${TMP_DIR} | awk '{ print $1 }')
    CONTROL_FILE="${TMP_DIR}/control/control"
    cp -R ${WORKSPACE}/control ${TMP_DIR}
    touch ${CONTROL_FILE}
    if [ ! -f "${CONTROL_FILE}" ]
     then
         echo "Failed to create ${CONTROL_FILE}, exiting at $(date)"
         exit 1
    fi
    if [ ! "${DEB_Package_u}" ]
     then
        echo "Missing DEB_Package_u, setting at $(date)"
        DEB_Package_u=$(echo -n ${DEB_Package} | sed -e 's/_/-/g')
    fi

    if [ "${DEB_Package_u}" ]; then echo "Package: ${DEB_Package_u}" | tee ${CONTROL_FILE}; fi
    if [ "${DEB_Source}" ]; then echo "Source: ${DEB_Source}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Version}" ]; then echo "Version: ${DEB_Version}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Architecture}" ]; then echo "Architecture: ${DEB_Architecture}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Maintainer}" ]; then echo "Maintainer: ${DEB_Maintainer}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_InstalledSize}" ]; then echo "Installed-Size: ${DEB_InstalledSize}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_PreDepends}" ]; then echo "Pre-Depends: ${DEB_PreDepends}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Depends}" ]; then echo "Depends: ${DEB_Depends}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Recommends}" ]; then echo "Recommends:  ${DEB_Recommends}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Suggests}" ]; then echo "Suggests: ${DEB_Suggests}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Conflicts}" ]; then echo "Conflicts: ${DEB_Conflicts}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Replaces}" ]; then echo "Replaces: ${DEB_Replaces}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Provides}" ]; then echo "Provides: ${DEB_Provides}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Section}" ]; then echo "Section: ${DEB_Section}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Priority}" ]; then echo "Priority: ${DEB_Priority}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Homepage}" ]; then echo "Homepage: ${DEB_Homepage}" | tee -a ${CONTROL_FILE}; fi
    if [ "${DEB_Description}" ]; then echo "Description: ${DEB_Description}" | tee -a ${CONTROL_FILE}; fi
}

function extract_built_tarball {
    echo "Enter function extract_built_tarball"
    cd ${TMP_DIR}
    echo "TARBALL_FILENAME is ${TARBALL_FILENAME}"
    if [ -f "${WORKSPACE}/${TARBALL_FILENAME}.tgz" ]
    then
        echo "Extracting tarfile ${WORKSPACE}/${TARBALL_FILENAME}.tgz to ${TMP_DIR} at $(date)"
        tar -zvxf ${WORKSPACE}/${TARBALL_FILENAME}.tgz
        if [ -d "${TMP_DIR}/${TARBALL_FILENAME}" ]
        then
            echo "Extracted to ${TMP_DIR}/${TARBALL_FILENAME}"
        else
            echo "Failed to extract ${WORKSPACE}/${TARBALL_FILENAME}.tgz to ${TMP_DIR}/${TARBALL_FILENAME}, exiting at $(date)"
            exit 1
        fi
    else
        echo "Missing ${WORKSPACE}/${TARBALL_FILENAME}.tgz exiting at $(date)"
        cleanup
        exit 1
    fi
}

function create_archive {
    echo "Enter function create_archive"
    echo "Creating archive at $(date)"
    echo "2.0" > ${TMP_DIR}/debian-binary
    mkdir -p ${TMP_DIR}/data
    cd ${TMP_DIR}/data
    PARENT_DIR="${BASE_INSTALL_DIR%/*}"
    mkdir -p .${PARENT_DIR}
    if [ -d ".${PARENT_DIR}" ]; then echo "Created .${PARENT_DIR}, moving build into .${BASE_INSTALL_DIR}"; else echo "Failed to create .${PARENT_DIR}, exiting at $(date)"; exit 1; fi
    mv ${TMP_DIR}/${TARBALL_FILENAME} .${BASE_INSTALL_DIR}
    if [ -d ".${BASE_INSTALL_DIR}" ]; then echo "Moved build into .${BASE_INSTALL_DIR}"; else echo "Failed to create .${PARENT_DIR}, exiting at $(date)"; exit 1; fi
    if [ -d ".${BASE_INSTALL_DIR}/etc" ]
    then
        echo "We have an etc dir in .${BASE_INSTALL_DIR}"
        /bin/cp -a .${BASE_INSTALL_DIR}/etc ${TMP_DIR}/data
    else
        echo "No etc dir in .${BASE_INSTALL_DIR}"
    fi
    MD5SUMS_FILE="${TMP_DIR}/control/md5sums"
    if [ -f "${MD5SUMS_FILE}" ]
    then
        echo "Deleting old md5sums file $(cat ${MD5SUMS_FILE} | wc -l) lines at $(date)"
        /bin/rm ${MD5SUMS_FILE}
    fi
    echo "Creating md5sums file"
    find . -type f -exec md5sum {} \; | sed -e 's/  \./  /' | tee ${MD5SUMS_FILE}
    CONF_FILE="${TMP_DIR}/control/conffiles"
    if [ -f "${CONF_FILE}" ]
     then
         echo "Removing exiting conffile ${CONF_FILE}"
         /bin/rm ${CONF_FILE}
     fi
    touch ${CONF_FILE}
    if [ ! -f "${CONF_FILE}" ]
     then
         echo "Failed to create ${CONF_FILE}, exiting at $(date)"
         exit 1
    fi
    for EXT in ${DEB_Conffiles}
    do
        echo "Adding files as conffiles with extensions ${EXT}"
        echo "Debug : find . -type f -iname \"*${EXT}\" | sed -e 's/^\.//' | tee -a ${CONF_FILE}"
        find . -type f -iname "*${EXT}" | sed -e 's/^\.//' |tee -a ${CONF_FILE}
    done
    echo "Creating data.tar.gz for debian package"
    tar -zcf ${TMP_DIR}/data.tar.gz *
    cd ${TMP_DIR}/control
    echo "Removing underscores from Control file"
    perl -pi -e 's/_/-/g' ${CONTROL_FILE}
    echo "Creating control tarball for debian package"
    tar -zcf ${TMP_DIR}/control.tar.gz *
    cd ${TMP_DIR}
    echo "Removing data and control folders"
    /bin/rm -Rf data
    /bin/rm -Rf control
    echo "Creating debian package ar archive"
    if [ -f "debian-binary" ] && [ -f "control.tar.gz" ] && [ "data.tar.gz" ]
     then
         echo "Creating debian package ${DEB_Package_Filename} at $(date)"
        echo "ar r debian-binary ${DEB_Package_Filename} debian-binary control.tar.gz data.tar.gz"
        ar r ${DEB_Package_Filename} debian-binary control.tar.gz data.tar.gz
    else
        echo "Missing at least one file in $(pwd) exiting at $(date)"
        echo "LS of dir is :"
        ls -hl
        exit 1
    fi
    if [ ! -f "${DEB_Package_Filename}" ]
     then
        echo "Failed to build deb package ${DEB_Package_Filename}, exiting at $(date)"
        cleanup
        exit 1
    else
        echo "Successfully created package ${DEB_Package_Filename} at $(date)"
        echo "MD5sum for new pacakage is $(md5sum ${DEB_Package_Filename})"
    fi
}

function reprepro_add {
    echo "Enter function reprepro_add"
    # This way doesn't work wit the way I'm using the env variables straight from Jenkins
    #if [ "${NODE_NAME}" = "master" ]
    # Going to use the username, and hard code ubuntu = vagrant instance, and jenkins = master node
    if [ "${USER}" = "jenkins" ]
    then
        echo "We are running on the master node, running reprepro locally"
        reprepro_clean
        reprepro_publish
    else
        echo "We are not running on the master node, skipping reprepro steps"
        echo "Additional steps required to add these packages to the master node"
        echo "reprepro -Vb ${DEB_repository_dir} removematched  ${DISTRIB_CODENAME} ${DEB_Package_u}"
        echo "reprepro -Vb ${DEB_repository_dir} deleteunreferenced"
        echo "reprepro -Vb ${DEB_repository_dir} includedeb ${DISTRIB_CODENAME} ${DEB_Package_Filename}"
    fi
}

echo "Starting ${0} on $(hostname) at $(date)"
#echo "############################################ $(date)"
#echo "First pass, creating default debian package : "
#echo "############################################ $(date)"
#check_jenkins_variables
#setup_variables
#cleanup
#setup_temp_dir
#extract_built_tarball
#update_control_file
#create_archive
#reprepro_add

echo "############################################ $(date)"
echo "Second pass, building for tomcat2 setup : "
echo "############################################ $(date)"
reset_control_in_temp
setup_variables
setup_tomcat2_variables
cleanup
setup_temp_dir
extract_built_tarball
update_control_file
update_control_scripts
get_tomcat6_blank
create_archive
#reprepro_add

#do each war thing here
#for war in ls webapps/*war
#make a package with a user named the same thing as the war

if [ "${USER}" = "jenkins" ]
then
    echo "We are running on the master node, running s3cmd locally"
#    s3_sync
else
    echo "We are not running on the master node, skipping reprepro steps"
    echo "Additional steps required to add these packages to the master node"
    echo "s3cmd --verbose --delete-removed  sync ${DEB_repository_dir} s3://${DEB_S3_bucket}/"
fi

cleanup
exit 0

