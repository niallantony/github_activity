package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class MemberEventTest {
  @Test
  public void memberEvent_WhenGivenSameRepo_IsSimilar() {
    JsonNode node = TestUtils.getMockMemberEvent("user1");
    JsonNode node2 = TestUtils.getMockMemberEvent("user2");
    MemberEvent event1 = new MemberEvent(node);
    MemberEvent event2 = new MemberEvent(node2);

    assertEquals(true, event1.isSimilar(event2));
  }

  @Test
  public void memberEvent_WhenGivenDifferentRepo_IsNotSimilar() {
    JsonNode node = TestUtils.getMockMemberEvent("user1");
    JsonNode node2 = TestUtils.getMockMemberEventOfDifferentRepo("user2", "repo2");
    MemberEvent event1 = new MemberEvent(node);
    MemberEvent event2 = new MemberEvent(node2);

    assertEquals(false, event1.isSimilar(event2));
  }
}
