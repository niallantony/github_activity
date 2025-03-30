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

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getType().equals(this.type) && other.getRepo().equals(this.repo_name)) {
      return true;
    }
    return false;
  }

  @Override
  public int getAggregationData() {
    return this.size;
  }

  @Override
  public void aggregate(GitEvent other) {
    if (other.getType().equals(this.type)) {
      this.size = this.size + other.getAggregationData();
    }
  }

}
