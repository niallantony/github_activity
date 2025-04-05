package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class PullRequestReviewEventTest {
  @Test
  public void pullRequestReview_whenComparedToSimilarEvent_isSimilar() {
    JsonNode pullreview = TestUtils.getMockNode("PullRequestReviewEvent");
    PullRequestReviewEvent event1 = new PullRequestReviewEvent(pullreview);
    PullRequestReviewEvent event2 = new PullRequestReviewEvent(pullreview);

    assertEquals(true, event1.isSimilar(event2));
  }

  @Test
  public void pullRequestReview_whenComparedToDifferentRepoEvent_isSimilar() {
    JsonNode pullreview = TestUtils.getMockPullRequesNodeOfTitle("PullRequestReviewEvent", "mockTitle1");
    JsonNode pullreview2 = TestUtils.getMockPullRequesNodeOfTitle("PullRequestReviewEvent", "mockTitle2");
    PullRequestReviewEvent event1 = new PullRequestReviewEvent(pullreview);
    PullRequestReviewEvent event2 = new PullRequestReviewEvent(pullreview2);

    assertEquals(false, event1.isSimilar(event2));
  }
}
