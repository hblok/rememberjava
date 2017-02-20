/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class WordCount {

  @Test
  public void countWords() throws IOException {
    Path path = Paths.get("com/rememberjava/lambda/words");

    Map<String, Long> map = Files.lines(path)
        .flatMap(line -> Stream.of(line.split(" ")))
        .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    System.out.println(map);
  }
}
