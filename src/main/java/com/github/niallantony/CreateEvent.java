package com.github.niallantony;

import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Getter;

public class CreateEvent extends GitEvent {

  private String desc;
  private @Getter String[] refs = new String[2];

  public CreateEvent(JsonNode event) {
    super(event);
    this.desc = this.payload.get("description").asText();
    this.refs[0] = this.payload.get("ref").asText();
    this.refs[1] = this.payload.get("ref_type").asText();
  }

  @Override
  public boolean shouldIgnore() {
    if (this.refs[1].equals("branch") && this.refs[0].equals("main"))
      return true;
    return false;
  }

  @Override
  public String toString() {
    if (this.refs[1].equals("repository")) {
      return String.format("Created '%s': %s", this.repo_name, this.desc);
    } else {
      return String.format("Created %s %s in %s", this.refs[1], this.refs[0], this.repo_name);
    }
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != CreateEvent.class)
      return false;
    CreateEvent previous = (CreateEvent) other;
    if (this.repo_name.equals(other.getRepo())
        && Arrays.toString(previous.getRefs()).equals(String.format("[%s, %s]", this.refs[0], this.refs[1])))
      return true;
    return false;
  }
}
