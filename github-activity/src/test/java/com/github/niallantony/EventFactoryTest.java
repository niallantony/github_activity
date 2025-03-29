package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class EventFactoryTest {

  private JsonNode getMockNode(String type) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode mockGitEvent = mapper.createObjectNode();
    mockGitEvent.put("id", "mockId");
    mockGitEvent.put("type", type);
    mockGitEvent.put("public", true);
    mockGitEvent.put("created_at", "mockDate");
    ObjectNode actorNode = mapper.createObjectNode();
    actorNode.put("display_login", "mockUser");
    mockGitEvent.set("actor", actorNode);
    ObjectNode repoNode = mapper.createObjectNode();
    repoNode.put("name", "mockRepo");
    mockGitEvent.set("repo", repoNode);
    ObjectNode payloadNode = mapper.createObjectNode();
    payloadNode.put("size", 3);
    payloadNode.put("description", "mockDescription");
    payloadNode.put("ref", "mockRef");
    payloadNode.put("ref_type", "mockRefType");
    payloadNode.put("action", "mockAction");
    ObjectNode pullRequestNode = mapper.createObjectNode();
    pullRequestNode.put("title", "mockTitle");
    ObjectNode headNode = mapper.createObjectNode();
    ObjectNode headRepoNode = mapper.createObjectNode();
    headRepoNode.put("full_name", "mockPullRepoName");
    headNode.set("repo", headRepoNode);
    pullRequestNode.set("head", headNode);
    payloadNode.set("pull_request", pullRequestNode);
    ObjectNode issueNode = mapper.createObjectNode();
    issueNode.put("title", "mockIssueTitle");
    payloadNode.set("issue", issueNode);
    mockGitEvent.set("payload", payloadNode);

    try {

      String json = mapper.writer().writeValueAsString(mockGitEvent);
      JsonNode root = mapper.readTree(json);
      return root;
    } catch (Exception ex) {
      System.err.println(ex);
      return null;
    }
  }

  @Test
  public void EventFactory_WhenGivenGenericType_ReturnsGitEvent() {
    JsonNode event = getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(GitEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenGenericType_ReturnsWellFormedGitEvent() {
    JsonNode event = getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("mockRepo", gitEvent.getRepo());
    assertEquals("GenericEvent", gitEvent.getType());
    assertEquals(0, gitEvent.getAggregationData());
    assertEquals("Event mockId: GenericEvent created at mockDate", gitEvent.toString());
  }

  @Test
  public void GitEvent_WhenAggregated_ReturnsCorrectString() {
    JsonNode event = getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent.aggregate(gitEvent2);
    gitEvent.aggregate(gitEvent2);

    assertEquals("3 GenericEvent events created at mockDate", gitEvent.toString());
  }

  @Test
  public void GitEvent_WhenAggregatedWithDifferentEvent_DoesNotAggregate() {
    JsonNode event = getMockNode("GenericEvent");
    JsonNode event2 = getMockNode("AnotherEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent.aggregate(gitEvent2);
    gitEvent.aggregate(gitEvent2);

    assertEquals("Event mockId: GenericEvent created at mockDate", gitEvent.toString());
  }

  @Test
  public void GitEvent_WhenComparedToSimilarGitEvent_ReturnsTrue() {
    JsonNode event = getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);

    assertEquals(true, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void GitEvent_WhenComparedToDifferentGitEvent_ReturnsFalse() {
    JsonNode event = getMockNode("GenericEvent");
    JsonNode event2 = getMockNode("AnotherEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void EventFactory_WhenGivenPushEvent_ReturnsPushEvent() {
    JsonNode event = getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(PushEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenPushEvent_ReturnsWellFormedPushEvent() {
    JsonNode event = getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(3, gitEvent.getAggregationData());
    assertEquals("Pushed 3 commit(s) to mockRepo", gitEvent.toString());
  }

  @Test
  public void PushEvent_WhenComparedToSimilarEvent_ReturnsIsSimilar() {
    JsonNode event = getMockNode("PushEvent");
    JsonNode event2 = getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(true, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void PushEvent_WhenComparedToDifferentEvent_ReturnsIsNotSimilar() {
    JsonNode event = getMockNode("PushEvent");
    JsonNode event2 = getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void PushEvent_WhenComparedToDifferentRepo_ReturnsIsNotSimilar() {
    JsonNode event = getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent2.setRepo("anotherRepo");

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void PushEvent_WhenAggregatedToSimilarEvent_AggregatesSize() {
    JsonNode event = getMockNode("PushEvent");
    JsonNode event2 = getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent.aggregate(gitEvent2);

    assertEquals(6, gitEvent.getAggregationData());
    assertEquals("Pushed 6 commit(s) to mockRepo", gitEvent.toString());
  }

  @Test
  public void PushEvent_WhenAggregatedWithDifferentEvent_DoesNotAggregateSize() {
    JsonNode event = getMockNode("PushEvent");
    JsonNode event2 = getMockNode("PullEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);
    gitEvent.aggregate(gitEvent2);

    assertEquals(3, gitEvent.getAggregationData());
    assertEquals("Pushed 3 commit(s) to mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenCreateEvent_ReturnsCreateEvent() {
    JsonNode event = getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(CreateEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenCreateEvent_ReturnsWellFormedCreateEvent() {
    JsonNode event = getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Created 'mockRepo': mockDescription", gitEvent.toString());
  }

  @Test
  public void CreateEvent_WhenComparedToSimilarEvent_ReturnsIsSimilar() {
    JsonNode event = getMockNode("CreateEvent");
    JsonNode event2 = getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(true, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void CreateEvent_WhenComparedToDifferentEvent_ReturnsIsNotSimilar() {
    JsonNode event = getMockNode("CreateEvent");
    JsonNode event2 = getMockNode("GenericEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void CreateEvent_WhenComparedToDifferentRepo_ReturnsIsNotSimilar() {
    JsonNode event = getMockNode("CreateEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent2.setRepo("anotherRepo");

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void EventFactory_WhenGivenDeleteEvent_ReturnsDeleteEvent() {
    JsonNode event = getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(DeleteEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenDeleteEvent_ReturnsWellFormedDeleteEvent() {
    JsonNode event = getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Deleted mockRefType mockRef in repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void DeleteEvent_WhenComparedToDifferentEvent_ReturnsIsNotSimilar() {
    JsonNode event = getMockNode("DeleteEvent");
    JsonNode event2 = getMockNode("PushEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event2);

    assertEquals(false, gitEvent.isSimilar(gitEvent2));

  }

  @Test
  public void DeleteEvent_WhenComparedToDifferentRepo_ReturnsIsNotSimilar() {
    JsonNode event = getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent2.setRepo("anotherRepo");

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void DeleteEvent_WhenComparedToDifferentRefType_ReturnsIsNotSimilar() {
    JsonNode event = getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    DeleteEvent gitEvent2 = (DeleteEvent) factory.create(event);
    gitEvent2.setRefType("tag");

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void DeleteEvent_WhenComparedToSimilarEvent_ReturnsSimilar() {
    JsonNode event = getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);

    assertEquals(true, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void DeleteEvent_WhenAggregatedWithSimilarEvent_AggregatesEvents() {
    JsonNode event = getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent.aggregate(gitEvent2);

    assertEquals("Deleted 2 mockRefTypes in repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void DeleteEvent_WhenAggregatedWithDifferentRepo_DoesNotAggregateEvents() {
    JsonNode event = getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent2.setRepo("otherRepo");
    gitEvent.aggregate(gitEvent2);

    assertEquals("Deleted mockRefType mockRef in repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void DeleteEvent_WhenAggregatedWithDifferentRefType_DoesNotAggregateEvents() {
    JsonNode event = getMockNode("DeleteEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    DeleteEvent gitEvent2 = (DeleteEvent) factory.create(event);
    gitEvent2.setRefType("tag");
    gitEvent.aggregate(gitEvent2);

    assertEquals("Deleted mockRefType mockRef in repo: mockRepo", gitEvent.toString());
  }

  @Test
  public void EventFactory_WhenGivenPullRequestEvent_ReturnsPullRequestEvent() {
    JsonNode event = getMockNode("PullRequestEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(PullRequestEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenPullRequestEvent_ReturnsWellFormedPullRequestEvent() {
    JsonNode event = getMockNode("PullRequestEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("Pull request action: mockAction, branch 'mockTitle' in repo mockPullRepoName", gitEvent.toString());
  }

  @Test
  public void PullRequestEvent_WhenComparedToSimilarEvent_ReturnsSimilar() {
    JsonNode event = getMockNode("PullRequestEvent");
    EventFactory factory = new EventFactory();
    PullRequestEvent gitEvent = (PullRequestEvent) factory.create(event);
    PullRequestEvent gitEvent2 = (PullRequestEvent) factory.create(event);
    gitEvent.setAction("opened");
    gitEvent2.setAction("closed");

    assertEquals(true, gitEvent2.isSimilar(gitEvent));
  }

  @Test
  public void PullRequestEvent_WhenComparedToDifferentEvent_ReturnsNotSimilar() {
    JsonNode event = getMockNode("PullRequestEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);
    GitEvent gitEvent2 = factory.create(event);
    gitEvent2.setRepo("otherRepo");

    assertEquals(false, gitEvent.isSimilar(gitEvent2));
  }

  @Test
  public void EventFactory_WhenGivenIssuesEvent_ReturnsIssuesEvent() {
    JsonNode event = getMockNode("IssuesEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals(IssuesEvent.class, gitEvent.getClass());
  }

  @Test
  public void EventFactory_WhenGivenIssuesEvent_ReturnsWellFormedIssuesEvent() {
    JsonNode event = getMockNode("IssuesEvent");
    EventFactory factory = new EventFactory();
    GitEvent gitEvent = factory.create(event);

    assertEquals("mockAction issue on mockRepo: mockIssueTitle", gitEvent.toString());
  }
}
