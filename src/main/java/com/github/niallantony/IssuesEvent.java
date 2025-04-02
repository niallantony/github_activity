package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class IssuesEvent extends GitEvent {
  private @Getter String title;
  private @Getter String action;

  public IssuesEvent(JsonNode event) {
    super(event);
    this.title = this.payload.get("issue").get("title").asText();
    this.action = this.payload.get("action").asText();
  }

  @Override
  public String toString() {

    return String.format("%s issue on %s: %s", this.action.substring(0, 1).toUpperCase() + this.action.substring(1),
        this.repo_name, this.title);
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != IssuesEvent.class)
      return false;
    IssuesEvent next = (IssuesEvent) other;
    if (this.repo_name.equals(next.getRepo())
        && this.action.equals(next.getAction())
        && this.title.equals(next.getTitle())) {
      return true;
    }
    return false;
  }
}
