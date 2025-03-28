package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class EventFactory {
  public EventFactory() {
  }

  public GitEvent create(JsonNode event) {
    String type = event.get("type").asText();
    switch (type) {
      case "PushEvent":
        return new PushEvent(event);
      case "DeleteEvent":
      case "CreateEvent":
      case "CommitCommentEvent":
      case "ForkEvent":
      case "GollumEvent":
      case "IssueCommentEvent":
      case "IssuesEvent":
      case "MemberEvent":
      case "PublicEvent":
      case "PullRequestEvent":
      case "PullRequestReviewEvent":
      case "PullRequestReviewCommentEvent":
      case "PullRequestReviewThreadEvent":
      case "ReleaseEvent":
      case "SponsorshipEvent":
      case "WatchEvent":
      default:
        return new GitEvent(event);
    }
  }
}
