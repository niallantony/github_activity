package com.github.niallantony;

import java.util.ArrayList;

public class EventAggregator {
  public static ArrayList<GitEvent> aggregate(ArrayList<GitEvent> events) throws IndexOutOfBoundsException {
    ArrayList<GitEvent> aggregated = new ArrayList<>();
    if (events.size() < 1)
      throw new IndexOutOfBoundsException();
    GitEvent current = events.get(0);
    for (int i = 1; i < events.size(); i++) {
      if (current.isSimilar(events.get(i))) {
        current.aggregate(events.get(i));
      } else {
        aggregated.add(current);
        current = events.get(i);
      }
    }
    return aggregated;
  }
}
