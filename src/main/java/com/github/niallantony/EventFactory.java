package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class EventFactory {
  /**
   * Factory for returning objects of the correct event.
   * 
   * @param event A Json representation of the Github Event as retrieved from the
   *              API
   * @return An event class corresponding to the correct Event
   */
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
        return new GollumEvent(event);
      case "WatchEvent":
        return new WatchEvent(event);
      case "IssueCommentEvent":
        return new IssueCommentEvent(event);
      case "MemberEvent":
        return new MemberEvent(event);
      case "PublicEvent":
        return new PublicEvent(event);
      case "ReleaseEvent":
        return new ReleaseEvent(event);
      case "SponsorshipEvent":
        return new SponsorshipEvent(event);
      case "PullRequestReviewEvent":
        return new PullRequestReviewEvent(event);
      case "PullRequestReviewCommentEvent":
        return new PullRequestReviewCommentEvent(event);
      case "PullRequestReviewThreadEvent":
        return new PullRequestReviewThreadEvent(event);
      default:
        return new GitEvent(event);
    }
  }
}
