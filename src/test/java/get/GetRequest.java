package get;

import java.net.URL;

import java.io.*;
import java.net.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import org.testng.annotations.Test;

public class GetRequest {
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

                // Parse the JSON response using org.json.simple
                JSONParser parser = new JSONParser();
                JSONObject jsonResponse = (JSONObject) parser.parse(content.toString());

                // Get the total number of pages
                totalPages = ((Long) jsonResponse.get("total_pages")).intValue();

                // Get the "data" array from the response
                JSONArray data = (JSONArray) jsonResponse.get("data");

                // Iterate through each record in the "data" array
                for (Object obj : data) {
                    JSONObject record = (JSONObject) obj;
                    JSONObject vitals = (JSONObject) record.get("vitals");

                    // Get the bloodPressureDiastolic value
                    long bloodPressureDiastolic =(Long) vitals.get("bloodPressureDiastole");

                    // Check if the value is within the specified range
                    if (bloodPressureDiastolic >= lowerlimit && bloodPressureDiastolic <= upperlimit) {
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

    @Test
    public void test() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
//        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(System.getenv("OUTPUT_PATH")));
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(System.out));

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
