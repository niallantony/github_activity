package com.github.niallantony;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventListParser {
  private ObjectMapper mapper = new ObjectMapper();
  private ArrayList<GitEvent> events = new ArrayList<>();
  private EventFactory factory = new EventFactory();

  public EventListParser(String body) throws IOException {
    this.events = extractEvents(body);
  }

  private ArrayList<GitEvent> extractEvents(String body) throws IOException {
    try {
      ArrayList<GitEvent> list = new ArrayList<>();
      JsonNode root = this.mapper.readTree(body);
      if (root.isArray()) {
        for (int i = 0; i < root.size(); i++) {
          GitEvent event = factory.create(root.get(i));
          list.add(event);
        }
      }
      return list;
    } catch (JsonProcessingException ex) {
      throw new IOException(String.format("Unable to read response: %s", body));
    }
  }

  public ArrayList<GitEvent> getEvents() {
    return this.events;
  }

  public ArrayList<String> getActivity(int limit) {
    ArrayList<String> activity = new ArrayList<>();
    for (int i = 0; i < limit; i++) {
      if (this.events.size() > i) {
        GitEvent event = this.events.get(i);
        activity.add(event.toString());
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
      for (int i = 0; i < aggregated.size(); i++) {
        if (aggregated.size() > i) {
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
