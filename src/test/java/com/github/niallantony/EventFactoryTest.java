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

  @Test
  public void EventFactory_WhenGivenCommitCommentEvent_ReturnsCommitCommentEvent() {
    JsonNode event = TestUtils.getMockCommitComment("mockComment");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(CommitCommentEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenCommitCommentEvent_ReturnsWellFormedCommitCommentEvent() {
    JsonNode event = TestUtils.getMockCommitComment("mockComment");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Made a comment on a commit in mockRepo. It said: mockComment", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenForkEvent_ReturnsForkEvent() {
    JsonNode event = TestUtils.getMockNode("ForkEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(ForkEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenForkEvent_ReturnsWellFormedForkEvent() {
    JsonNode event = TestUtils.getMockNode("ForkEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Forked a repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenGollumEvent_ReturnsGollumEvent() {
    JsonNode event = TestUtils.getMockGollumNode("mockRepo", 3);
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(GollumEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenGollumEvent_ReturnsWellFormedGollumEvent() {
    JsonNode event = TestUtils.getMockGollumNode("mockRepo", 3);
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Updated 3 pages on a Wiki in mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenWatchEvent_ReturnsWatchEvent() {
    JsonNode event = TestUtils.getMockNode("WatchEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(WatchEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenWatchEvent_ReturnsWellFormedWatchEvent() {
    JsonNode event = TestUtils.getMockNode("WatchEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Watched repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenIssueCommentEvent_ReturnsIssueCommentEvent() {
    JsonNode event = TestUtils.getMockNode("IssueCommentEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(IssueCommentEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenIssueCommentEvent_ReturnsWellFormedIssueCommentEvent() {
    JsonNode event = TestUtils.getMockNode("IssueCommentEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("MockAction a comment on issue: mockIssueTitle (mockRepo)", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenMemberEvent_ReturnsMemberEvent() {
    JsonNode event = TestUtils.getMockMemberEvent("mockUser");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(MemberEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenMemberEvent_ReturnsWellFormedMemberEvent() {
    JsonNode event = TestUtils.getMockMemberEvent("mockUser");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Added user mockUser to the repo mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenPublicEvent_ReturnsPublicEvent() {
    JsonNode event = TestUtils.getMockNode("PublicEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(PublicEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenPublicEvent_ReturnsWellFormedPublicEvent() {
    JsonNode event = TestUtils.getMockNode("PublicEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Made repo 'mockRepo' public", gitEvent.toString());
  }
}
