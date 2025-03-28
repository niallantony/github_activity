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
  private ObjectMapper mapper = new ObjectMapper();
  private ArrayList<GitEvent> events = new ArrayList<>();
  private EventFactory factory = new EventFactory();
  private String serverResponseBody;
  private HttpClient client;

  public ActivityGetter(String username) {
    this.username = username;
    this.client = HttpClient.newBuilder().build();
  }

  public ActivityGetter(String username, HttpClient client) {
    this.username = username;
    this.client = client;
  }

  public ActivityGetter sendRequest() {
    try {
      var uri = new URI(String.format("https://api.github.com/users/%s/events", this.username));
      var request = HttpRequest.newBuilder(uri).build();

      var response = this.client.send(request, BodyHandlers.ofString(Charset.defaultCharset()));
      this.serverResponseBody = response.body();
      return this;
    } catch (IOException ex) {
      System.err.println(ex);
      return null;
    } catch (URISyntaxException ex) {
      System.err.println(ex);
      return null;
    } catch (InterruptedException ex) {
      System.err.println(ex);
      return null;
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

  public ActivityGetter json() {
    JsonNode root = getJsonRoot(this.serverResponseBody);
    if (root.isArray()) {
      for (int i = 0; i < root.size(); i++) {
        GitEvent event = factory.create(root.get(i));
        this.events.add(event);
      }
    }
    return this;
  }

  public ArrayList<String> getActivity(int limit) {
    ArrayList<String> activity = new ArrayList<>();
    for (int i = 0; i < limit; i++) {
      if (this.events.size() > i) {
        GitEvent event = this.events.get(i);
        activity.add(event.toString() + "\n");
      } else {
        break;
      }
    }
    return activity;
  }

  public ArrayList<String> getAggregatedActivity(int limit) {
    ArrayList<String> activity = new ArrayList<>();
    try {
      ArrayList<GitEvent> aggregated = EventAggregator.aggregate(this.events);
      for (int i = 0; i < limit; i++) {
        if (this.events.size() > i) {
          GitEvent event = aggregated.get(i);
          activity.add(event.toString());
        } else {
          break;
        }
      }
      return activity;
    } catch (IndexOutOfBoundsException ex) {
      activity.add("No Activity Found");
      return activity;
    }

  }
}
