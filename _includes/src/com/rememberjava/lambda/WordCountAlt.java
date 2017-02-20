/* Copyright rememberjava.com. Licensed under GPL 3. See http://rememberjava.com/license */
package com.rememberjava.lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

public class WordCountAlt {

  private static Collector<String, ?, Map<String, Long>> WORD_COUNT_GROUPING = 
      Collectors.groupingBy(Function.identity(), Collectors.counting());

  @Test
  public void countWords() throws IOException {
    Path path = Paths.get("com/rememberjava/lambda/words");

    Stream<String> words = Files.lines(path)
        .flatMap(line -> Stream.of(line.split(" ")));

    Map<String, Long> wordCountMap = words.collect(WORD_COUNT_GROUPING);

    System.out.println(wordCountMap);
  }
}
