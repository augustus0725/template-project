#!/usr/bin/env bash

set -e
DXP_RUNTIME_HOME=${1}
DXP_TASK_CONFIG=${2}
DXP_TASK_OPTIONS=${3}

python "${DXP_RUNTIME_HOME}"/bin/datax.py ${DXP_TASK_OPTIONS} "${DXP_TASK_CONFIG}" &
echo $! > ${DXP_TASK_CONFIG}.python.pid
sleep 3
ps --ppid $(cat ${DXP_TASK_CONFIG}.python.pid) | tail -n 1 | awk '{print $1}' > ${DXP_TASK_CONFIG}.pid
rm -rf ${DXP_TASK_CONFIG}.python.pid
wait
