package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class CommitCommentEventTest {
  @Test
  public void CommitComment_WhenComparedToAnother_ReturnsFalse() {
    JsonNode commitNode = TestUtils.getMockCommitComment("mockComment");
    CommitCommentEvent event1 = new CommitCommentEvent(commitNode);
    CommitCommentEvent event2 = new CommitCommentEvent(commitNode);

    assertEquals(false, event1.isSimilar(event2));
  }

  @Test
  public void CommitComment_WhenAggregatedWithAnother_DoesNotAffectIt() {
    JsonNode commitNode = TestUtils.getMockCommitComment("mockComment");
    CommitCommentEvent event1 = new CommitCommentEvent(commitNode);
    CommitCommentEvent event2 = new CommitCommentEvent(commitNode);
    event1.aggregate(event2);

    assertEquals("Made a comment on a commit in mockRepo. It said: mockComment", event1.toString());
    assertEquals("Made a comment on a commit in mockRepo. It said: mockComment", event2.toString());
  }
}
