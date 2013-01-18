#!/bin/bash
if [ -z "${TARBALL_FILENAME}" ] || [ ! "${TARBALL_FILENAME}" ]; then TARBALL_FILENAME="tp$(date +%Y%m%d)"; echo "TARBALL_FILENAME not specified, using default ${TARBALL_FILENAME}"; fi
TMP_DIR="${WORKSPACE}/${TARBALL_FILENAME}"
if [ ! -d "${WORKSPACE}/logs" ]
    mkdir -p ${WORKSPACE}/logs
fi
if [ ! "${catalina_home}" ]
then
    catalina_home=${WORKSPACE}
    CATALINA_HOME=${WORKSPACE}
fi
if [ -d "${TMP_DIR}" ]
then
    /bin/rm -Rf ${TMP_DIR}
fi
mkdir -p ${TMP_DIR}
if [ -d "${TMP_DIR}" ]
 then
    echo "Created temp dir ${TMP_DIR}"
else
    echo "Failed to create temp dir ${TMP_DIR} Error : $?"
    echo "Exiting at $(date)"
    exit 1
fi
echo "Getting wars : "
# Old way, which overwrote identical wars in order they are found, could probably work if we just added | grep target
# and checked for already added war of the same name or cp -i
# find ${WORKSPACE} -type f -iname "*.war" -print -exec /bin/cp {} ${TMP_DIR} \;
/bin/cp ${WORKSPACE}/web/target/tiwipro.war ${TMP_DIR}
/bin/cp ${WORKSPACE}/webutil/target/tiwiproutil.war ${TMP_DIR}
/bin/cp ${WORKSPACE}/service/target/service.war ${TMP_DIR}
/bin/cp ${WORKSPACE}/scheduler/target/scheduler.war ${TMP_DIR}
/bin/cp ${WORKSPACE}/hoskiosk/target/hoskiosk.war ${TMP_DIR}
/bin/cp ${WORKSPACE}/include_wars/*.war ${TMP_DIR}


echo "Cleaning old tgz files"
find ${WORKSPACE} -maxdepth 1 -type f -iname "*.tgz" -mtime +1 -print -exec /bin/rm {} \;


if [ -f "${TARBALL_FILENAME}.tgz" ]
then
    /bin/rm ${TARBALL_FILENAME}.tgz
fi

tar -C ${WORKSPACE} -zcf ${TARBALL_FILENAME}.tgz ${TARBALL_FILENAME}
if [ -f "${TARBALL_FILENAME}.tgz" ]
then
        md5sum ${TARBALL_FILENAME}.tgz
else
    echo "Failed to create tarball ${TARBALL_FILENAME}.tgz, exiting at $(date)"
    /bin/rm -Rf ${TMP_DIR}
    exit 1
fi

if [ -d "${TMP_DIR}" ]
then
    /bin/rm -Rf ${TMP_DIR}
else
    echo "Something cleaned up ${TMP_DIR}"
fi

exit 0

