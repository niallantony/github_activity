package com.github.niallantony;

import java.util.ArrayList;

interface ActivityPrinter {
  public void print(String event);

  public void printAll(ArrayList<String> events);
}
