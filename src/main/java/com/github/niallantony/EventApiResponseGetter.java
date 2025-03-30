package com.github.niallantony;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

public class EventApiResponseGetter {
  private URI uri;

  public EventApiResponseGetter(String username) throws URISyntaxException {
    this.uri = new URI(String.format("https://api.github.com/users/%s/events", username));
  }

  public HttpResponse<String> getResponse() throws InterruptedException, IOException {
    HttpClient client = HttpClient.newBuilder().build();
    HttpRequest request = HttpRequest.newBuilder(this.uri).build();
    return client.send(request, BodyHandlers.ofString(Charset.defaultCharset()));
  }
}
