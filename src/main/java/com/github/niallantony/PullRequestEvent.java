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

  @Override
  public int getAggregationData() {
    if (this.action.equals("opened")) {
      return 1;
    }
    return 0;
  }

  public void setAction(String other) {
    this.action = other;
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (this.repo_name.equals(other.getRepo())
        && other.getAggregationData() == 1
        && this.action.equals("closed"))
      return true;
    return false;
  }
}
