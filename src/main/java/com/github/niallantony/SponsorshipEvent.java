package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class SponsorshipEvent extends GitEvent {
  /**
   * TO-DO: Understand what a sponsorship event pertains to...
   * Receiving a sponsorship, sponsoring another, then what is the repo?
   */
  public SponsorshipEvent(JsonNode event) {
    super(event);
  }

  @Override
  public String toString() {
    return String.format("Sponsorship Event");
  }
}
