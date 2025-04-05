package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class PullRequestReviewThreadEventTest {

  @Test
  public void pullRequestReviewThread_whenComparedToSamePullRequest_isSimilar() {
    JsonNode pull = TestUtils.getMockPullRequesNodeOfTitle("PullRequestReviewThreadEvent", "mockTitle");
    PullRequestReviewThreadEvent event1 = new PullRequestReviewThreadEvent(pull);
    PullRequestReviewThreadEvent event2 = new PullRequestReviewThreadEvent(pull);

    assertEquals(true, event1.isSimilar(event2));
  }

  @Test
  public void pullRequestReviewThread_whenComparedToDifferentPullRequest_isNotSimilar() {
    JsonNode pull = TestUtils.getMockPullRequesNodeOfTitle("PullRequestReviewThreadEvent", "mockTitle");
    JsonNode pull2 = TestUtils.getMockPullRequesNodeOfTitle("PullRequestReviewThreadEvent", "mockTitle2");
    PullRequestReviewThreadEvent event1 = new PullRequestReviewThreadEvent(pull);
    PullRequestReviewThreadEvent event2 = new PullRequestReviewThreadEvent(pull2);

    assertEquals(false, event1.isSimilar(event2));
  }

  @Test
  public void pullRequestReviewThread_whenComparedToDifferentRepo_isNotSimilar() {
    JsonNode pull = TestUtils.getMockNodeOfRepo("PullRequestReviewThreadEvent", "repo");
    JsonNode pull2 = TestUtils.getMockNodeOfRepo("PullRequestReviewThreadEvent", "repo2");
    PullRequestReviewThreadEvent event1 = new PullRequestReviewThreadEvent(pull);
    PullRequestReviewThreadEvent event2 = new PullRequestReviewThreadEvent(pull2);

    assertEquals(false, event1.isSimilar(event2));
  }
}
