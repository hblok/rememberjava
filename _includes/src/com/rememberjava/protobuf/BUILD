
proto_library(
    name = "person_proto",
    srcs = ["person.proto"],
)

java_proto_library(
    name = "person_java_proto",
    deps = [":person_proto"],
)

java_test(
    name = "PersonTest",
    size = "small",
    srcs = ["PersonTest.java"],
    deps = [
        ":person_java_proto",
    ],
)
