package com.github.niallantony;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.w3c.dom.events.Event;

import com.fasterxml.jackson.databind.JsonNode;
import com.github.niallantony.utils.TestUtils;

public class EventAggregatorTest {

  @ParameterizedTest
  @MethodSource("pushEventsOfSize")
  public void aggregator_WhenGivenSimilarPushEvents_ReturnsOneEvent(int[] sizes, int finalSize) {
    ArrayList<GitEvent> events = TestUtils.getPushEventsWithSameRepoOfSizes(sizes);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(1, aggregated.size());
  }

  @ParameterizedTest
  @MethodSource("pushEventsOfSize")
  public void aggregator_WhenGivenSimilarPushEventsOfSizes_AddsCommitSizes(int[] sizes, int finalSize) {
    ArrayList<GitEvent> events = TestUtils.getPushEventsWithSameRepoOfSizes(sizes);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(String.format("Pushed %d commit(s) to mockRepo", finalSize), aggregated.get(0).toString());
  }

  @ParameterizedTest(name = "repos: {0} are size {1}")
  @MethodSource("pushEventsOfRepo")
  public void aggregator_WhenGivenPushEventsOfVariousRepos_AggregatesAppropriately(String[] repos, int finalSize) {
    ArrayList<GitEvent> events = TestUtils.getPushEventsWithDifferentRepos(repos);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(finalSize, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenCreateRepoAndCreateMainBranch_OnlyReturnsRepoCreationEvent() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent repoCreation = new CreateEvent(TestUtils.getMockNodeOfRefType("CreateEvent", "repository"));
    GitEvent mainBranch = new CreateEvent(TestUtils.getMockCreateMain());
    events.add(repoCreation);
    events.add(mainBranch);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(1, aggregated.size());
    assertEquals("Created 'mockRepo': mockDescription", aggregated.get(0).toString());
  }

  @Test
  public void aggregator_WhenGivenIdenticalCreateEvents_OnlyReturnsOne() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent create1 = new CreateEvent(TestUtils.getMockNode("CreateEvent"));
    GitEvent create2 = new CreateEvent(TestUtils.getMockNode("CreateEvent"));
    events.add(create1);
    events.add(create2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(1, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenIdenticalDeleteEvents_OnlyReturnsOne() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent delete1 = new DeleteEvent(TestUtils.getMockNode("DeleteEvent"));
    GitEvent delete2 = new DeleteEvent(TestUtils.getMockNode("DeleteEvent"));
    events.add(delete1);
    events.add(delete2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(1, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenDeleteAndCreateEventsWithSimilarDetails_ReturnsBoth() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent create = new CreateEvent(TestUtils.getMockNode("CreateEvent"));
    GitEvent delete = new DeleteEvent(TestUtils.getMockNode("DeleteEvent"));
    events.add(create);
    events.add(delete);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(2, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenDifferentIssues_ReturnsBoth() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent issue1 = new IssuesEvent(TestUtils.getMockNodeOfAction("IssueEvent", "closed"));
    GitEvent issue2 = new IssuesEvent(TestUtils.getMockNodeOfAction("IssueEvent", "opened"));
    events.add(issue1);
    events.add(issue2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(2, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenSameIssues_ReturnsOne() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent issue1 = new IssuesEvent(TestUtils.getMockNode("IssueEvent"));
    GitEvent issue2 = new IssuesEvent(TestUtils.getMockNode("IssueEvent"));
    events.add(issue1);
    events.add(issue2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(1, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenDifferentPullRequests_ReturnsBoth() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent issue1 = new PullRequestEvent(TestUtils.getMockNodeOfAction("PullRequestEvent", "closed"));
    GitEvent issue2 = new PullRequestEvent(TestUtils.getMockNodeOfAction("PullRequestEvent", "started"));
    events.add(issue1);
    events.add(issue2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(2, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenAdjacentOpenCloseOnSameBranch_ReturnsOpenAndClosedEvent() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent issue1 = new PullRequestEvent(TestUtils.getMockNodeOfAction("PullRequestEvent", "opened"));
    GitEvent issue2 = new PullRequestEvent(TestUtils.getMockNodeOfAction("PullRequestEvent", "closed"));
    events.add(issue1);
    events.add(issue2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals("Pull request action: opened and closed branch 'mockTitle' in repo mockRepo",
        aggregated.get(0).toString());
    assertEquals(1, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenAdjacentCommitComments_ReturnsBoth() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent comCom1 = new CommitCommentEvent(TestUtils.getMockCommitComment("mockComment"));
    GitEvent comCom2 = new CommitCommentEvent(TestUtils.getMockCommitComment("mockComment"));
    events.add(comCom1);
    events.add(comCom2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals("Made a comment on a commit in mockRepo. It said: mockComment",
        aggregated.get(0).toString());
    assertEquals(2, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenAdjacentForkEventsOnSameRepo_OnlyReturnsOne() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent fork1 = new ForkEvent(TestUtils.getMockNode("ForkEvent"));
    GitEvent fork2 = new ForkEvent(TestUtils.getMockNode("ForkEvent"));
    events.add(fork1);
    events.add(fork2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(1, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenAdjacentForkEventsOnDifferentRepo_ReturnsBoth() {
    ArrayList<GitEvent> events = new ArrayList<>();
    GitEvent fork1 = new ForkEvent(TestUtils.getMockNodeOfRepo("ForkEvent", "repo1"));
    GitEvent fork2 = new ForkEvent(TestUtils.getMockNodeOfRepo("ForkEvent", "repo2"));
    events.add(fork1);
    events.add(fork2);

    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    assertEquals(2, aggregated.size());
  }

  @ParameterizedTest
  @MethodSource("gollumEventsOfSize")
  public void aggregator_WhenGivenGollumEventsOfSizes_AggregatesCorrectly(int[] sizes) {
    ArrayList<GitEvent> events = TestUtils.getGollumEventsWithOfPagesSize(sizes);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(1, aggregated.size());
  }

  @ParameterizedTest(name = "repos: {0} are size {1} - with first event at {2} pages")
  @MethodSource("gollumEventsOfRepo")
  public void aggregator_WhenGivenGollumEventsOfVariousRepos_AggregatesAppropriately(String[] repos, int finalSize,
      int firstPages) {
    ArrayList<GitEvent> events = TestUtils.getGollumEventsWithDifferentRepos(repos);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);
    GollumEvent first = (GollumEvent) aggregated.get(0);

    assertEquals(finalSize, aggregated.size());
    assertEquals(firstPages, first.getPages());
  }

  @ParameterizedTest
  @MethodSource("genericEventsOfType")
  public void aggregator_WhenGivenEventsOfTypes_AggregatesAppropriately(String[] types, int finalSize,
      String firstString) {
    ArrayList<GitEvent> events = TestUtils.getGitEventsOfDifferentTypes(types);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(finalSize, aggregated.size());
    assertEquals(firstString, aggregated.get(0).toString());
  }

  @Test
  public void aggregator_WhenGivenWatchEventsOfSameRepo_OnlyReturnsOne() {
    ArrayList<GitEvent> events = new ArrayList<>();
    JsonNode watchNode = TestUtils.getMockNode("WatchEvent");
    GitEvent watch1 = new WatchEvent(watchNode);
    GitEvent watch2 = new WatchEvent(watchNode);
    events.add(watch1);
    events.add(watch2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(1, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenWatchEventsOfDifferentRepos_ReturnsBoth() {
    ArrayList<GitEvent> events = new ArrayList<>();
    JsonNode watchNode = TestUtils.getMockNode("WatchEvent");
    JsonNode watchNode2 = TestUtils.getMockNodeOfRepo("WatchEvent", "repo2");
    GitEvent watch1 = new WatchEvent(watchNode);
    GitEvent watch2 = new WatchEvent(watchNode2);
    events.add(watch1);
    events.add(watch2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(2, aggregated.size());
  }

  @ParameterizedTest
  @MethodSource("issueCommentsOfParams")
  public void aggregator_WhenGivenIssueCommentsOfParams_AggregatesAppropriately(String[] params, int finalSize,
      String firstString) {
    ArrayList<GitEvent> events = TestUtils.getIssueCommentEventsWithDifferentParams(params);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(finalSize, aggregated.size());
    assertEquals(firstString, aggregated.get(0).toString());
  }

  @Test
  public void aggregator_WhenGivenMemberEventsOfSameRepo_AggregatesIntoOne() {
    JsonNode node = TestUtils.getMockMemberEvent("user1");
    MemberEvent event1 = new MemberEvent(node);
    MemberEvent event2 = new MemberEvent(node);
    ArrayList<GitEvent> events = new ArrayList<>();
    events.add(event1);
    events.add(event2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(1, aggregated.size());
    assertEquals("Added 2 users to the repo mockRepo", aggregated.get(0).toString());
  }

  @Test
  public void aggregator_WhenGivenMemberEventsOfDifferentRepo_AggregatesIntoOne() {
    JsonNode node = TestUtils.getMockMemberEvent("user1");
    JsonNode node2 = TestUtils.getMockMemberEventOfDifferentRepo("user2", "mockRepo2");
    MemberEvent event1 = new MemberEvent(node);
    MemberEvent event2 = new MemberEvent(node2);
    ArrayList<GitEvent> events = new ArrayList<>();
    events.add(event1);
    events.add(event2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(2, aggregated.size());
    assertEquals("Added user user1 to the repo mockRepo", aggregated.get(0).toString());
    assertEquals("Added user user2 to the repo mockRepo2", aggregated.get(1).toString());
  }

  @Test
  public void aggregator_WhenGivenTwoIdenticalPublicEvents_ReturnsOnlyOne() {
    JsonNode node = TestUtils.getMockNode("PublicEvent");
    PublicEvent event1 = new PublicEvent(node);
    PublicEvent event2 = new PublicEvent(node);
    ArrayList<GitEvent> events = new ArrayList<>();
    events.add(event1);
    events.add(event2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(1, aggregated.size());
  }

  @Test
  public void aggregator_WhenGivenMemberEventsOfDifferentRepo_ReturnsBoth() {
    JsonNode node = TestUtils.getMockNodeOfRepo("MemberEvent", "mockRepo");
    JsonNode node2 = TestUtils.getMockNodeOfRepo("MemberEvent", "mockRepo2");
    PublicEvent event1 = new PublicEvent(node);
    PublicEvent event2 = new PublicEvent(node2);
    ArrayList<GitEvent> events = new ArrayList<>();
    events.add(event1);
    events.add(event2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(2, aggregated.size());
  }

  @Test
  public void aggregator_whenGivenIdentialReleaseEvents_returnsOne() {
    JsonNode node = TestUtils.getMockReleaseNode("mockRepo", "mockRelease");
    ReleaseEvent event1 = new ReleaseEvent(node);
    ReleaseEvent event2 = new ReleaseEvent(node);
    ArrayList<GitEvent> events = new ArrayList<>();
    events.add(event1);
    events.add(event2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(1, aggregated.size());
  }

  @Test
  public void aggregator_whenGivenReleaseEventsWithDifferentRepos_returnsBoth() {
    JsonNode node = TestUtils.getMockReleaseNode("mockRepo", "mockRelease");
    JsonNode node2 = TestUtils.getMockReleaseNode("mockRepo2", "mockRelease");
    ReleaseEvent event1 = new ReleaseEvent(node);
    ReleaseEvent event2 = new ReleaseEvent(node2);
    ArrayList<GitEvent> events = new ArrayList<>();
    events.add(event1);
    events.add(event2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(2, aggregated.size());
    assertEquals("Published mockRepo: mockRelease", aggregated.get(0).toString());
  }

  @Test
  public void aggregator_whenGivenReleaseEventsWithDifferentReleases_returnsBoth() {
    JsonNode node = TestUtils.getMockReleaseNode("mockRepo", "mockRelease");
    JsonNode node2 = TestUtils.getMockReleaseNode("mockRepo", "mockRelease2");
    ReleaseEvent event1 = new ReleaseEvent(node);
    ReleaseEvent event2 = new ReleaseEvent(node2);
    ArrayList<GitEvent> events = new ArrayList<>();
    events.add(event1);
    events.add(event2);
    ArrayList<GitEvent> aggregated = EventAggregator.aggregate(events);

    assertEquals(2, aggregated.size());
    assertEquals("Published mockRepo: mockRelease2", aggregated.get(1).toString());
  }

  private static Stream<Arguments> pushEventsOfSize() {
    int[] sizes1 = { 1, 3, 4 };
    int[] sizes2 = { 4, 3, 4 };
    int[] sizes3 = { 1, 1, 1 };
    return Stream.of(Arguments.arguments(sizes1, 8), Arguments.arguments(sizes2, 11), Arguments.arguments(sizes3, 3));
  }

  private static Stream<Arguments> gollumEventsOfSize() {
    int[] sizes1 = { 1, 3, 4 };
    int[] sizes2 = { 4, 3, 4 };
    int[] sizes3 = { 1, 1, 1 };
    return Stream.of(Arguments.arguments(sizes1, 8), Arguments.arguments(sizes2, 11), Arguments.arguments(sizes3, 3));
  }

  private static Stream<Arguments> pushEventsOfRepo() {
    String[] repos1 = { "repo1", "repo1", "repo1" };
    String[] repos2 = { "repo1", "repo2", "repo1" };
    String[] repos3 = { "repo2", "repo2", "repo1" };
    String[] repos4 = { "repo1", "repo2", "repo3" };
    return Stream.of(
        Arguments.arguments(repos1, 1),
        Arguments.arguments(repos2, 3),
        Arguments.arguments(repos3, 2),
        Arguments.arguments(repos4, 3));
  }

  private static Stream<Arguments> issueCommentsOfParams() {
    String[] params1 = { "repo1,created,issue1", "repo1,created,issue1", "repo1,created,issue1" };
    String[] params2 = { "repo1,created,issue1", "repo2,created,issue1", "repo1,created,issue1" };
    String[] params3 = { "repo1,created,issue1", "repo1,created,issue1", "repo2,created,issue1" };
    String[] params4 = { "repo1,created,issue1", "repo1,deleted,issue1", "repo1,created,issue1" };
    String[] params5 = { "repo1,edited,issue1", "repo1,deleted,issue1", "repo1,created,issue1" };
    String[] params6 = { "repo1,created,issue1", "repo1,created,issue2", "repo1,created,issue3" };
    return Stream.of(
        Arguments.arguments(params1, 1, "Created 3 comments on issue: issue1 (repo1)"),
        Arguments.arguments(params2, 3, "Created a comment on issue: issue1 (repo1)"),
        Arguments.arguments(params3, 2, "Created 2 comments on issue: issue1 (repo1)"),
        Arguments.arguments(params4, 1, "Created 2 comments on issue: issue1 (repo1)"),
        Arguments.arguments(params5, 1, "Created a comment on issue: issue1 (repo1)"),
        Arguments.arguments(params6, 3, "Created a comment on issue: issue1 (repo1)"));
  }

  private static Stream<Arguments> gollumEventsOfRepo() {
    String[] repos1 = { "repo1", "repo1", "repo1" };
    String[] repos2 = { "repo1", "repo2", "repo1" };
    String[] repos3 = { "repo2", "repo2", "repo1" };
    String[] repos4 = { "repo1", "repo2", "repo3" };
    return Stream.of(
        Arguments.arguments(repos1, 1, 9),
        Arguments.arguments(repos2, 3, 3),
        Arguments.arguments(repos3, 2, 6),
        Arguments.arguments(repos4, 3, 3));
  }

  private static Stream<Arguments> genericEventsOfType() {
    String[] repos1 = { "GitEvent1", "GitEvent1", "GitEvent1" };
    String[] repos2 = { "GitEvent1", "GitEvent2", "GitEvent1" };
    String[] repos3 = { "GitEvent2", "GitEvent2", "GitEvent1" };
    String[] repos4 = { "GitEvent1", "GitEvent2", "GitEvent3" };
    return Stream.of(
        Arguments.arguments(repos1, 1, "3 GitEvent1 events created at mockDate"),
        Arguments.arguments(repos2, 3, "Event mockId: GitEvent1 created at mockDate"),
        Arguments.arguments(repos3, 2, "2 GitEvent2 events created at mockDate"),
        Arguments.arguments(repos4, 3, "Event mockId: GitEvent1 created at mockDate"));

  }

}
