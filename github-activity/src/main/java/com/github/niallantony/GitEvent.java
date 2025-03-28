package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

public class GitEvent {
  private String id;
  private String type;
  private JsonNode actor;
  private JsonNode repo;
  private JsonNode payload;
  private boolean isPublic;
  private String createdAt;

  public GitEvent(JsonNode event) {
    this.id = event.get("id").asText();
    this.type = event.get("type").asText();
    this.actor = event.get("actor");
    this.repo = event.get("repo");
    this.payload = event.get("payload");
    this.isPublic = event.get("public").asBoolean();
    this.createdAt = event.get("created_at").asText();
  }

  public String toString() {
    return String.format("Event %s: %s created at %s", this.id, this.type, this.createdAt);
  }
}
