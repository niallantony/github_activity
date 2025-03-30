package com.github.niallantony.utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class TestUtils {
  public static JsonNode getMockNode(String type) {
    ObjectMapper mapper = new ObjectMapper();
    ObjectNode mockGitEvent = mapper.createObjectNode();
    mockGitEvent.put("id", "mockId");
    mockGitEvent.put("type", type);
    mockGitEvent.put("public", true);
    mockGitEvent.put("created_at", "mockDate");
    ObjectNode actorNode = mapper.createObjectNode();
    actorNode.put("display_login", "mockUser");
    mockGitEvent.set("actor", actorNode);
    ObjectNode repoNode = mapper.createObjectNode();
    repoNode.put("name", "mockRepo");
    mockGitEvent.set("repo", repoNode);
    ObjectNode payloadNode = mapper.createObjectNode();
    payloadNode.put("size", 3);
    payloadNode.put("description", "mockDescription");
    payloadNode.put("ref", "mockRef");
    payloadNode.put("ref_type", "mockRefType");
    payloadNode.put("action", "mockAction");
    ObjectNode pullRequestNode = mapper.createObjectNode();
    pullRequestNode.put("title", "mockTitle");
    ObjectNode headNode = mapper.createObjectNode();
    ObjectNode headRepoNode = mapper.createObjectNode();
    headRepoNode.put("full_name", "mockPullRepoName");
    headNode.set("repo", headRepoNode);
    pullRequestNode.set("head", headNode);
    payloadNode.set("pull_request", pullRequestNode);
    ObjectNode issueNode = mapper.createObjectNode();
    issueNode.put("title", "mockIssueTitle");
    payloadNode.set("issue", issueNode);
    mockGitEvent.set("payload", payloadNode);

    try {

      String json = mapper.writer().writeValueAsString(mockGitEvent);
      JsonNode root = mapper.readTree(json);
      return root;
    } catch (Exception ex) {
      System.err.println(ex);
      return null;
    }
  }
}
