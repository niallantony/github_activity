
package com.github.niallantony;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class GollumEvent extends GitEvent {
  private @Getter int pages;

  public GollumEvent(JsonNode event) {
    super(event);
    this.pages = this.payload.get("pages").size();
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != GollumEvent.class)
      return false;
    if (this.repo_name.equals(other.getRepo())) {
      return true;
    }
    return false;
  }

  @Override
  public void aggregate(GitEvent other) {
    if (other.getClass() != GollumEvent.class)
      return;
    GollumEvent next = (GollumEvent) other;
    this.pages += next.getPages();
  }

  @Override
  public String toString() {
    return String.format("Updated %d pages on a Wiki in %s", this.pages, this.repo_name);
  }
}
