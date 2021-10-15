import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class ExcludeType implements Comparator<String> {

    private static final List<JSONObject> jsonObjects = new ArrayList<>();

    public static JSONObject sortData() {
        try {

            FileReader reader = new FileReader(Main.input);

            var jsonParser = new JSONParser();
            var jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject condition = (JSONObject) jsonObject.get("condition");
            JSONArray exclude = (JSONArray) condition.get("exclude");
            JSONArray sort_by = (JSONArray) condition.get("sort_by");

            // получение ключа сортировки
            String sort_byObject = (String) sort_by.iterator().next();// "rating"

            JSONObject excludeObject = (JSONObject) exclude.get(0);
            String excludeKey = excludeObject.keySet().toString().replaceFirst("\\[", "")
                    .replaceFirst("\\]", ""); // disable
            String excludeValue = excludeObject.values().toString().replaceFirst("\\[", "")
                    .replaceFirst("\\]", ""); // false/true

            // получение массива
            JSONArray data = (JSONArray) jsonObject.get("data");
            var iterator = data.iterator();

            while (iterator.hasNext()) {
                JSONObject innerObject = (JSONObject) iterator.next();

                if (!innerObject.get(excludeKey).toString().equals(excludeValue.toString()))
                    jsonObjects.add(innerObject);
            }

            jsonObjects.sort(new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject jsonObject, JSONObject t1) {
                return jsonObject.get(sort_byObject).toString().compareTo(t1.get(sort_byObject).toString());
                }
            });

            JSONObject resultObject = new JSONObject();
            resultObject.put("result", jsonObjects);

            return resultObject;

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int compare(String s, String t1) {
        return s.compareTo(t1);
    }
}