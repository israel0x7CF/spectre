package com.spectrun.spectrum.utils.API;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spectrun.spectrum.controllers.test.kafkaTest;
import com.spectrun.spectrum.utils.API.ResponseBody.ResponseBody;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.logging.Logger;

public class Request<RequestObject, ResponseObject> {
    private final HttpClient client = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();
    Logger logger = Logger.getLogger(Request.class.getName());
    public ResponseBody<ResponseObject> handleApiCall(RequestObject requestBody, String url, TypeReference<ResponseBody<ResponseObject>> responseType) {
        ResponseBody<ResponseObject> response = new ResponseBody<>();
        try {
            // Serialize requestBody to JSON
            String jsonBody = objectMapper.writeValueAsString(requestBody);

            // Build the HTTP request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofMinutes(2))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                    .build();

            // Send the request and get the response
            HttpResponse<String> httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Deserialize the response body into ResponseBody<ResponseObject>
            response = objectMapper.readValue(httpResponse.body(), responseType);

        } catch (Exception e) {
            e.printStackTrace();
            response.setStatus(500); // Example error status
            response.setMessage("Error: " + e.getMessage());
        }
        return response;
    }
}
