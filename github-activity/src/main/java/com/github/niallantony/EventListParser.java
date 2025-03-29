package com.github.niallantony;

import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EventListParser {
  private ObjectMapper mapper = new ObjectMapper();
  private ArrayList<GitEvent> events = new ArrayList<>();
  private EventFactory factory = new EventFactory();

  public EventListParser(String body) {
    this.events = extractEvents(body);
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

  private ArrayList<GitEvent> extractEvents(String body) {
    ArrayList<GitEvent> list = new ArrayList<>();
    JsonNode root = getJsonRoot(body);
    if (root.isArray()) {
      for (int i = 0; i < root.size(); i++) {
        GitEvent event = factory.create(root.get(i));
        list.add(event);
      }
    }
    return list;
  }

  public ArrayList<GitEvent> getEvents() {
    return this.events;
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
