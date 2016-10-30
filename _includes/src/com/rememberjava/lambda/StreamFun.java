package com.rememberjava.lambda;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;

public class StreamFun {

  @Test
  public void printFile() throws IOException {
    Path p = Paths.get("/etc/passwd");
    Files.lines(p).sorted().forEach(System.out::println);
  }

  @Test
  public void printFileAlternative() throws IOException {
    Path p = Paths.get("/etc", "issue");
    Stream<String> lines = Files.lines(p);
    Stream<String> sorted = lines.sorted();
    sorted.forEach(System.out::println);
    lines.close();
  }

  @Test
  public void walkDirectories() throws IOException {
    Path p = Paths.get("/proc/bus");
    Files.walk(p).forEach(System.out::println);
  }

  @Test
  public void testGoldenFile() throws IOException {
    assertFileEquals("/proc/cpuinfo", "/proc/cpuinfo");
  }

  private void assertFileEquals(String expectedFile, String actualFile) throws IOException {
    String[] actual = readFile(expectedFile);
    String[] expected = readFile(actualFile);

    Assert.assertArrayEquals(expected, actual);
  }

  private String[] readFile(String filename) throws IOException {
    return Files.lines(Paths.get(filename)).toArray(String[]::new);
  }
}
