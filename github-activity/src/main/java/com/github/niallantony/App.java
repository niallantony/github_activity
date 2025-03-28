package com.github.niallantony;

/**
 * An application for downloading a user's recent Github activity.
 */
public class App {
  public static void main(String[] args) {
    ActivityGetter getter = new ActivityGetter(args[0]);
<<<<<<< HEAD
    getter.downloadActivity();
    String activity = getter.getAggregatedActivity(10);
    CommandLinePrinter printer = new CommandLinePrinter();
    printer.print(activity);
=======
    getter.getActivity();
    getter.showAggregatedActivity(10);
>>>>>>> 15982249d546069e05f8d060f389cef0ec3b5871
  }
}
