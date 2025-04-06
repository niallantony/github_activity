# Github Activity
<a href="https://skillicons.dev">
  <img src="https://skillicons.dev/icons?i=java,maven" />
</a>

A command-line program for fetching Github Activity and displaying it on the command line

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

## Next Steps

This project was initially started as a learning project to start me off with Maven, and get a first functional Java app out there. That goal is complete - and I have ended up with a robust, albeit small, application which is packaged and runs as intended.

There are plenty of roads now for improvement: 
- This could be converted into a micro-service API for getting human-readable summaries of Github user's activities
- There is the opportunity to practice some data visualisation and expand this into a set of dashboard tools for displaying user activity
- This could be the base of a tool which tracks weekly activity for developers, condensed into weekly or monthly digests.

The possibilities are there - and while for now I will be focussing on other Java skills - I will be back to expand this project.

Next steps on my *overall* Java journey, however, include:

- **Databases**: learning the fundamentals of connecting Java to a database
- **Spring**: after the above objectives; I will feel ready to dive into the Spring framework

---

Thank you for looking, and don't forget to check out my other projects!
