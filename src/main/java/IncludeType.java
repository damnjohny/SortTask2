import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class IncludeType implements Comparator<String> {

    private static final SortedMap<String, String> sortedMap = new TreeMap<>();
    private static final List<JSONObject> jsonObjects = new ArrayList<>();

    public static JSONObject sortData() {

        try {

            FileReader reader = new FileReader(Main.input);

            var jsonParser = new JSONParser();
            var jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject condition = (JSONObject) jsonObject.get("condition");
            JSONArray include = (JSONArray) condition.get("include");
            JSONArray sort_by = (JSONArray) condition.get("sort_by");

            // получение искомый элемент
            JSONObject includeKeyAndValue = (JSONObject) include.iterator().next();
            String includeKey = includeKeyAndValue.keySet().toString().replaceFirst("\\[", "")
                    .replaceFirst("\\]", ""); // name

            // получение значение искомого элемента в массиве
            String includeValue = includeKeyAndValue.values().toString().replaceFirst("\\[", "")
                    .replaceFirst("\\]", ""); // John

            // получение ключа сортировки
            String sort_byKey = (String) sort_by.get(0); // email

            // получение массива
            JSONArray data = (JSONArray) jsonObject.get("data");

            // берем каждое значение из массива json отдельно
            var iterator = data.iterator();

            while (iterator.hasNext()) {
                JSONObject innerObj = (JSONObject) iterator.next();

                if (innerObj.get(includeKey).toString().equals(includeValue.toString())) {
                    jsonObjects.add(innerObj);
                }
            }

            jsonObjects.sort(new Comparator<JSONObject>() {
                @Override
                public int compare(JSONObject jsonObject, JSONObject t1) {
                    return jsonObject.get(sort_byKey).toString().compareTo(t1.get(sort_byKey).toString());
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