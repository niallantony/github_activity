package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class DeleteEventTest {

  @Test
  public void DeleteEvent_WhenComparedToDifferentEvent_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    JsonNode event2 = TestUtils.getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));

  }

  @Test
  public void DeleteEvent_WhenComparedToDifferentRepo_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    JsonNode event2 = TestUtils.getMockNodeOfRepo("DeleteEvent", "anotherRepo");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void DeleteEvent_WhenComparedToDifferentRefType_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    JsonNode event2 = TestUtils.getMockNodeOfRefType("DeleteEvent", "tag");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    DeleteEvent gitEvent2 = (DeleteEvent) factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void DeleteEvent_WhenComparedToSimilarEvent_ReturnsSimilar() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);

    assertEquals(true, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void DeleteEvent_WhenAggregatedWithSimilarEvent_AggregatesEvents() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent.aggregate(gitEvent2);

    assertEquals("Deleted 2 mockRefTypes in repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void DeleteEvent_WhenAggregatedWithDifferentRepo_DoesNotAggregateEvents() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    JsonNode event2 = TestUtils.getMockNodeOfRepo("DeleteEvent", "anotherRepo");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent.aggregate(gitEvent2);

    assertEquals("Deleted mockRefType mockRef in repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void DeleteEvent_WhenAggregatedWithDifferentRefType_DoesNotAggregateEvents() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    JsonNode event2 = TestUtils.getMockNodeOfRefType("DeleteEvent", "tag");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent.aggregate(gitEvent2);

    assertEquals("Deleted mockRefType mockRef in repo: mockRepo", gitEvent.toString());
  }
}
