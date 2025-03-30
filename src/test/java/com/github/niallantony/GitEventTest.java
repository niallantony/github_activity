package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class GitEventTest {

  @Test
  public void GitEvent_WhenAggregated_ReturnsCorrectString() {
    JsonNode event = TestUtils.getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent.aggregate(gitEvent2);
    gitEvent.aggregate(gitEvent2);

    assertEquals("3 GenericEvent events created at mockDate", gitEvent.toString());
  }

  @Test
  public void GitEvent_WhenAggregatedWithDifferentEvent_DoesNotAggregate() {
    JsonNode event = TestUtils.getMockNode("GenericEvent");
    JsonNode event2 = TestUtils.getMockNode("AnotherEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent.aggregate(gitEvent2);
    gitEvent.aggregate(gitEvent2);

    assertEquals("Event mockId: GenericEvent created at mockDate", gitEvent.toString());
  }

  @Test
  public void GitEvent_WhenComparedToSimilarGitEvent_ReturnsTrue() {
    JsonNode event = TestUtils.getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);

    assertEquals(true, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void GitEvent_WhenComparedToDifferentGitEvent_ReturnsFalse() {
    JsonNode event = TestUtils.getMockNode("GenericEvent");
    JsonNode event2 = TestUtils.getMockNode("AnotherEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }
}
