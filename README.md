# Github Activity

A command-line program for fetching Github Activity and displaying it on the command line

<a href="https://skillicons.dev">
  <img src="https://skillicons.dev/icons?i=java,maven" />
</a>

## How to Use

Simply use this command together with a public github username to receive a list of recent events:

`github-activity niallantony`

returns

```
-       Created branch fix/clean-repo in niallantony/github_activity
-       Pushed 1 commit(s) to niallantony/Blog
-       Opened issue on niallantony/JavaGitActivity: Tidy GitHub Repo
```

`github-activity` aggregates similar events, so if two adjacent events push commits to the same repository, for instance,
the application will combine these events into a single line.

## Learning Objectives

This project was designed solely as a learning project to start learning the fundamentals of backend design
with Java. My aim is to learn a few core fundamentals about Java and how it works with APIs (in this project),
databases (in a future version?) and threads (in another project or a future version) - before moving on to
learning the Spring framework.

- **Maven**: Learn the Maven build tool and how to properly structure a Java program.
- **Java and HTTP**: Learn how Java uses HTTP and the best practices around connecting to APIs.
- **JUnit and Mockito**: Create a solid suite of Unit Tests to make sure the application runs as needed.
- **Bonus: Lombok**: Use Lombok to simplify code.

## ToDo:

- Create dedicated event actions for missing github events, include:
    - `CommitCommentEvent`
    - `ForkEvent`
    - `GollumEvent`
    - `IssueCommentEvent`
    - `MemberEvent`
    - `PublicEvent`
    - `PullRequestReviewEvent`
    - `PullRequestReviewCommentEvent`
    - `PullRequestReviewThreadEvent`
    - `ReleaseEvent`
    - `SponsorshipEvent`
    - `WatchEvent`

## Next Steps

Next steps on this learning project depend on whether I choose to implement threads in this program or include
persistance with a database. After reading up on these topics I might find it suits a different project type
more than a future version of this (it does feel like this has gone as far as I want it to...)

Next steps on my *overall* Java journey, however, include:
 
- **Threads**: making an app that executes its processes on multiple threads
- **Databases**: learning the fundamentals of connecting Java to a database
- **Spring**: after the above objectives; I will feel ready to dive into the Spring framework

---

Thank you for looking, and don't forget to check out my other projects!
