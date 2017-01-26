package com.rememberjava.reflect;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Constructor;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.Test;

public class ObjectConstructorTest {

  enum ClassNames {
    Foo(Foo.class),
    Account(Account.class);

    private Class<?> type;

    private ClassNames(Class<?> type) {
      this.type = type;
    }

    Class<?> getType() {
      return type;
    }
  }

  static class Foo {
    final String str;
    final int i;
    final double d;

    public Foo(String str, int i, Double d) {
      this.str = str;
      this.i = i;
      this.d = d;
    }
  }

  static class Account {
    final Email email;
    final Password password;

    public Account(Email email, Password password) {
      this.email = email;
      this.password = password;
    }
  }

  static class Email {
    final String email;

    Email(String email) {
      this.email = email;
    }
  }

  static class Password {
    final String hash;

    Password(String hash) {
      this.hash = hash;
    }

    static Password hash(String pw) {
      try {
        MessageDigest digest = MessageDigest.getInstance("SHA");
        digest.update(pw.getBytes());
        digest.update("some salt".getBytes());

        String base64 = Base64.getEncoder().encodeToString(digest.digest());

        return new Password(base64);
      } catch (NoSuchAlgorithmException e) {
        e.printStackTrace();
      }
      return null;
    }
  }

  @SuppressWarnings("serial")
  Map<Class<?>, Function<String, ?>> classConstructorMap = new HashMap<Class<?>, Function<String, ?>>() {{
    put(String.class, String::new);
    put(Integer.TYPE, Integer::new);
    put(Double.class, Double::new);
    put(Email.class, Email::new);
    put(Password.class, Password::hash);
  }};

  Object construct(String type, String... args) throws Exception {
    Class<?> klass = ClassNames.valueOf(type).getType();
    Constructor<?>[] constructors = klass.getConstructors();
    Class<?>[] types = constructors[0].getParameterTypes();
    Object[] parameters = createParameters(types, args);
    return constructors[0].newInstance(parameters);
  }

  Object[] createParameters(Class<?>[] types, String[] args) {
    if (types.length != args.length) {
      throw new IllegalArgumentException("Expects: " + Arrays.asList(types));
    }

    Object[] result = new Object[types.length];
    for (int i = 0; i < types.length; i++) {
      Class<?> klass = types[i];
      Function<String, ?> constuctor = classConstructorMap.get(klass);
      if (constuctor == null) {
        throw new IllegalArgumentException("Constructor for " + klass + " not declared.");
      }
      result[i] = constuctor.apply(args[i]);
    }
    return result;
  }

  @Test
  public void test() throws Exception {
    Foo foo = (Foo) construct("Foo", "ABC", "123", "3.14");
    assertEquals("ABC", foo.str);
    assertEquals(123, foo.i);
    assertEquals(3.14, foo.d, 2);

    Account account = (Account) construct("Account", "bob@example.com", "some password");
    assertEquals("V0PYfuPn4u9Gize+0DZ0nLgQQPk=", account.password.hash);
  }
}
