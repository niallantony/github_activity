package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

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
  public void CreateEvent_WhenAskedForRefs_GivesRefArray() {
    JsonNode event = TestUtils.getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    CreateEvent gitEvent = (CreateEvent) factory.create(event);
    String refs = Arrays.toString(gitEvent.getRefs());

    assertEquals("[mockRef, mockRefType]", Arrays.toString(gitEvent.getRefs()));
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
    JsonNode event2 = TestUtils.getMockNodeOfRepo("CreateEvent", "anotherRepo");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void CreateEvent_WhenComparedToDifferentRef_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("CreateEvent");
    JsonNode event2 = TestUtils.getMockNodeOfRef("CreateEvent", "anotherRef");

    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void CreateEvent_WhenComparedToDifferentRefType_ReturnsIsNotSimilar() {
    JsonNode event = TestUtils.getMockNode("CreateEvent");
    JsonNode event2 = TestUtils.getMockNodeOfRefType("CreateEvent", "anotherRefType");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    CreateEvent gitEvent2 = (CreateEvent) factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void CreateEvent_WhenReferringToMainBranch_IsIgnored() {
    JsonNode event = TestUtils.getMockCreateMain();
    EventFactory factory = new EventFactory();
    CreateEvent gitEvent = (CreateEvent) factory.create(event);

    assertEquals(true, gitEvent.shouldIgnore());
  }

  @Test
  public void CreateEvent_WhenReferringToBranch_returnsCorrectString() {
    JsonNode event = TestUtils.getMockNodeOfRefType("CreateEvent", "branch");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Created branch mockRef in mockRepo", gitEvent.toString());
  }

  @Test
  public void CreateEvent_WhenReferringToRepo_returnsCorrectString() {
    JsonNode event = TestUtils.getMockNodeOfRefType("CreateEvent", "repository");
    EventFactory factory = new EventFactory();
    CreateEvent gitEvent = (CreateEvent) factory.create(event);

    assertEquals("Created 'mockRepo': mockDescription", gitEvent.toString());
  }
}
