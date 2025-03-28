package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class CreateEvent extends GitEvent {

  private String desc;

  public CreateEvent(JsonNode event) {
    super(event);
    this.desc = this.payload.get("description").asText();
  }

  @Override
  public String toString() {
    return String.format("Created '%s': %s", this.repo_name, this.desc);
  }
}
