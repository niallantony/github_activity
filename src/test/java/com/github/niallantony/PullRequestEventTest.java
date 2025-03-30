package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class PullRequestEventTest {
  @Test
  public void PullRequestEvent_WhenComparedToSimilarEvent_ReturnsSimilar() {
    JsonNode event = TestUtils.getMockNodeOfAction("PullRequestEvent", "opened");
    JsonNode event2 = TestUtils.getMockNodeOfAction("PullRequestEvent", "closed");
    EventFactory factory = new EventFactory();
    PullRequestEvent gitEvent = (PullRequestEvent) factory.create(event);
    PullRequestEvent gitEvent2 = (PullRequestEvent) factory.create(event2);

    assertEquals(true, gitEvent2.isSimilar(gitEvent));
  }

  @Test
  public void PullRequestEvent_WhenComparedToDifferentEvent_ReturnsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("PullRequestEvent");
    JsonNode event2 = TestUtils.getMockNodeOfRepo("PullRequestEvent", "anotherRepo");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent2.setRepo("otherRepo");

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }
}
