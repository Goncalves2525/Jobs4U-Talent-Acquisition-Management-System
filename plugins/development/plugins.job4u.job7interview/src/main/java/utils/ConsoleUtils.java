package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Adapted version.
 * @author Paulo Maio pam@isep.ipp.pt
 */
public class ConsoleUtils {

    static public String readLineFromConsole(String prompt) {
        try {
            System.out.println();
            System.out.println(prompt);

            InputStreamReader converter = new InputStreamReader(System.in);
            BufferedReader in = new BufferedReader(converter);

            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    static public int readIntegerFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                int value = Integer.parseInt(input);
                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(ConsoleUtils.class.getName()).log(Level.SEVERE,
                        "Wrong input. Please, add an integer number.", ex);
            }
        } while (true);
    }

    static public double readDoubleFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                double value = Double.parseDouble(input);
                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(ConsoleUtils.class.getName()).log(Level.SEVERE,
                        "Wrong input. Please, add a decimal number.", ex);
            }
        } while (true);
    }

    static public float readFloatFromConsole(String prompt) {
        do {
            try {
                String input = readLineFromConsole(prompt);
                float value = Float.parseFloat(input);
                return value;
            } catch (NumberFormatException ex) {
                Logger.getLogger(ConsoleUtils.class.getName()).log(Level.SEVERE,
                        "Wrong input. Please, add a decimal number.", ex);
            }
        } while (true);
    }

    static public Date readDateFromConsole(String prompt) {
        do {
            try {
                String strDate = readLineFromConsole(prompt);
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                Date date = df.parse(strDate);
                return date;
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    static public boolean confirm(String message) {
        String input;
        do {
            input = ConsoleUtils.readLineFromConsole(message);
        } while (!input.equalsIgnoreCase("y") && !input.equalsIgnoreCase("n"));

        return input.equalsIgnoreCase("y");
    }

    static public void buildUiHeader(String header){
        String postHeader = " ";
        for (int i = header.length(); i < 51; i++) {
            postHeader = postHeader.concat("=");
        }
        System.out.println();
        System.out.println(":----------------------------------------------------------:");
        System.out.println("+===== " + header + postHeader + "+");
        System.out.println(":----------------------------------------------------------:");
    }

    static public void buildUiTitle(String title){
        String postHeader = " ";
        for (int i = title.length(); i < 51; i++) {
            postHeader = postHeader.concat("=");
        }
        System.out.println();
        System.out.println("+===== " + title + postHeader + "+");
        System.out.println();
    }

    static public Object showAndSelectOne(List list, String header, String zeroName) {
        showList(list, header, zeroName);
        return selectsObject(list);
    }

    static public Object showAndSelectOneNoCancel(List list, String header) {
        showListNoCancel(list, header);
        return selectsObjectNoCancel(list);
    }

    static public int showAndSelectIndex(List list, String header, String zeroName) {
        showList(list, header, zeroName);
        return selectsIndex(list);
    }

    static public void showList(List list, String header, String zeroName) {
        System.out.println();
        System.out.println(header);
        int index = 0;
        for (Object o : list) {
            index++;
            System.out.println(index + ". " + o.toString());
        }
        System.out.println("0. " + zeroName);
    }

    static public void showListNoCancel(List list, String header) {
        System.out.println();
        System.out.println(header);
        int index = 0;
        for (Object o : list) {
            index++;
            System.out.println(index + ". " + o.toString());
        }
    }

    static public Object selectsObject(List list) {
        String input;
        Integer value;
        do {
            input = ConsoleUtils.readLineFromConsole("Type your option: ");
            value = Integer.valueOf(input);
        } while (value < 0 || value > list.size());

        if (value == 0) {
            return null;
        } else {
            return list.get(value - 1);
        }
    }

    static public Object selectsObjectNoCancel(List list) {
        String input;
        Integer value;
        do {
            try {
                input = ConsoleUtils.readLineFromConsole("Type your option: ");
                value = Integer.valueOf(input);
            } catch (Exception ex) {
                value = 0;
            }
        } while (value < 1 || value > list.size());
        return list.get(value - 1);
    }

    static public int selectsIndex(List list) {
        String input;
        Integer value;
        do {
            input = ConsoleUtils.readLineFromConsole("Type your option: ");
            try {
                value = Integer.valueOf(input);
            } catch (NumberFormatException ex) {
                value = -1;
            }
        } while (value < 0 || value > list.size());
        return value;
    }

    static public final String ANSI_RESET = "\u001B[0m";

    static public void showMessageColor(String message, AnsiColor color) {
        System.out.println(color.getColorValue() + message + ANSI_RESET);
    }
}