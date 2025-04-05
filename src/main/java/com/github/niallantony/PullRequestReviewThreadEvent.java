package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class PullRequestReviewThreadEvent extends GitEvent {

  private @Getter String title;
  private @Getter String action;

  public PullRequestReviewThreadEvent(JsonNode event) {
    super(event);
    this.action = this.payload.get("action").asText();
    this.title = this.payload.get("pull_request").get("title").asText();
  }

  @Override
  public String toString() {
    return String.format("Marked a thread as %s in '%s' (%s)", this.action, this.title, this.repo_name);
  }

  @Override
  public void aggregate(GitEvent other) {
    if (other.getClass() != PullRequestReviewThreadEvent.class)
      return;
    PullRequestReviewThreadEvent next = (PullRequestReviewThreadEvent) other;
    this.action = next.getAction();
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != PullRequestReviewThreadEvent.class)
      return false;
    PullRequestReviewThreadEvent next = (PullRequestReviewThreadEvent) other;
    if (next.getRepo().equals(this.repo_name)
        && next.getTitle().equals(this.title))
      return true;
    return false;
  }
}
