package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class ReleaseEventTest {

  @Test
  public void releaseEvent_WhenGivenIdenticalEvent_isSimilar() {
    JsonNode node = TestUtils.getMockReleaseNode("mockRepo", "mockRelease");
    ReleaseEvent event1 = new ReleaseEvent(node);
    ReleaseEvent event2 = new ReleaseEvent(node);

    assertEquals(true, event1.isSimilar(event2));
  }

  @Test
  public void releaseEvent_WhenGivenDifferentRelease_isNotSimilar() {
    JsonNode node = TestUtils.getMockReleaseNode("mockRepo", "mockRelease");
    JsonNode node2 = TestUtils.getMockReleaseNode("mockRepo", "mockRelease2");
    ReleaseEvent event1 = new ReleaseEvent(node);
    ReleaseEvent event2 = new ReleaseEvent(node2);

    assertEquals(false, event1.isSimilar(event2));
  }

  @Test
  public void releaseEvent_WhenGivenDifferentRepo_isNotSimilar() {
    JsonNode node = TestUtils.getMockReleaseNode("mockRepo", "mockRelease");
    JsonNode node2 = TestUtils.getMockReleaseNode("mockRepo2", "mockRelease");
    ReleaseEvent event1 = new ReleaseEvent(node);
    ReleaseEvent event2 = new ReleaseEvent(node2);

    assertEquals(false, event1.isSimilar(event2));
  }
}
