package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class GitEvent {
  protected String id;
  protected String type;
  protected String user;
  protected String repo_name;
  protected JsonNode payload;
  protected boolean isPublic;
  protected String createdAt;

  public GitEvent(JsonNode event) {
    this.id = event.get("id").asText();
    this.type = event.get("type").asText();
    this.user = event.get("actor").get("display_login").asText();
    this.repo_name = event.get("repo").get("name").asText();
    this.payload = event.get("payload");
    this.isPublic = event.get("public").asBoolean();
    this.createdAt = event.get("created_at").asText();
  }

  public String toString() {
    return String.format("Event %s: %s created at %s", this.id, this.type, this.createdAt);
  }
}
