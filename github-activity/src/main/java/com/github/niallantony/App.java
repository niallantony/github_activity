package com.github.niallantony;

import java.util.ArrayList;

/**
 * An application for downloading a user's recent Github activity.
 */
public class App {
  public static void main(String[] args) {
    ActivityGetter getter = new ActivityGetter(args[0]);
    getter.downloadActivity();
    ArrayList<String> activity = getter.getAggregatedActivity(10);
    CommandLinePrinter printer = new CommandLinePrinter();
    printer.printAll(activity);
  }
}
