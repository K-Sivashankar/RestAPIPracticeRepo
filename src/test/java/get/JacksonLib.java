package get;

import java.io.*;
import java.net.*;
import com.fasterxml.jackson.databind.*;

public class JacksonLib {
    public static int healthCheckup(int lowerlimit, int upperlimit) {
        int count = 0;
        int page = 1;
        int totalPages = 1;

        try {
            ObjectMapper mapper = new ObjectMapper();

            while (page <= totalPages) {
                // Construct the URL for the API request
                URL url = new URL("https://jsonmock.hackerrank.com/api/medical_records?page=" + page);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.connect();

                // Read the API response
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder content = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                in.close();
                conn.disconnect();

                // Parse the JSON response using Jackson
                JsonNode jsonResponse = mapper.readTree(content.toString());

                // Get the total number of pages
                totalPages = jsonResponse.get("total_pages").asInt();

                // Get the "data" array from the response
                JsonNode data = jsonResponse.get("data");

                // Iterate through each record in the "data" array
                for (JsonNode record : data) {
                    JsonNode vitals = record.get("vitals");

                    // Get the bloodPressureDiastole value
                    int bloodPressureDiastole = vitals.get("bloodPressureDiastole").asInt();

                    // Check if the value is within the specified range
                    if (bloodPressureDiastole >= lowerlimit && bloodPressureDiastole <= upperlimit) {
                        count++;
                    }
                }

                // Move to the next page
                page++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return count;
    }

    public static void main(String[] args) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));

        // Read input values for lowerlimit and upperlimit
        int lowerlimit = Integer.parseInt(bufferedReader.readLine().trim());
        int upperlimit = Integer.parseInt(bufferedReader.readLine().trim());

        // Call the healthCheckup function
        int result = healthCheckup(lowerlimit, upperlimit);

        // Write the result to the output
        bufferedWriter.write(String.valueOf(result));
        bufferedWriter.newLine();

        // Close resources
        bufferedReader.close();
        bufferedWriter.close();
    }
}
