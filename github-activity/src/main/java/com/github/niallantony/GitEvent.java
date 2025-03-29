package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class GitEvent {
  protected String id;
  protected int aggregations;
  protected String type;
  protected String user;
  protected String repo_name;
  protected JsonNode payload;
  protected boolean isPublic;
  protected String createdAt;

  public static final String EVENT_STRING = "Event %s: %s created at %s";
  public static final String EVENTS_STRING = "%d %s events created at %s";

  public GitEvent(JsonNode event) {
    this.id = event.get("id").asText();
    this.type = event.get("type").asText();
    this.user = event.get("actor").get("display_login").asText();
    this.repo_name = event.get("repo").get("name").asText();
    this.payload = event.get("payload");
    this.isPublic = event.get("public").asBoolean();
    this.createdAt = event.get("created_at").asText();
    this.aggregations = 1;
  }

  public String toString() {
    if (this.aggregations == 1) {
      return String.format(GitEvent.EVENT_STRING, this.id, this.type, this.createdAt);
    } else {
      return String.format(GitEvent.EVENTS_STRING, this.aggregations, this.type, this.createdAt);
    }
  }

  public int getAggregationData() {
    return 0;
  }

  public String getRepo() {
    return this.repo_name;
  }

  public String getType() {
    return this.type;
  }

  public void setRepo(String repo) {
    this.repo_name = repo;
  }

  public boolean isSimilar(GitEvent other) {
    if (other.getType().equals(this.type))
      return true;
    return false;
  }

  public void aggregate(GitEvent other) {
    if (other.isSimilar(this)) {
      this.aggregations++;
    }
  }
}
