package com.github.niallantony.utils;

import java.util.ArrayList;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.github.niallantony.GitEvent;
import com.github.niallantony.GollumEvent;
import com.github.niallantony.IssueCommentEvent;
import com.github.niallantony.PushEvent;

public class TestUtils {

  static class MockNode {
    ObjectMapper mapper;
    ObjectNode mockGitEvent;
    ObjectNode payloadNode;

    public MockNode(String type) {
      this.mapper = new ObjectMapper();
      this.mockGitEvent = mapper.createObjectNode();
      this.mockGitEvent.put("id", "mockId");
      this.mockGitEvent.put("type", type);
      this.mockGitEvent.put("public", true);
      this.mockGitEvent.put("created_at", "mockDate");
      ObjectNode actorNode = mapper.createObjectNode();
      actorNode.put("display_login", "mockUser");
      this.mockGitEvent.set("actor", actorNode);
      this.payloadNode = mapper.createObjectNode();
      this.mockGitEvent.set("payload", this.payloadNode);
    }

    public MockNode withRepo(String repo) {
      ObjectNode repoNode = mapper.createObjectNode();
      repoNode.put("name", repo);
      mockGitEvent.set("repo", repoNode);
      return this;
    }

    public MockNode withSize(int size) {
      this.payloadNode.put("size", size);
      return this;
    }

    public MockNode withDesc(String desc) {
      this.payloadNode.put("description", desc);
      return this;
    }

    public MockNode withRef(String ref) {
      this.payloadNode.put("ref", ref);
      return this;
    }

    public MockNode withRefType(String ref) {
      this.payloadNode.put("ref_type", ref);
      return this;
    }

    public MockNode withAction(String action) {
      this.payloadNode.put("action", action);
      return this;
    }

    public MockNode withPages(int length) {
      ArrayNode pages = mapper.createArrayNode();
      for (int i = 0; i < length; i++) {
        JsonNode page = mapper.createObjectNode();
        pages.add(page);
      }
      this.payloadNode.set("pages", pages);
      return this;
    }

    public MockNode withUser(String name) {
      ObjectNode member = mapper.createObjectNode();
      member.put("name", name);
      this.payloadNode.set("member", member);
      return this;
    }

    public MockNode withPullRequest(String title, String repo) {
      ObjectNode pullRequestNode = mapper.createObjectNode();
      pullRequestNode.put("title", title);
      ObjectNode headNode = mapper.createObjectNode();
      ObjectNode headRepoNode = mapper.createObjectNode();
      headRepoNode.put("full_name", repo);
      headNode.set("repo", headRepoNode);
      pullRequestNode.set("head", headNode);
      this.payloadNode.set("pull_request", pullRequestNode);
      return this;
    }

    public MockNode withCommitComment(String comment) {
      ObjectNode commentNode = mapper.createObjectNode();
      commentNode.put("body", comment);
      this.payloadNode.set("comment", commentNode);
      return this;
    }

    public MockNode withIssue(String title) {
      ObjectNode issueNode = mapper.createObjectNode();
      issueNode.put("title", title);
      this.payloadNode.set("issue", issueNode);
      return this;
    }

    public JsonNode asJsonNode() {
      try {
        String json = this.mapper.writer().writeValueAsString(this.mockGitEvent);
        JsonNode jsonRoot = this.mapper.readTree(json);
        return jsonRoot;
      } catch (Exception ex) {
        System.err.println(ex);
        return null;
      }
    }
  }

  public static JsonNode getMockNode(String type) {
    MockNode base = new MockNode(type)
        .withRepo("mockRepo")
        .withDesc("mockDescription")
        .withSize(3)
        .withRef("mockRef")
        .withRefType("mockRefType")
        .withAction("mockAction")
        .withPullRequest("mockTitle", "mockPullRepoName")
        .withIssue("mockIssueTitle");
    return base.asJsonNode();
  }

  public static JsonNode getMockPushNodeOfSize(int size) {
    MockNode base = new MockNode("PushEvent")
        .withRepo("mockRepo")
        .withDesc("mockDescription")
        .withSize(size)
        .withRef("mockRef")
        .withRefType("mockRefType")
        .withAction("mockAction")
        .withPullRequest("mockTitle", "mockPullRepoName")
        .withIssue("mockIssueTitle");
    return base.asJsonNode();
  }

  public static JsonNode getMockNodeOfAction(String type, String action) {
    MockNode base = new MockNode(type)
        .withRepo("mockRepo")
        .withDesc("mockDescription")
        .withSize(3)
        .withRef("mockRef")
        .withRefType("mockRefType")
        .withAction(action)
        .withPullRequest("mockTitle", "mockPullRepoName")
        .withIssue("mockIssueTitle");
    return base.asJsonNode();
  }

  public static JsonNode getMockCreateMain() {
    MockNode base = new MockNode("CreateEvent")
        .withRepo("mockRepo")
        .withDesc("mockDescription")
        .withSize(3)
        .withRef("main")
        .withRefType("branch")
        .withAction("mockAction")
        .withPullRequest("mockTitle", "mockPullRepoName")
        .withIssue("mockIssueTitle");
    return base.asJsonNode();
  }

