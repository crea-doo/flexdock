#!/bin/bash

SD=$(cd $(dirname $0); pwd -P)
WD="`pwd`"
SCRIPT=$(basename $0)
SCRIPT_NAME=${SCRIPT%.*}
SCRIPT_EXTENSION=${SCRIPT##*.}
SELF=$SD/$SCRIPT

cd $SD/

mvnw -N versions:update-child-modules $@
mvnw versions:commit $@

cd $WD/