package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class PullRequestReviewEvent extends GitEvent {

  private @Getter String title;

  public PullRequestReviewEvent(JsonNode event) {
    super(event);
    this.title = this.payload.get("pull_request").get("title").asText();
  }

  @Override
  public String toString() {
    return String.format("Reviewed a pull request in %s: %s", this.repo_name, this.title);
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != PullRequestReviewEvent.class)
      return false;
    PullRequestReviewEvent next = (PullRequestReviewEvent) other;
    if (this.repo_name.equals(other.getRepo())
        && next.getTitle().equals(this.title))
      return true;
    return false;
  }
}
