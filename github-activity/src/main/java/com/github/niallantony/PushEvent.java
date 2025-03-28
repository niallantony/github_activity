package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class PushEvent extends GitEvent {
  private int size;

  public PushEvent(JsonNode event) {
    super(event);
    this.size = this.payload.get("size").asInt();
  }

  @Override
  public String toString() {
    return String.format("Pushed %d commit(s) to %s", this.size, this.repo_name);
  }
}
