package com.rememberjava.grpc;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

import com.rememberjava.protobuf.Person;
import com.rememberjava.grpc.GreeterGrpc;
import com.rememberjava.grpc.HelloReply;
import com.rememberjava.grpc.HelloRequest;


class HelloClient {

    void hello() {
	int port = 1234;
	
	ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", port)
          .usePlaintext().build();

	GreeterGrpc.GreeterBlockingStub stub =
	    GreeterGrpc.newBlockingStub(channel);

	HelloRequest request = HelloRequest.newBuilder()
	    .setFrom(Person.newBuilder()
		     .setFirstname("Foo").build())
	    .setTo(Person.newBuilder()
		   .setFirstname("Bar").build())
	    .build();
	    
	HelloReply reply = stub.sayHello(request);
	System.out.println("Reply on client: " + reply.getReply());

	channel.shutdown();
    }

    public static void main(String[] args) {
	new HelloClient().hello();
    }
}

