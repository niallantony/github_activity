package com.github.niallantony;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;
import java.util.ArrayList;

/**
 * An application for downloading a user's recent Github activity.
 */
public class App {
  public static void main(String[] args) {
    CommandLinePrinter printer = new CommandLinePrinter();

    try {

      EventApiResponseGetter getter = new EventApiResponseGetter(args[0]);
      HttpResponse<String> response = getter.getResponse();
      if (response.statusCode() == 200) {
        EventListParser parser = new EventListParser(response.body());
        ArrayList<String> activity = parser.getAggregatedActivity(3);
        printer.printAll(activity);
      } else {
        System.err.println(String.format("ERROR: Status %d on username %s\n", response.statusCode(), args[0]));
      }

    } catch (IOException ex) {
      System.err.println(ex);
    } catch (InterruptedException ex) {
      System.err.println(ex);
    } catch (URISyntaxException ex) {
      System.err.println(ex);
    }

  }
}
