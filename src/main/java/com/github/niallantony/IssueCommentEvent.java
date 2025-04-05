package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class IssueCommentEvent extends GitEvent {
  private @Getter String action;
  private @Getter String issue;

  public IssueCommentEvent(JsonNode event) {
    super(event);
    this.action = this.payload.get("action").asText();
    this.issue = this.payload.get("issue").asText();
  }

  @Override
  public String toString() {
    if (this.aggregations == 1) {
      return String.format("%s a comment on issue: %s (%s)",
          this.action.substring(0, 1).toUpperCase() + this.action.substring(1),
          this.issue, this.repo_name);
    } else {
      return String.format("%s %d comments on issue: %s (%s)",
          this.action.substring(0, 1).toUpperCase() + this.action.substring(1),
          this.aggregations, this.issue, this.repo_name);
    }
  }

  @Override
  public boolean shouldIgnore() {
    if (this.action.equals("edited") || this.action.equals("deleted"))
      return true;
    return false;
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != IssueCommentEvent.class)
      return false;
    IssueCommentEvent next = (IssueCommentEvent) other;
    if (next.getAction().equals(this.action) && next.getRepo().equals(this.repo_name)
        && next.getIssue().equals(this.issue)) {
      return true;
    }
    return false;
  }
}
