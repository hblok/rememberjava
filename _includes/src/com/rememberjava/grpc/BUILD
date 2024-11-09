
load("@rules_proto_grpc_java//:defs.bzl", "java_grpc_library")

proto_library(
    name = "hello_proto",
    srcs = ["hello.proto"],
    deps = [
        "//_includes/src/com/rememberjava/protobuf:person_proto",
    ],
)

java_grpc_library(
    name = "hello_java_grpc",
    protos = [
        ":hello_proto",
        "//_includes/src/com/rememberjava/protobuf:person_proto",
    ],
)

java_binary(
    name = "HelloServer",
    srcs = ["HelloServer.java"],
    deps = [":hello_java_grpc"],
)

java_binary(
    name = "HelloClient",
    srcs = ["HelloClient.java"],
    deps = [":hello_java_grpc"],
)
