package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class ReleaseEvent extends GitEvent {
  private @Getter String release;

  public ReleaseEvent(JsonNode event) {
    super(event);

    this.release = this.payload.get("release").get("name").asText();
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != ReleaseEvent.class)
      return false;
    ReleaseEvent next = (ReleaseEvent) other;
    if (next.getRepo().equals(this.repo_name) && next.getRelease().equals(this.release)) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format("Published %s: %s", this.repo_name, this.release);
  }
}
