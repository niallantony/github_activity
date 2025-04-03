package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class CommitCommentEvent extends GitEvent {
  private @Getter String comment;

  public CommitCommentEvent(JsonNode event) {
    super(event);
    this.comment = this.payload.get("comment").get("body").asText();
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    return false;
  }

  @Override
  public String toString() {
    return String.format("Made a comment on a commit in %s. It said: %s", this.repo_name, this.comment);
  }
}
