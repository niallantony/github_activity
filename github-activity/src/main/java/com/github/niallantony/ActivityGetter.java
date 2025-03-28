package com.github.niallantony;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActivityGetter {
  private String username;
  private ActivityPrinter printer;
  private ObjectMapper mapper = new ObjectMapper();

  public ActivityGetter(
      String username) {
    this.username = username;
    this.printer = new CommandLinePrinter();
  }

  public ActivityGetter(String username, ActivityPrinter printer) {
    this.username = username;
    this.printer = printer;
  }

  private String getActivity() {
    try {
      var client = HttpClient.newBuilder().build();
      var uri = new URI(String.format("https://api.github.com/users/%s/events", this.username));
      var request = HttpRequest.newBuilder(uri).build();

      var response = client.send(request, BodyHandlers.ofString(Charset.defaultCharset()));
      var body = response.body();
      return body;
    } catch (IOException ex) {
      return ex.toString();
    } catch (URISyntaxException ex) {
      return ex.toString();
    } catch (InterruptedException ex) {
      return ex.toString();
    }
  }

  private JsonNode getJsonRoot(String body) {
    try {
      return this.mapper.readTree(body);

    } catch (JsonMappingException ex) {
      System.out.println(ex.toString());
      return null;
    } catch (JsonProcessingException ex) {
      System.out.println(ex.toString());
      return null;
    }
  }

  public void unpackEventList(JsonNode root) {
    if (root.isArray()) {
      for (int i = 0; i < root.size(); i++) {
        GitEvent event = new GitEvent(root.get(i));
        System.out.println(event.toString());
      }
    }
  }

  public void showActivity() {
    String activity = getActivity();
    JsonNode root = getJsonRoot(activity);
    unpackEventList(root);
    printer.print("Done");
  }
}
