package com.github.niallantony;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;

public class ActivityGetterTest {

  @Test
  public void Getter_SendsRequest_WhenGivenClient() throws IOException, InterruptedException, URISyntaxException {
    HttpClient mockClient = mock(HttpClient.class);
    HttpResponse<String> mockResponse = mock(HttpResponse.class, RETURNS_DEEP_STUBS);

    when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);

    ActivityGetter getter = new ActivityGetter("username", mockClient);
    getter.sendRequest();

    verify(mockClient, times(1)).send(any(), any());
    verify(mockResponse, times(1)).body();
  }

}
