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
    if (this.aggregations == 1) {
      return String.format("Deleted %s %s in repo: %s", this.ref_type, this.ref, this.repo_name);
    }
    return String.format("Deleted %d %ss in repo: %s", this.aggregations, this.ref_type, this.repo_name);
  }

  public String getRefCode() {
    return this.ref_type;
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != DeleteEvent.class)
      return false;
    DeleteEvent previous = (DeleteEvent) other;
    if (this.repo_name.equals(other.getRepo())
        && getRefCode() == previous.getRefCode()) {
      return true;
    }
    return false;
  }
}
