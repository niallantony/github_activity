package com.github.niallantony;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.Charset;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ActivityGetter {
  private String username;
  private ActivityPrinter printer;
  private ObjectMapper mapper = new ObjectMapper();
  private ArrayList<GitEvent> events = new ArrayList<>();
  private EventFactory factory = new EventFactory();

  public ActivityGetter(
      String username) {
    this.username = username;
    this.printer = new CommandLinePrinter();
  }

  public ActivityGetter(String username, ActivityPrinter printer) {
    this.username = username;
    this.printer = printer;
  }

  public void getActivity() {
    try {
      var client = HttpClient.newBuilder().build();
      var uri = new URI(String.format("https://api.github.com/users/%s/events", this.username));
      var request = HttpRequest.newBuilder(uri).build();

      var response = client.send(request, BodyHandlers.ofString(Charset.defaultCharset()));
      var body = response.body();
      JsonNode root = getJsonRoot(body);
      unpackEventList(root);
    } catch (IOException ex) {
      System.err.println(ex);
    } catch (URISyntaxException ex) {
      System.err.println(ex);
    } catch (InterruptedException ex) {
      System.err.println(ex);
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
        GitEvent event = factory.create(root.get(i));
        this.events.add(event);
      }
    }
  }

  public void showActivity(int limit) {
    printer.print("Done");
    for (int i = 0; i < limit; i++) {
      if (this.events.size() > i) {
        GitEvent event = this.events.get(i);
        printer.print("- " + event.toString());
      } else {
        break;
      }
    }
  }
}
