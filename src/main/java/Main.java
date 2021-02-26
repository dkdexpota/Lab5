import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.*;
import java.io.*;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

/**
 * Главный класс: создание очереди из файла.
 * @author я
 */
public class Main {
    public static void main(String[] args) throws IOException {
        boolean go = false;
        Scanner scan = null;
        try {
            scan = new Scanner(new File("E:/JP/M5/src/main/java/kavo.json"));
            go = true;
        } catch (FileNotFoundException e) {
            System.out.println("Файл не найден");
        }
        if (go) {
            String jsstr = new String();
            SortedSet<Integer> all_id = new TreeSet<>();
            while (scan.hasNextLine()) {
                jsstr = jsstr + scan.nextLine();
            }
            scan.close();
            Gson g = new GsonBuilder()
                    .setPrettyPrinting()
                    .registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
                        @Override
                        public void write(JsonWriter out, ZonedDateTime value) throws IOException {
                            out.value(value.toString());
                        }

                        @Override
                        public ZonedDateTime read(JsonReader in) throws IOException {
                            return ZonedDateTime.parse(in.nextString());
                        }
                    })
                    .enableComplexMapKeySerialization()
                    .create();
            PriorityQueue<SpaceMarine> pQueue = new PriorityQueue(new SpaceMarineComparator());
            LocalDateTime time_create = LocalDateTime.now();
            if (jsstr.length() != 0) {
                for (SpaceMarine obj : g.fromJson(jsstr, SpaceMarine[].class)) {
                    pQueue.add(obj);
                    all_id.add(obj.getId());
                }
            }
            Treatment.treatment(pQueue, all_id, g, time_create);
        }
    }
}