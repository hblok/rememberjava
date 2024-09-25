# Runs all Bazel tests in a Debian-based container

FROM debian:12

# OS packages
RUN apt-get update && \
    export DEBIAN_FRONTEND=noninteractive && \
    apt-get install -y software-properties-common curl git apt-transport-https gnupg sudo
RUN apt-get install -y openjdk-17-jdk-headless    

# Bazel
RUN curl -fsSL https://bazel.build/bazel-release.pub.gpg | gpg --dearmor > bazel-archive-keyring.gpg && \
    sudo mv bazel-archive-keyring.gpg /usr/share/keyrings && \
    echo "deb [arch=amd64 signed-by=/usr/share/keyrings/bazel-archive-keyring.gpg] https://storage.googleapis.com/bazel-apt stable jdk1.8" | sudo tee /etc/apt/sources.list.d/bazel.list && \
    apt-get update && \
    apt-get install -y bazel

ARG USERNAME=bazel
ARG USER_UID=2000
ARG USER_GID=$USER_UID
RUN groupadd --gid $USER_GID $USERNAME && \
    useradd --uid $USER_UID --gid $USER_GID -m $USERNAME
USER bazel
