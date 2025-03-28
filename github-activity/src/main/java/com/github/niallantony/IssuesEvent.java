package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class IssuesEvent extends GitEvent {
  private String title;
  private String action;

  public IssuesEvent(JsonNode event) {
    super(event);
    this.title = this.payload.get("issue").get("title").asText();
    this.action = this.payload.get("action").asText();
  }

  @Override
  public String toString() {

    return String.format("%s issue on %s: %s", this.action, this.repo_name, this.title);
  }
}
