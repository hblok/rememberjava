package com.rememberjava.grpc;

import java.io.IOException;

import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import io.grpc.stub.StreamObserver;

import com.rememberjava.grpc.GreeterGrpc;
import com.rememberjava.grpc.HelloReply;
import com.rememberjava.grpc.HelloRequest;


class HelloServer extends GreeterGrpc.GreeterImplBase {

    @Override
    public void sayHello(HelloRequest request,
			 StreamObserver<HelloReply> responseObserver) {
	String from = request.getFrom().getFirstname();
	String to = request.getTo().getFirstname();
	System.out.println("Server: " + from + " says hello to " + to);

	HelloReply response = HelloReply
	    .newBuilder()
	    .setReply(to + " sends greetings.")
	    .build();

	responseObserver.onNext(response);
	responseObserver.onCompleted();
    }

    void start() throws IOException, InterruptedException {
	int port = 1234;

	System.out.println("Starting gRPC HelloServer on port " + port);
	
	ServerBuilder<?> serverBuilder = Grpc.newServerBuilderForPort(
	    port, InsecureServerCredentials.create());
	Server server = serverBuilder.addService(this).build();
	server.start();
	server.awaitTermination();
    }

    public static void main(String[] args) throws IOException, InterruptedException {
	new HelloServer().start();
    }
}
