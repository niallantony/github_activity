package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class GollumEventTest {
  @Test
  public void gollumEvent_WhenGivenArrayOfPages_HasCorrectPageSize() {
    JsonNode gollumNode = TestUtils.getMockGollumNode("mockRepo", 3);
    GollumEvent gollum = new GollumEvent(gollumNode);

    assertEquals(3, gollum.getPages());
  }

  @Test
  public void gollumEvent_WhenComparedToGollumEventinSameRepo_IsSimilar() {
    JsonNode gollumNode = TestUtils.getMockGollumNode("mockRepo", 3);
    GollumEvent gollum1 = new GollumEvent(gollumNode);
    GollumEvent gollum2 = new GollumEvent(gollumNode);

    assertEquals(true, gollum1.isSimilar(gollum2));
  }

  @Test
  public void gollumEvent_WhenComparedToGollumEventOfDifferentRepo_IsNotSimilar() {
    JsonNode gollumNode = TestUtils.getMockGollumNode("mockRepo", 3);
    JsonNode gollumNode2 = TestUtils.getMockGollumNode("mockRepo2", 3);
    GollumEvent gollum1 = new GollumEvent(gollumNode);
    GollumEvent gollum2 = new GollumEvent(gollumNode2);

    assertEquals(false, gollum1.isSimilar(gollum2));
  }

  @Test
  public void gollumEvent_WhenAggregating_AddsSizesCorrectly() {
    JsonNode gollumNode = TestUtils.getMockGollumNode("mockRepo", 3);
    JsonNode gollumNode2 = TestUtils.getMockGollumNode("mockRepo", 2);
    GollumEvent gollum1 = new GollumEvent(gollumNode);
    GollumEvent gollum2 = new GollumEvent(gollumNode2);
    gollum1.aggregate(gollum2);

    assertEquals(5, gollum1.getPages());
  }
}
