package com.rememberjava.junit;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class MockitoTest {

  class WebGet {

    private final OutputStream out;

    WebGet(OutputStream out) {
      this.out = out;
    }

    void download(URL url) throws IOException {
      try (InputStream in = url.openStream()) {
        IOUtils.copy(in, out);
      }
    }
  }

  @Mock
  private OutputStream mockOut;
  
  @Mock
  private URL mockUrl;

  @Before
  public void setup() {
    MockitoAnnotations.initMocks(this);
  }

  @Test
  public void testMocked() throws IOException {
    WebGet wget = new WebGet(mockOut);
    wget.download(mockUrl);
  }
}
