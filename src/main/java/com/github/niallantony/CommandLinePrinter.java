package com.github.niallantony;

import java.util.ArrayList;

public class CommandLinePrinter implements ActivityPrinter {
  public void print(String events) {
    System.out.println(events);
  }

  public void printAll(ArrayList<String> events) {
    for (int i = 0; i < events.size(); i++) {
      System.out.println("-\t" + events.get(i));
    }
  }
}
