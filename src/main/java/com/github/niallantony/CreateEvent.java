package com.github.niallantony;

import java.util.Arrays;

import com.fasterxml.jackson.databind.JsonNode;

public class CreateEvent extends GitEvent {

  private String desc;
  private String ref;
  private String ref_type;

  public CreateEvent(JsonNode event) {
    super(event);
    this.desc = this.payload.get("description").asText();
    this.ref = this.payload.get("ref").asText();
    this.ref_type = this.payload.get("ref_type").asText();
  }

  @Override
  public boolean shouldIgnore() {
    if (this.ref_type.equals("branch") && this.ref.equals("main"))
      return true;
    return false;
  }

  @Override
  public String toString() {
    if (this.ref_type.equals("repository")) {
      return String.format("Created '%s': %s", this.repo_name, this.desc);
    } else {
      return String.format("Created %s %s in %s", this.ref_type, this.ref, this.repo_name);
    }
  }

  public String[] getRefs() {
    String[] refs = { this.ref, this.ref_type };
    return refs;
  }

  public void setRef(String newRef) {
    this.ref = newRef;
  }

  public void setRefType(String newRef) {
    this.ref_type = newRef;
  }

  @Override
  public boolean isSimilar(GitEvent other) {
    if (other.getClass() != CreateEvent.class)
      return false;
    CreateEvent previous = (CreateEvent) other;
    if (this.repo_name.equals(other.getRepo())
        && Arrays.toString(previous.getRefs()).equals(String.format("[%s, %s]", this.ref, this.ref_type)))
      return true;
    return false;
  }
}
