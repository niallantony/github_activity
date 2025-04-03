package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class EventFactory {
  public GitEvent create(JsonNode event) {
    String type = event.get("type").asText();
    switch (type) {
      case "PushEvent":
        return new PushEvent(event);
      case "CreateEvent":
        return new CreateEvent(event);
      case "DeleteEvent":
        return new DeleteEvent(event);
      case "PullRequestEvent":
        return new PullRequestEvent(event);
      case "IssuesEvent":
        return new IssuesEvent(event);
      case "CommitCommentEvent":
        return new CommitCommentEvent(event);
      case "ForkEvent":
        return new ForkEvent(event);
      case "GollumEvent":
      case "IssueCommentEvent":
      case "MemberEvent":
      case "PublicEvent":
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
