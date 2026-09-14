package truckpacker;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

public class TruckServer {

    public static void startServer() throws IOException {
        // Create a local server on port 8080
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        
        // Create an endpoint at http://localhost:8080/pack
        server.createContext("/pack", new PackHandler());
        server.setExecutor(null); 
        server.start();
        
        System.out.println("Local server started for confidentiality.");
        System.out.println("Listening for XML/JSON requests on http://localhost:8080/pack");
    }

    static class PackHandler implements HttpHandler {
        private final ObjectMapper jsonMapper = new ObjectMapper();
        private final XmlMapper xmlMapper = new XmlMapper();

        @Override
        public void handle(HttpExchange exchange) throws IOException {
            // Only allow POST requests
            if (!"POST".equalsIgnoreCase(exchange.getRequestMethod())) {
                exchange.sendResponseHeaders(405, -1); // 405 Method Not Allowed
                return;
            }

            try {
                // Read the Content-Type header to see if it's XML or JSON
                String contentType = exchange.getRequestHeaders().getFirst("Content-Type");
                InputStream requestBody = exchange.getRequestBody();
                
                
                InputHandler inputData;

                // Parse using the correct mapper
                if (contentType != null && contentType.contains("application/xml")) {
                    inputData = xmlMapper.readValue(requestBody, InputHandler.class);
                } else {
                    // Default to JSON
                    inputData = jsonMapper.readValue(requestBody, InputHandler.class);
                }

                // Run the packing algorithm
                TruckPacker packer = new TruckPacker();
                List<Truck> resultFleet = null;

                if (inputData.getFleet() != null && !inputData.getFleet().isEmpty()) {
                    resultFleet = packer.packMultipleTrucks(inputData.getFleet(), inputData.getInventory());
                } else {
                    // Use Tut 2  algorithm if it a single truck
                    Truck singleTruck = TruckPacker.packTruck(inputData.getTruckVolume(), inputData.getMaxItems(), inputData.getInventory());
                    resultFleet = List.of(singleTruck);
                }

                // Convert the result back to JSON and send it
                String responseBody = jsonMapper.writeValueAsString(resultFleet);
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, responseBody.getBytes().length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(responseBody.getBytes());
                os.close();

            } catch (Exception e) {
                // If anything goes wrong, return a 400 Bad Request
                String errorResponse = "{\"error\": \"Invalid input format or packing failed.\"}";
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(400, errorResponse.getBytes().length);
                
                OutputStream os = exchange.getResponseBody();
                os.write(errorResponse.getBytes());
                os.close();
            }
        }
    }
}