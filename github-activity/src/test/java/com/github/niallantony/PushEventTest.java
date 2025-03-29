package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class PushEventTest {

  @Test
  public void PushEvent_WhenComparedToSimilarEvent_ReturnsIsSimilar() {
    JsonNode event = TestUtils.getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);

    assertEquals(true, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void PushEvent_WhenComparedToDifferentEvent_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("PushEvent");
    JsonNode event2 = TestUtils.getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void PushEvent_WhenComparedToDifferentRepo_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent2.setRepo("anotherRepo");

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void PushEvent_WhenAggregatedToSimilarEvent_AggregatesSize() {
    JsonNode event = TestUtils.getMockNode("PushEvent");
    JsonNode event2 = TestUtils.getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent.aggregate(gitEvent2);

    assertEquals(6, gitEvent.getAggregationData());
    assertEquals("Pushed 6 commit(s) to mockRepo", gitEvent.toString());
  }

  @Test
  public void PushEvent_WhenAggregatedWithDifferentEvent_DoesNotAggregateSize() {
    JsonNode event = TestUtils.getMockNode("PushEvent");
    JsonNode event2 = TestUtils.getMockNode("PullEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent.aggregate(gitEvent2);

    assertEquals(3, gitEvent.getAggregationData());
    assertEquals("Pushed 3 commit(s) to mockRepo", gitEvent.toString());
  }

}
