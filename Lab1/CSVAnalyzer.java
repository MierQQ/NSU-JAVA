import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.*;
import java.util.stream.Collectors;

public class CSVAnalyzer {
    private final Reader reader;
    private final Writer writer;
    private long count = 0;
    private final Map<String, Integer> map;
    private void incrementMapValue(String word){
        if (map.containsKey(word)){
            map.put(word, map.get(word)+1);
        } else {
            map.put(word, 1);
        }
    }
    private void fillMap() throws IOException{
        StringBuilder word = new StringBuilder();
        int ch;
        while (reader.ready()){
            ch = reader.read();
            if (Character.isLetterOrDigit((char)ch)){
                word.append((char)ch);
            } else if (word.length() != 0) {
                incrementMapValue(word.toString());
                ++count;
                word.delete(0,word.length());
            }
        }
        if (word.length() != 0) {
            incrementMapValue(word.toString());
            ++count;
        }

    }
    private void printResult() throws IOException{
        Comparator<Map.Entry<String, Integer>> cp = new Comparator<Map.Entry<String, Integer>>() {
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                if (o2.getValue() - o1.getValue() != 0) {
                    return o2.getValue() - o1.getValue();
                } else {
                    return o1.getKey().compareTo(o2.getKey());
                }
            }
        };
        List<Map.Entry<String, Integer>> sortedMapList = map.entrySet().stream().sorted(cp).collect(Collectors.toList());
        for (var it : sortedMapList){
            writer.write(it.getKey() + ";" + it.getValue().toString() + ";" + (100 * (double)it.getValue()/count) + "%\n");
        }
    }
    public CSVAnalyzer(Reader reader, Writer writer) {
        map = new HashMap<>();
        this.reader = reader;
        this.writer = writer;
    }
    public void analyze() throws IOException {
        fillMap();
        printResult();
    }
}
