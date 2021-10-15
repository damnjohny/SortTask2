import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;


public class Main {

    public static final String input = "src\\main\\java\\input.json";
    private static final File output = new File("src\\main\\java\\output.json");

    public static void main(String[] args) {

        try {
            // считывание данных из входящего файла "input.json"
            FileReader reader = new FileReader(input);

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject condition = (JSONObject) jsonObject.get("condition");
            JSONArray exclude = (JSONArray) condition.get("exclude");
            JSONArray include = (JSONArray) condition.get("include");

            ObjectMapper objectMapper = new ObjectMapper();

            // выполнение логики программы в зависимости от ключа с последующей записью результата в файл "output.json"
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