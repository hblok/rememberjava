#!/bin/bash

set -x -e

THIS_DIR="$(readlink -f $(dirname $0))"

cd /base

bazel test --test_output all --test_tag_filters -skip //...
