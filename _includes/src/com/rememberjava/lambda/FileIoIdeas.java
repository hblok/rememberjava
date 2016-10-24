package com.rememberjava.lambda;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileIoIdeas {

  public static void main(String[] args) throws IOException {
    olden();
  }

  static Path path = Paths.get("/tmp/a");

  public static void count2() throws IOException {
    Map<String, Long> map = Files.lines(path)
        .flatMap(line -> Stream.of(line.split(" ")))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    System.out.println(map);
  }

  public static void forloop() throws IOException {
    Map<String, Long> map = new HashMap<>();

    for (String line : Files.readAllLines(path)) {
      for (String s : line.split(" ")) {
        map.put(s, map.getOrDefault(s, 1L));
      }
    }

    System.out.println(map);
  }

  public static void olden() throws IOException {
    Map<String, Long> map = new HashMap<>();

    BufferedReader in = new BufferedReader(new FileReader("/tmp/a"));
    String line;
    while ((line = in.readLine()) != null) {
      for (String s : line.split(" ")) {
        map.put(s, map.containsKey(s) ? map.get(s) + 1 : 1);
      }
    }
    in.close();

    System.out.println(map);

  }

  public static void total() throws IOException {
    Path p = Paths.get("/tmp/a");
    int total = Files.lines(p).map(line -> {
      return line.split(" ").length;
    }).reduce(0, (a, b) -> a + b).intValue();
    System.out.println(total);
  }

  public static void map() {
    Map<Integer, String> m = new HashMap<>();
    m.put(1, "one");
    m.put(2, "two");

    m.entrySet().stream().map(e -> e.getKey() + " " + e.getValue()).forEach(System.out::println);
  }

  public static void count() throws IOException {
    Path p = Paths.get("/tmp/a");
    Files.lines(p).map(line -> {
      return Arrays.asList(line.split(" "));
    }).reduce(new ArrayList<>(), (a, b) -> {
      a.addAll(b);
      return a;
    }).stream().collect(Collectors.groupingBy(String::toString)).entrySet().stream()
        .map(e -> e.getKey() + " " + e.getValue().size()).forEach(System.out::println);
  }

  public static void grep_cut() throws IOException {
    Path p = Paths.get("/tmp/a");
    Files.lines(p).filter(str -> str.contains("note")).map(str -> {
      String s[] = str.split(" ");
      return s[s.length - 1];
    }).forEach(System.out::println);

  }
}
