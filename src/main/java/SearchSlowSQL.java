import java.io.*;

public class SearchSlowSQL {
    public static void main(String[] args) {
        String fileRead = "E:/自制小工具/SearchSlowSQL/Log_old.txt";
        String fileWrite = "E:/自制小工具/SearchSlowSQL/Log_new.txt";
        String leftMargin = "{executed in ";
        String rightMargin = " msec}";
        int timeMargin = 500;

        ReadAndWrite(fileRead, fileWrite, leftMargin, rightMargin, timeMargin);
    }

    private static void ReadAndWrite(String fileRead, String fileWrite, String leftMargin, String rightMargin, int timeMargin) {
        BufferedReader buff_Reader = null;
        BufferedWriter buff_Writer = null;

        try {
            buff_Reader = new BufferedReader(new FileReader(fileRead));
            buff_Writer = new BufferedWriter(new FileWriter(fileWrite));

            String readLine;
            String writeLine;

            while ((readLine = buff_Reader.readLine()) != null) {
                writeLine = func_TimeCompare(readLine, leftMargin, rightMargin, timeMargin);

                if(writeLine.length() > 0) {
                    buff_Writer.write(writeLine, 0, writeLine.length());
                    buff_Writer.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                assert buff_Reader != null;
                buff_Reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                assert buff_Writer != null;
                buff_Writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static String func_TimeCompare(String readLine, String leftMargin, String rightMargin, int timeMargin) {
        if (readLine.contains(leftMargin)) {
            int startIndex = readLine.indexOf(leftMargin) + leftMargin.length();
            int stopIndex;

            if (readLine.contains(rightMargin)) {
                stopIndex = readLine.lastIndexOf(rightMargin);
            } else {
                stopIndex = readLine.length();
            }

            String TimeExecute_String = readLine.substring(startIndex, stopIndex);
            int TimeExecute = Integer.valueOf(TimeExecute_String.trim());

            if (TimeExecute > timeMargin) {
                return readLine;
            } else {
                return "";
            }
        } else {
            return readLine;
        }
    }
}
