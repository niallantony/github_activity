package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class PullRequestEvent extends GitEvent {

  private String toRepo;
  private String title;
  private String action;

  public PullRequestEvent(JsonNode event) {
    super(event);
    this.action = this.payload.get("action").asText();
    this.title = this.payload.get("pull_request").get("title").asText();
    this.toRepo = this.payload.get("pull_request").get("head").get("repo").get("full_name").asText();
  }

  @Override
  public String toString() {
    return String.format("Pull request action: %s, branch '%s' in repo %s", this.action, this.title, this.toRepo);
  }

  public String getAction() {
    return this.action;
  }

  public void setAction(String other) {
    this.action = other;
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != PullRequestEvent.class)
      return false;
    PullRequestEvent previous = (PullRequestEvent) other;
    if (this.repo_name.equals(other.getRepo())
        && previous.getAction().equals("opened")
        && this.action.equals("closed"))
      return true;
    return false;
  }
}
