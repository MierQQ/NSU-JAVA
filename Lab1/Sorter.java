import java.io.*;

public class Sorter {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.print("Bad number of args");
            return;
        }
        Reader reader = null;
        Writer writer = null;
        try {
            reader = new InputStreamReader(new FileInputStream(args[0]));
            writer = new OutputStreamWriter(new FileOutputStream(args[1]));
            CSVAnalyzer analyzer = new CSVAnalyzer(reader, writer);
            analyzer.analyze();
        } catch (IOException exception){
            System.err.print("Error while analyzing file" + exception.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException exception) {
                    exception.printStackTrace(System.err);
                }
            }
            if (null != writer) {
                try {
                    writer.close();
                } catch (IOException exception) {
                    exception.printStackTrace(System.err);
                }
            }
        }
    }
}
