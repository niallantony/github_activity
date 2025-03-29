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

  @Override
  public int getAggregationData() {
    return getRefCode(this.ref_type);
  }

  private int getRefCode(String ref_type) {
    switch (ref_type) {
      case "branch":
        return 1;
      case "tag":
        return 2;
      default:
        return 0;
    }

  }

  public void setRefType(String type) {
    this.ref_type = type;
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (this.repo_name.equals(other.getRepo()) && this.type.equals(other.getType())
        && getRefCode(this.ref_type) == other.getAggregationData()) {
      return true;
    }
    return false;
  }
}
