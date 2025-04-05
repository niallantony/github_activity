package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class PullRequestReviewCommentEventTest {
  @Test
  public void pullRequestReviewComment_whenComparedToSamePullRequest_isSimilar() {
    JsonNode pull = TestUtils.getMockPullRequesNodeOfTitle("PullRequestReviewCommentEvent", "mockTitle");
    PullRequestReviewCommentEvent event1 = new PullRequestReviewCommentEvent(pull);
    PullRequestReviewCommentEvent event2 = new PullRequestReviewCommentEvent(pull);

    assertEquals(true, event1.isSimilar(event2));
  }

  @Test
  public void pullRequestReviewComment_whenComparedToDifferentPullRequest_isNotSimilar() {
    JsonNode pull = TestUtils.getMockPullRequesNodeOfTitle("PullRequestReviewCommentEvent", "mockTitle");
    JsonNode pull2 = TestUtils.getMockPullRequesNodeOfTitle("PullRequestReviewCommentEvent", "mockTitle2");
    PullRequestReviewCommentEvent event1 = new PullRequestReviewCommentEvent(pull);
    PullRequestReviewCommentEvent event2 = new PullRequestReviewCommentEvent(pull2);

    assertEquals(false, event1.isSimilar(event2));
  }
}
