#!/bin/bash

set -x -e

THIS_DIR="$(readlink -f $(dirname $0))"

cd /base

# TODO: Skip very large tests
bazel test ...
