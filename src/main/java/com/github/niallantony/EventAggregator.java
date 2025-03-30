package com.github.niallantony;

import java.util.ArrayList;

public class EventAggregator {
  public static ArrayList<GitEvent> aggregate(ArrayList<GitEvent> events) throws IndexOutOfBoundsException {
    ArrayList<GitEvent> aggregated = new ArrayList<>();
    if (events.size() < 1)
      throw new IndexOutOfBoundsException();
    GitEvent current = null;
    for (int i = 0; i < events.size(); i++) {
      if (events.get(i).shouldIgnore())
        continue;
      if (current == null) {
        current = events.get(i);
        continue;
      }
      if (current.isSimilar(events.get(i))) {
        current.aggregate(events.get(i));
      } else {
        aggregated.add(current);
        current = events.get(i);
      }
    }
    aggregated.add(current);
    return aggregated;
  }
}
