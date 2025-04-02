package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class EventFactoryTest {

  @Test
  public void EventFactory_WhenGivenGenericType_ReturnsGitEvent() {
    JsonNode event = TestUtils.getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(GitEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenGenericType_ReturnsWellFormedGitEvent() {
    JsonNode event = TestUtils.getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("mockRepo", gitEvent.getRepo());
    assertEquals("GenericEvent", gitEvent.getType());
    assertEquals("Event mockId: GenericEvent created at mockDate", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenPushEvent_ReturnsPushEvent() {
    JsonNode event = TestUtils.getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(PushEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenPushEvent_ReturnsWellFormedPushEvent() {
    JsonNode event = TestUtils.getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Pushed 3 commit(s) to mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenCreateEvent_ReturnsCreateEvent() {
    JsonNode event = TestUtils.getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(CreateEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenCreateEvent_ReturnsWellFormedCreateEvent() {
    JsonNode event = TestUtils.getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Created mockRefType mockRef in mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenDeleteEvent_ReturnsDeleteEvent() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(DeleteEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenDeleteEvent_ReturnsWellFormedDeleteEvent() {
    JsonNode event = TestUtils.getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Deleted mockRefType mockRef in repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenPullRequestEvent_ReturnsPullRequestEvent() {
    JsonNode event = TestUtils.getMockNode("PullRequestEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(PullRequestEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenPullRequestEvent_ReturnsWellFormedPullRequestEvent() {
    JsonNode event = TestUtils.getMockNode("PullRequestEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Pull request action: mockAction branch 'mockTitle' in repo mockPullRepoName", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenIssuesEvent_ReturnsIssuesEvent() {
    JsonNode event = TestUtils.getMockNode("IssuesEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(IssuesEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenIssuesEvent_ReturnsWellFormedIssuesEvent() {
    JsonNode event = TestUtils.getMockNode("IssuesEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("MockAction issue on mockRepo: mockIssueTitle", gitEvent.toString());
  }
}
