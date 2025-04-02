package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class PullRequestEvent extends GitEvent {

  private String toRepo;
  private @Getter String title;
  private @Getter String action;

  public PullRequestEvent(JsonNode event) {
    super(event);
    this.action = this.payload.get("action").asText();
    this.title = this.payload.get("pull_request").get("title").asText();
    this.toRepo = this.payload.get("pull_request").get("head").get("repo").get("full_name").asText();
  }

  @Override
  public String toString() {
    return String.format("Pull request action: %s branch '%s' in repo %s", this.action, this.title, this.toRepo);
  }

  @Override
  public void aggregate(GitEvent other) {
    this.action = "opened and closed";
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != PullRequestEvent.class)
      return false;
    PullRequestEvent next = (PullRequestEvent) other;
    if (this.repo_name.equals(other.getRepo())
        && next.getAction().equals("closed")
        && next.getTitle().equals(this.title)
        && this.action.equals("opened"))
      return true;
    return false;
  }
}
