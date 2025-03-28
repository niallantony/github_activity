package com.github.niallantony;

/**
 * Hello world!
 */
public class App {
  public static void main(String[] args) {
    ActivityGetter getter = new ActivityGetter(args[0]);
    getter.getActivity();
    getter.showActivity(3);
  }
}
