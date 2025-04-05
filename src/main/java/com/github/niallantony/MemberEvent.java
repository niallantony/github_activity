package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class MemberEvent extends GitEvent {
  private String user;

  public MemberEvent(JsonNode event) {
    super(event);
    this.user = this.payload.get("member").get("name").asText();
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != MemberEvent.class) {
      return false;
    }
    MemberEvent next = (MemberEvent) other;
    if (next.getRepo().equals(this.repo_name)) {
      return true;
    }
    return false;
  }

  @Override
  public String toString() {
    if (this.aggregations == 1) {
      return String.format("Added user %s to the repo %s", this.user, this.repo_name);
    } else {
      return String.format("Added %d users to the repo %s", this.aggregations, this.repo_name);
    }
  }
}
