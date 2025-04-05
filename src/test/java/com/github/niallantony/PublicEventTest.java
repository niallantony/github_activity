package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class PublicEventTest {
  @Test
  public void publicEvent_whenComparedToSameRepoEvent_isSimilar() {
    JsonNode node = TestUtils.getMockNodeOfRepo("PublicEvent", "mockRepo");
    PublicEvent event1 = new PublicEvent(node);
    PublicEvent event2 = new PublicEvent(node);
    assertEquals(true, event1.isSimilar(event2));
  }

  @Test
  public void publicEvent_whenComparedToDifferentRepoEvent_isNotSimilar() {
    JsonNode node = TestUtils.getMockNodeOfRepo("PublicEvent", "mockRepo");
    JsonNode node2 = TestUtils.getMockNodeOfRepo("PublicEvent", "mockRepo2");
    PublicEvent event1 = new PublicEvent(node);
    PublicEvent event2 = new PublicEvent(node2);
    assertEquals(false, event1.isSimilar(event2));
  }
}
