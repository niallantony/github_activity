package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class IssueCommentEventTest {
  @Test
  public void issueComment_WhenComparedToIdenticalIssueComment_IsSimilar() {
    JsonNode node = TestUtils.getMockIssueCommentNode("mockRepo", "mockAction", "mockIssue");
    IssueCommentEvent comment1 = new IssueCommentEvent(node);
    IssueCommentEvent comment2 = new IssueCommentEvent(node);

    assertEquals(true, comment1.isSimilar(comment2));
  }

  @Test
  public void issueComment_WhenComparedToDifferentRepoIssueComment_IsNotSimilar() {
    JsonNode node = TestUtils.getMockIssueCommentNode("mockRepo", "mockAction", "mockIssue");
    JsonNode node2 = TestUtils.getMockIssueCommentNode("mockRepo2", "mockAction", "mockIssue");
    IssueCommentEvent comment1 = new IssueCommentEvent(node);
    IssueCommentEvent comment2 = new IssueCommentEvent(node2);

    assertEquals(false, comment1.isSimilar(comment2));
  }

  @Test
  public void issueComment_WhenComparedToDifferentIssueComment_IsNotSimilar() {
    JsonNode node = TestUtils.getMockIssueCommentNode("mockRepo", "mockAction", "mockIssue");
    JsonNode node2 = TestUtils.getMockIssueCommentNode("mockRepo", "mockAction", "mockIssue2");
    IssueCommentEvent comment1 = new IssueCommentEvent(node);
    IssueCommentEvent comment2 = new IssueCommentEvent(node2);

    assertEquals(false, comment1.isSimilar(comment2));
  }

  @Test
  public void issueComment_WhenComparedToDifferentActionIssueComment_IsNotSimilar() {
    JsonNode node = TestUtils.getMockIssueCommentNode("mockRepo", "mockAction", "mockIssue");
    JsonNode node2 = TestUtils.getMockIssueCommentNode("mockRepo", "mockAction2", "mockIssue");
    IssueCommentEvent comment1 = new IssueCommentEvent(node);
    IssueCommentEvent comment2 = new IssueCommentEvent(node2);

    assertEquals(false, comment1.isSimilar(comment2));
  }

  @Test
  public void issueComment_WithActionDeleted_ShouldBeIgnored() {
    JsonNode node = TestUtils.getMockIssueCommentNode("mockRepo", "deleted", "mockIssue");
    IssueCommentEvent comment = new IssueCommentEvent(node);

    assertEquals(true, comment.shouldIgnore());
  }

  @Test
  public void issueComment_WithActionEdited_ShouldBeIgnored() {
    JsonNode node = TestUtils.getMockIssueCommentNode("mockRepo", "edited", "mockIssue");
    IssueCommentEvent comment = new IssueCommentEvent(node);

    assertEquals(true, comment.shouldIgnore());
  }

  @Test
  public void issueComment_WhenAggregated_ShouldReturnCorrectString() {
    JsonNode node = TestUtils.getMockIssueCommentNode("mockRepo", "mockAction", "mockIssue");
    IssueCommentEvent comment = new IssueCommentEvent(node);
    comment.aggregate(comment);

    assertEquals("MockAction 2 comments on issue: mockIssue (mockRepo)", comment.toString());
  }
}
