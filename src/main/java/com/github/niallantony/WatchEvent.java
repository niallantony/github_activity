package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class WatchEvent extends GitEvent {
  public WatchEvent(JsonNode event) {
    super(event);
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != WatchEvent.class)
      return false;
    if (other.getRepo().equals(this.repo_name))
      return true;
    return false;
  }

  @Override
  public String toString() {
    return String.format("Watched repo: %s", this.repo_name);
  }
}
