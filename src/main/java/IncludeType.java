import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

public class IncludeType {

    private static final SortedMap<String, String> sortedMap = new TreeMap<>();


    public static JSONObject sortData() {

        try {

            FileReader reader = new FileReader(Main.input);

            var jsonParser = new JSONParser();
            var jsonObject = (JSONObject) jsonParser.parse(reader);

            JSONObject condition = (JSONObject) jsonObject.get("condition");
            JSONArray exclude = (JSONArray) condition.get("exclude");
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
            Iterator dataIterator = data.iterator();

            String key = "";
            String value = "";
            while (dataIterator.hasNext()) {
                JSONObject inerObj = (JSONObject) dataIterator.next();
                key = (String) inerObj.get(includeKey); // John
                value = (String) inerObj.get(sort_byKey); // john2@mail.com

                if (key.equals(includeValue)) {
                    sortedMap.put(value, key);

                }
            }
            JSONArray resultArray = new JSONArray();

            // из сортированого списка записываем обьекты в JSON массив
            for (Map.Entry<String, String> next : sortedMap.entrySet()) {
                JSONObject object = new JSONObject();
                object.put(includeKey, next.getValue());
                object.put(sort_byKey, next.getKey());
                resultArray.add(object);
            }

            JSONObject resultObject = new JSONObject();
            resultObject.put("result", resultArray);
            System.out.println(resultObject);

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return null;
    }
}