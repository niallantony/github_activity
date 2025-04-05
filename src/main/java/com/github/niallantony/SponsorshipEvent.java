package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class SponsorshipEvent extends GitEvent {
  public SponsorshipEvent(JsonNode event) {
    super(event);
    System.out.println(event.toPrettyString());
  }

  @Override
  public String toString() {
    return String.format("Sponsorship Event");
  }
}
