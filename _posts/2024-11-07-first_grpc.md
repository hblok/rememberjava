---
published: true	

layout: post
title:  gRPC
date:   2024-11-07
categories: grpc
tags: grpc protocol bazel
---

To round off the list of useful Google technologies for now, let's look at [gRPC][grpc]. Building on top of [Protocol Buffers][proto_doc], it enables a language agnostic RPC definition. Similar to protobufs, auto-generated source code for a long list of compatible languages can easily be integrated. This includes the source for both the client and the server side. The communication is by protobuf objects, over HTTP.

# Proto Service

Like protobufs, the RPC service API is defined in a `.proto` file.

Notice the `service` and `rpc` keywords. Within a service, there can be one or more rpc methods. The method declaration follows the pattern "rpc MethodName (InputType) returns (ReturnType) {}". However, as opposed to many languages, there can only be one input and one return object, which means that it is common to have dedicated Request and Response types with nested fields.

Finally, the import statement includes the proto we looked in the [previous example][proto_example]. The Person message can then be used in the fields (or grpc) of this proto file.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/grpc/hello.proto' method='import' before=0 after=0 %}
{% include includemethod filename='src/com/rememberjava/grpc/hello.proto' method='Greeter' before=0 after=11 %}
{% endhighlight %}

# Bazel

To compile the gRPC service code, we need an additional Bazel rule. Now, gRPC compile rules have changed frequently over the years, often with incompatible versions and upgrades. For now, we're using the `java_grpc_library` rule from [*rules_proto_grpc_java*][rules_proto_grpc_java]. This rule will also compile the imported `person.proto`.

Notice the `protos` field, similar to `srcs` in other rules. It takes the `proto_library` dependencies of all protos and dependencies to compile.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/grpc/BUILD' method='load' before=0  after=16 %}
{% endhighlight %}

Finally, there are two `java_binary` rules for the server and client which we'll look at next.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/grpc/BUILD' method='java_binary' before=0  after=12 %}
{% endhighlight %}

# Server

This examples includes a very minimal server class, which combines the main, start and RPC methods. Typically, these would be split over multiple classes and files.

First, the `sayHello` RPC method, which takes the protobufs `HelloRequest` input and `HelloReply` output as parameters. Within the methods, the usual protobuf generated API methods apply.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/grpc/HelloServer.java' method='sayHello' before=1  after=14 %}
{% endhighlight %}

To start and let the server run, a `io.grpc.Server` objected is constructed our `GreeterGrpc` as the Service. This runs in blocking mode.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/grpc/HelloServer.java' method='void start()' before=0  after=0 %}
{% endhighlight %}

# Client

Finally, we fire up a minimal gRPC client to make a request on the server. It news a `channel`, a `stub` (which has the service API) and a `request`. After the call, the `reply` from the server is printed and we shutdown and exit.

{% highlight java %}
{% include includemethod filename='src/com/rememberjava/grpc/HelloClient.java' method='void hello()' before=0  after=0 %}
{% endhighlight %}

# Run it

To run the server and client, use two separate terminal windows and call, in order:

{% highlight shell %}
bazel run :HelloServer
{% endhighlight %}

{% highlight shell %}
bazel run :HelloClient
{% endhighlight %}


## **Files**

Here are files includes in this example:

{% include javafile filename='src/com/rememberjava/grpc/hello.proto' %}
{% include javafile filename='src/com/rememberjava/protobuf/person.proto' %}
{% include javafile filename='src/com/rememberjava/grpc/BUILD' %}
{% include javafile filename='src/com/rememberjava/grpc/HelloServer.java' %}
{% include javafile filename='src/com/rememberjava/grpc/HelloClient.java' %}

[grpc]: https://grpc.io/
[proto_doc]: https://protobuf.dev/
[proto_example]: /protobuf/2024/11/05/first_proto.html
[rules_proto_grpc_java]: https://registry.bazel.build/modules/rules_proto_grpc_java