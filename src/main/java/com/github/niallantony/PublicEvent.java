package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class PublicEvent extends GitEvent {

  public PublicEvent(JsonNode event) {
    super(event);
  }

  @Override
  public String toString() {
    return String.format("Made repo '%s' public", this.repo_name);
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() == PublicEvent.class && other.getRepo().equals(this.repo_name)) {
      return true;
    }
    return false;
  }

}
