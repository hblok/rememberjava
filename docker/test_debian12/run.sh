#!/bin/bash

set -x -e

THIS_DIR="$(readlink -f $(dirname $0))"
BASE_DIR="$(realpath $THIS_DIR/../..)"

IMAGE_TAG="rj_test"
CONTAINER_NAME="exec_bazel"

docker build --tag $IMAGE_TAG .
docker run --rm --name $CONTAINER_NAME -v $BASE_DIR:/base $IMAGE_TAG /base/docker/test_debian12/internal_test.sh

