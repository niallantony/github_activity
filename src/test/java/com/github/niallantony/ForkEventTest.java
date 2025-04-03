package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class ForkEventTest {
  @Test
  public void forkEvent_WhenComparedToIdenticalEvent_IsSimilar() {
    JsonNode forkNode = TestUtils.getMockNode("ForkEvent");
    ForkEvent fork1 = new ForkEvent(forkNode);
    ForkEvent fork2 = new ForkEvent(forkNode);

    assertEquals(true, fork1.isSimilar(fork2));
  }
}
