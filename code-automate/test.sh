#!/bin/bash

. ./common.sh

set -x
set -e
GIT_SOURCE_PATH=/home/darwin/workspace/opensource/elasticsearch_loader-001
generate_history "$GIT_SOURCE_PATH"
move_modified_files "$GIT_SOURCE_PATH" "1"
move_modified_files "$GIT_SOURCE_PATH" "3"