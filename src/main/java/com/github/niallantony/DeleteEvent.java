package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class DeleteEvent extends GitEvent {
  private @Getter String refType;
  private String ref;

  public DeleteEvent(JsonNode event) {
    super(event);
    this.refType = this.payload.get("ref_type").asText();
    this.ref = this.payload.get("ref").asText();
  }

  @Override
  public String toString() {
    if (this.aggregations == 1) {
      return String.format("Deleted %s %s in repo: %s", this.refType, this.ref, this.repo_name);
    }
    return String.format("Deleted %d %ss in repo: %s", this.aggregations, this.refType, this.repo_name);
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != DeleteEvent.class)
      return false;
    DeleteEvent previous = (DeleteEvent) other;
    if (this.repo_name.equals(other.getRepo())
        && getRefType() == previous.getRefType()) {
      return true;
    }
    return false;
  }
}
