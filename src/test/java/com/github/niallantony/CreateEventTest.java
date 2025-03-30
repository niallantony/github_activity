package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class CreateEventTest {
  @Test
  public void CreateEvent_WhenComparedToSimilarEvent_ReturnsIsSimilar() {
    JsonNode event = TestUtils.getMockNode("CreateEvent");
    JsonNode event2 = TestUtils.getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(true, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void CreateEvent_WhenComparedToDifferentEvent_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("CreateEvent");
    JsonNode event2 = TestUtils.getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void CreateEvent_WhenComparedToDifferentRepo_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent2.setRepo("anotherRepo");

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }
}
