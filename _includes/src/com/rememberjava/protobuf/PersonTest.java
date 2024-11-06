package com.rememberjava.protobuf;

import com.rememberjava.protobuf.Person;

import static org.junit.Assert.*;
import org.junit.Test;

public class PersonTest {

    @Test
    public void testPerson() {
	Person person = Person.newBuilder()
	    .setFirstname("Bob")
	    .setLastname("Johnson")
	    .setEmail("bob@example.com")
	    .build();

	assertEquals("Bob", person.getFirstname());
    }
}
