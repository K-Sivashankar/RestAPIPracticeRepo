package get;

import java.io.*;
import java.net.*;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

public class GsonLibrary {
    public static int healthCheckup(int lowerlimit, int upperlimit) {
        int count = 0;
        int page = 1;
        int totalPages = 1;

        try {
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

                // Parse the JSON response using Gson
                Gson gson = new Gson();
                JsonObject jsonResponse = gson.fromJson(content.toString(), JsonObject.class);

                // Get the total number of pages
                totalPages = jsonResponse.get("total_pages").getAsInt();

                // Get the "data" array from the response
                JsonArray data = jsonResponse.getAsJsonArray("data");

                // Iterate through each record in the "data" array
                for (JsonElement element : data) {
                    JsonObject record = element.getAsJsonObject();
                    JsonObject vitals = record.getAsJsonObject("vitals");

                    // Get the bloodPressureDiastole value
                    int bloodPressureDiastole = vitals.get("bloodPressureDiastole").getAsInt();

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
