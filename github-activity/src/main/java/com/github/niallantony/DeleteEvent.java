package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class DeleteEvent extends GitEvent {
  private int ref_type;

  public DeleteEvent(JsonNode event) {
    super(event);
    this.ref_type = this.payload.get("ref_type").asInt();
  }

  @Override
  public String toString() {
    return String.format("Deleted %s in repo: %s", this.ref_type, this.repo_name);
  }
}
