package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class EventListParserTest {

  @Test
  public void parse_WhenGivenWellFormedResponse_ReturnsList() {
    try {
      String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<GitEvent> events = parser.getEvents();
      assertEquals(1, events.size());
      assertEquals(GitEvent.class, events.get(0).getClass());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void parse_WhenGivenTwoEvents_ReturnsTwoEvents() {
    String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}, {\"id\":\"idvalue2\",\"type\":\"typevalue2\",\"actor\":{\"display_login\":\"display_loginvalue2\"},\"repo\":{\"name\":\"repovalue2\"},\"payload\":\"payloadvalue2\",\"public\":true,\"created_at\":\"createdatvalue2\"}]";
    try {

      EventListParser parser = new EventListParser(mockBody);
      ArrayList<GitEvent> events = parser.getEvents();
      assertEquals(2, events.size());
      assertEquals(GitEvent.class, events.get(0).getClass());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void parse_WhenGivenEmptyList_ReturnsEmptyList() {
    try {
      EventListParser parser = new EventListParser("[]");
      ArrayList<GitEvent> events = parser.getEvents();
      assertEquals(0, events.size());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void parse_WhenGivenMisformedResponse_ThrowsIOException() {
    Exception exception = assertThrows(IOException.class, () -> {
      new EventListParser("<>");
    });
    assertEquals("Unable to read response: <>", exception.getMessage());

  }

  @Test
  public void getActivity_WhenGiventOneEvent_ReturnsCorrectString() {
    try {
      String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<String> events = parser.getActivity(1);
      assertEquals(1, events.size());
      assertEquals("Event idvalue1: typevalue1 created at createdatvalue1", events.get(0).toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void getActivity_WhenGivenTwoEvents_ReturnsTwoCorrectStrings() {
    try {
      String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}, {\"id\":\"idvalue2\",\"type\":\"typevalue2\",\"actor\":{\"display_login\":\"display_loginvalue2\"},\"repo\":{\"name\":\"repovalue2\"},\"payload\":\"payloadvalue2\",\"public\":true,\"created_at\":\"createdatvalue2\"}]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<String> events = parser.getActivity(2);
      assertEquals(2, events.size());
      assertEquals("Event idvalue1: typevalue1 created at createdatvalue1", events.get(0).toString());
      assertEquals("Event idvalue2: typevalue2 created at createdatvalue2", events.get(1).toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void getActivity_WhenAskedToReturnOneString_ReturnsOneStringOnly() {
    try {
      String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}, {\"id\":\"idvalue2\",\"type\":\"typevalue2\",\"actor\":{\"display_login\":\"display_loginvalue2\"},\"repo\":{\"name\":\"repovalue2\"},\"payload\":\"payloadvalue2\",\"public\":true,\"created_at\":\"createdatvalue2\"}]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<String> events = parser.getActivity(1);
      assertEquals(1, events.size());
      assertEquals("Event idvalue1: typevalue1 created at createdatvalue1", events.get(0).toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void getActivity_WhenAskedToReturnMoreStringsThanAvailable_ReturnsOnlyAvailableStrings() {
    try {
      String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}, {\"id\":\"idvalue2\",\"type\":\"typevalue2\",\"actor\":{\"display_login\":\"display_loginvalue2\"},\"repo\":{\"name\":\"repovalue2\"},\"payload\":\"payloadvalue2\",\"public\":true,\"created_at\":\"createdatvalue2\"}]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<String> events = parser.getActivity(3);
      assertEquals(2, events.size());
      assertEquals("Event idvalue1: typevalue1 created at createdatvalue1", events.get(0).toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void getAggregatedActivity_WhenGiven2Events_Returns2Events() {
    try {
      String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}, {\"id\":\"idvalue2\",\"type\":\"typevalue2\",\"actor\":{\"display_login\":\"display_loginvalue2\"},\"repo\":{\"name\":\"repovalue2\"},\"payload\":\"payloadvalue2\",\"public\":true,\"created_at\":\"createdatvalue2\"}]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<String> events = parser.getAggregatedActivity(2);
      assertEquals(2, events.size());
      assertEquals("Event idvalue1: typevalue1 created at createdatvalue1", events.get(0).toString());
      assertEquals("Event idvalue2: typevalue2 created at createdatvalue2", events.get(1).toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void getAggregatedActivity_WhenGiven2SimilarEvents_Returns1Event() {
    try {
      String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}, {\"id\":\"idvalue2\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue2\"},\"repo\":{\"name\":\"repovalue2\"},\"payload\":\"payloadvalue2\",\"public\":true,\"created_at\":\"createdatvalue2\"}]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<String> events = parser.getAggregatedActivity(2);
      assertEquals(1, events.size());
      assertEquals("2 typevalue1 events created at createdatvalue1", events.get(0).toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void getAggregatedActivity_WhenGivenEmptyList_ReturnsNoneFoundMessage() {
    try {
      String mockBody = "[]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<String> events = parser.getAggregatedActivity(2);
      assertEquals(1, events.size());
      assertEquals("No Activity Found", events.get(0).toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }

  @Test
  public void getAggregatedActivity_WhenGivenOneDistinctAndTwoSimilarEvents_ReturnsTwoEvents() {
    try {
      String mockBody = "[{\"id\":\"idvalue1\",\"type\":\"typevalue1\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}, {\"id\":\"idvalue2\",\"type\":\"typevalue2\",\"actor\":{\"display_login\":\"display_loginvalue2\"},\"repo\":{\"name\":\"repovalue2\"},\"payload\":\"payloadvalue2\",\"public\":true,\"created_at\":\"createdatvalue2\"},{\"id\":\"idvalue1\",\"type\":\"typevalue2\",\"actor\":{\"display_login\":\"display_loginvalue1\"},\"repo\":{\"name\":\"repovalue1\"},\"payload\":\"payloadvalue1\",\"public\":true,\"created_at\":\"createdatvalue1\"}]";
      EventListParser parser = new EventListParser(mockBody);
      ArrayList<String> events = parser.getAggregatedActivity(2);
      assertEquals(2, events.size());
      assertEquals("Event idvalue1: typevalue1 created at createdatvalue1", events.get(0).toString());
      assertEquals("2 typevalue2 events created at createdatvalue2", events.get(1).toString());
    } catch (Exception ex) {
      System.err.println(ex);
    }
  }
}
