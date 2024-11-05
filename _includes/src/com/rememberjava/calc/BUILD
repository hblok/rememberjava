java_library(
    name = "Model",
    srcs = [
	"Expression.java",
	"Node.java",
	"Operator.java",
	"Tree.java",
	"Value.java",
        "Model.java",
    ]
)

java_binary(
    name = "Calculator",
    main_class = "com.rememberjava.calc.CalculatorMain",
    srcs = [
	"Button.java",
	"ButtonType.java",
	"Controller.java",
	"UiFrame.java",
        "CalculatorMain.java",
    ],
    deps = [
        ":Model",
	"@maven2//:org_openjdk_nashorn_nashorn_core",
    ]
)

java_test(
    name = "ModelTest",
    srcs = ["ModelTest.java"],
    deps = [
        ":Model",
    ],
    tags = ["manual"]
)

