#!/bin/sh
chmod +x run.sh
exec java ${JAVA_OPTS} -jar app.jar ${@}
