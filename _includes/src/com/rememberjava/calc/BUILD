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
    srcs = [
	"Button.java",
	"ButtonType.java",
	"Controller.java",
	"UiFrame.java",
        "CalculatorMain.java",
    ],
    deps = [
        ":Model",
    ]
)

java_test(
    name = "ModelTest",
    srcs = ["ModelTest.java"],
    deps = [
        ":Model",
    ]
)

