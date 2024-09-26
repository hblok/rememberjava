---
published: true

layout: post
title:  Bazel ❤ Java
date:   2024-09-26
categories: bazel 
tags: bazel build test
---

[Bazel][bazel] is an open-source build and test tool similar to Make, CMake, Maven, and Gradle. It is cross platform, and as opposed to the mentioned tools, it supports a plethora of languages; many natively and almost anything else by extendable rules and tool chains.

Bazel's strong points are a human-readable high-level build and dependency language (in contrast to Make or CMake), and having the dependency graph between modules and projects as the foundation of the build processing itself. This means that all actions, builds and tests can be cached deterministically, and only need to be re-executed if there are actual changes.

Finally, Bazel integrates seamlessly with external projects, source and project repositories, whether Git, Github, Pip, Maven repository, or simply downloaded tar.gz files. As is common, dependencies can be locked to specific versions and verified with check-sums.

# Install

[Installing Bazel][install] is a breeze, and multiple alternatives are supported. My personal preference is to [use their Debian / Ubuntu apt repository][apt]. When using Github Action, pre-built images and caching is readily available and easy to use, [as seen here][bazel_action].

# Build and test

To start using Bazel in an existing code base takes minimal setup. The bare minimum is 1) `MODULE.bazel` file at top level repository directory (at first, it can be empty), and 2) at least one `BUILD` file which defines a build rule, for example a unit test. These two commits put in place [the top level files][MODULE] and [the first BUILD file][first] in our Git code.

A simple test rule might look like this:

{% highlight shell %}
java_test(
    name = "HelloJunit",
    srcs = ["HelloJunit.java"],
)
{% endhighlight %}

It's a Java Junit test, and it is given a name, which by convention is the same as the testcase file, and finally it specifies the source file(s).

Here's [another example][calc], which defines a library, a binary, and a unit test. The two later depend on the library.

{% highlight shell %}
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
{% endhighlight %}

To build and execute these tests, various query strings can be used. The first builds and runs only a single test in the current directory:

{% highlight shell %}
bazel test :HelloJunit
{% endhighlight %}

To run all test at and below the current directory, the `...` query is used:

{% highlight shell %}
bazel test ...
{% endhighlight %}

A `/` at the beginning references the top level. Thus, this runs all tests:

{% highlight shell %}
bazel test ...
{% endhighlight %}

To execute a binary, the run command is used:

{% highlight shell %}
bazel run :Calculator
{% endhighlight %}

For even more Bazel examples, including other languages, see our [Bazel Examples repository][bazel_examples].


[bazel]: https://bazel.build
[install]: https://bazel.build/install
[apt]: https://bazel.build/install/ubuntu
[bazel_action]: https://github.com/hblok/rememberjava/blob/master/.github/workflows/main.yml
[MODULE]: https://github.com/hblok/rememberjava/commit/f578aa4a8a92595268dd080036e8aeb09988df1a
[first]: https://github.com/hblok/rememberjava/commit/91970013f9b173d8a1fa21c0c06269677e941265
[calc]: https://github.com/hblok/rememberjava/blob/master/_includes/src/com/rememberjava/calc/BUILD
[bazel_examples]: https://github.com/hblok/bazel_examples
