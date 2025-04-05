package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class PullRequestReviewCommentEvent extends GitEvent {
  private @Getter String title;

  public PullRequestReviewCommentEvent(JsonNode event) {
    super(event);
    this.title = this.payload.get("pull_request").get("title").asText();
  }

  @Override
  public String toString() {
    if (this.aggregations == 1) {
      return String.format("Commented on a pull request review: %s (%s)", this.title, this.repo_name);
    } else {
      return String.format("Made %d comments on a pull request review: %s (%s)", this.aggregations, this.title,
          this.repo_name);
    }
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != PullRequestReviewCommentEvent.class)
      return false;
    PullRequestReviewCommentEvent next = (PullRequestReviewCommentEvent) other;
    if (this.repo_name.equals(other.getRepo())
        && next.getTitle().equals(this.title))
      return true;
    return false;
  }
}