  public static JsonNode getMockNodeOfRef(String type, String ref) {
    MockNode base = new MockNode(type)
        .withRepo("mockRepo")
        .withDesc("mockDescription")
        .withSize(3)
        .withRef(ref)
        .withRefType("mockRefType")
        .withAction("mockAction")
        .withPullRequest("mockTitle", "mockPullRepoName")
        .withIssue("mockIssueTitle");
    return base.asJsonNode();
  }

  public static JsonNode getMockNodeOfRefType(String type, String ref) {
    MockNode base = new MockNode(type)
        .withRepo("mockRepo")
        .withDesc("mockDescription")
        .withSize(3)
        .withRef("mockRef")
        .withRefType(ref)
        .withAction("mockAction")
        .withPullRequest("mockTitle", "mockPullRepoName")
        .withIssue("mockIssueTitle");
    return base.asJsonNode();
  }

  public static JsonNode getMockNodeOfRepo(String type, String repo) {
    MockNode base = new MockNode(type)
        .withRepo(repo)
        .withDesc("mockDescription")
        .withSize(3)
        .withRef("mockRef")
        .withRefType("mockRefType")
        .withAction("mockAction")
        .withPullRequest("mockTitle", "mockPullRepoName")
        .withIssue("mockIssueTitle");
    return base.asJsonNode();
  }

  public static JsonNode getMockCommitComment(String comment) {
    MockNode base = new MockNode("CommitCommentEvent")
        .withCommitComment("mockComment")
        .withRepo("mockRepo");
    return base.asJsonNode();
  }

  public static JsonNode getMockGollumNode(String repo, int pages) {
    MockNode base = new MockNode("GollumEvent")
        .withPages(pages)
        .withRepo(repo);
    return base.asJsonNode();
  }

  public static JsonNode getMockIssueCommentNode(String repo, String action, String issue) {
    MockNode base = new MockNode("IssueCommentEvent")
        .withRepo(repo)
        .withAction(action)
        .withIssue(issue);
    return base.asJsonNode();
  }

  public static JsonNode getMockMemberEvent(String name) {
    MockNode base = new MockNode("MemberEvent")
        .withUser(name)
        .withRepo("mockRepo");
    return base.asJsonNode();
  }

  public static JsonNode getMockMemberEventOfDifferentRepo(String name, String repo) {
    MockNode base = new MockNode("MemberEvent")
        .withUser(name)
        .withRepo(repo);
    return base.asJsonNode();
  }

  public static ArrayList<GitEvent> getPushEventsWithSameRepoOfSizes(int[] sizes) {
    ArrayList<GitEvent> events = new ArrayList<>();
    for (int i = 0; i < sizes.length; i++) {
      JsonNode node = getMockPushNodeOfSize(sizes[i]);
      PushEvent event = new PushEvent(node);
      events.add(event);
    }
    return events;
  }

  public static ArrayList<GitEvent> getIssueCommentEventsWithDifferentParams(String[] paramsList) {
    ArrayList<GitEvent> events = new ArrayList<>();
    for (int i = 0; i < paramsList.length; i++) {
      String[] params = paramsList[i].split(",");
      JsonNode node = getMockIssueCommentNode(params[0], params[1], params[2]);
      IssueCommentEvent event = new IssueCommentEvent(node);
      events.add(event);
    }
    return events;
  }

  public static ArrayList<GitEvent> getGollumEventsWithOfPagesSize(int[] sizes) {
    ArrayList<GitEvent> events = new ArrayList<>();
    for (int i = 0; i < sizes.length; i++) {
      JsonNode node = getMockGollumNode("mockRepo", sizes[i]);
      GollumEvent event = new GollumEvent(node);
      events.add(event);
    }
    return events;
  }

  public static ArrayList<GitEvent> getPushEventsWithDifferentRepos(String[] repos) {
    ArrayList<GitEvent> events = new ArrayList<>();
    for (int i = 0; i < repos.length; i++) {
      JsonNode node = getMockNodeOfRepo("PushEvent", repos[i]);
      PushEvent event = new PushEvent(node);
      events.add(event);
    }
    return events;
  }

  public static ArrayList<GitEvent> getGollumEventsWithDifferentRepos(String[] repos) {
    ArrayList<GitEvent> events = new ArrayList<>();
    for (int i = 0; i < repos.length; i++) {
      JsonNode node = getMockGollumNode(repos[i], 3);
      GollumEvent event = new GollumEvent(node);
      events.add(event);
    }
    return events;
  }

  public static ArrayList<GitEvent> getGitEventsOfDifferentTypes(String[] types) {
    ArrayList<GitEvent> events = new ArrayList<>();
    for (int i = 0; i < types.length; i++) {
      JsonNode node = getMockNode(types[i]);
      GitEvent event = new GitEvent(node);
      events.add(event);
    }
    return events;
  }

}
