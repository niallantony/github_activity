package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class DeleteEvent extends GitEvent {
  private String ref_type;
  private String ref;

  public DeleteEvent(JsonNode event) {
    super(event);
    this.ref_type = this.payload.get("ref_type").asText();
    this.ref = this.payload.get("ref").asText();
  }

  @Override
  public String toString() {
    return String.format("Deleted %s %s in repo: %s", this.ref_type, this.ref, this.repo_name);
  }
}
