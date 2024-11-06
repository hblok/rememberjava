---
published: true

layout: post
title:  Protocol Buffers
date:   2024-11-05
categories: protobuf
tags: protobuf
---

[Protocol Buffers][proto_doc] (aka "protobufs") is another Google technology which integrates neatly with Java (and other languages). As their tag-lines says: "Protocol Buffers are language-neutral, platform-neutral extensible mechanisms for serializing structured data".

For the sake of this example, we will look at 1) definition of a "proto" file; 2) integration with Bazel; 3) and use in a small Java test example.

# Proto Message

At the heart of protobufs is the Message, which is an abstract language agnostic definition of a data class. As any class, the Message has fields, which can be of a few basic common native types or other Message types. Thus, any data structure can be realised.

For this example, let's take the Person message from the official example. It comes with a few basic fields:

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/protobuf/person.proto' method='Person' %}
{% endhighlight %}

The proto file is compiled using the `protoc` command line tool, which will produce language specific source code to depend on.

# Bazel

For the sake of this example, we will use Bazel to build. It has native (more or less) support for protobufs. No extra dependencies are needed in the `MODULE.bazel` nor the `BUILD` file.

There are two rules to specify, one for the proto itself and one for the Java specific library. The latter will depend on the former:

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/protobuf/BUILD' method='proto_library' before=0 after=9 %}
{% endhighlight %}

This can now be compiled using:

{% highlight shell %}
bazel build :person_java_proto
{% endhighlight %}

# Use in Java code

The generated class will be available for import as any other class. Notice the package, which is defined specifically for Java at the top of the .proto file.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/protobuf/PersonTest.java' method='import com' before=0  after=0 %}
{% endhighlight %}

A proto message can be instantiated using its [builder methods](builder_pattern). The instance is immutable.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/protobuf/PersonTest.java' method='testPerson' before=-1  after=-2 %}
{% endhighlight %}

The official example shows a slightly more complex structure, with nested objects:

{% highlight java %}
Person john =
  Person.newBuilder()
    .setId(1234)
    .setName("John Doe")
    .setEmail("jdoe@example.com")
    .addPhones(
      Person.PhoneNumber.newBuilder()
        .setNumber("555-4321")
        .setType(Person.PhoneType.PHONE_TYPE_HOME)
        .build());
    .build();
{% endhighlight %}


Files from this example:

{% include javafile filename='src/com/rememberjava/protobuf/person.proto' %}
{% include javafile filename='src/com/rememberjava/protobuf/PersonTest.java' %}
{% include javafile filename='src/com/rememberjava/protobuf/BUILD' %}


[proto_doc]: https://protobuf.dev/
[proto_java_tut]: https://protobuf.dev/getting-started/javatutorial/
[builder_pattern]: https://blogs.oracle.com/javamagazine/post/exploring-joshua-blochs-builder-design-pattern-in-java