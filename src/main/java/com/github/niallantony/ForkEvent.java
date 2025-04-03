package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class ForkEvent extends GitEvent {

  public ForkEvent(JsonNode event) {
    super(event);
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != ForkEvent.class)
      return false;
    if (this.repo_name.equals(other.getRepo())) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    return String.format("Forked a repo: %s", this.repo_name);
  }
}
