import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class Main {

    public static final String input = "src\\main\\java\\input.json";
    private static final File output = new File("src\\main\\java\\output.json");

    public static void main(String[] args) {

        try {
            // считывание файла JSON
            FileReader reader = new FileReader(input);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject condition = (JSONObject) jsonObject.get("condition");
            JSONArray exclude = (JSONArray) condition.get("exclude");
            JSONArray include = (JSONArray) condition.get("include");
            JSONArray sort_by = (JSONArray) condition.get("sort_by");

            ObjectMapper objectMapper = new ObjectMapper();

            if (exclude != null) {
                objectMapper.writeValue(output, ExcludeType.sortData());

            } else if (include != null) {
                objectMapper.writeValue(output, IncludeType.sortData());

            } else System.out.println("Wrong JSON string!");

        } catch (IOException | ParseException | NullPointerException ex) {
            ex.printStackTrace();
        }
    }
}