java_library(
    name = "SimpleHttpServer",
    srcs = [
       "SimpleHttpServer.java",
       "StaticFileHandler.java",
    ],
    data = [
       "test.txt"
    ],
    deps = [
       "@maven2//:org_simpleframework_simple_http",
    ]
)

java_test(
    name = "SfHttpServerTest",
    srcs = ["SfHttpServerTest.java"],
    deps = [
       "@maven2//:org_simpleframework_simple_transport",
       "@maven2//:org_simpleframework_simple_http",
    ]    
)

java_test(
    name = "SfWebsocketServerTest",
    srcs = ["SfWebsocketServerTest.java"],
    deps = [
       "@maven2//:org_simpleframework_simple_transport",
       "@maven2//:org_simpleframework_simple_http",
    ]
    
)

java_test(
    name = "SimpleHttpServerTest",
    srcs = ["SimpleHttpServerTest.java"],
    deps = [":SimpleHttpServer"],
)

