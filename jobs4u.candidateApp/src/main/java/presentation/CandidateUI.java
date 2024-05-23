package presentation;

import console.ConsoleUtils;
import textformat.AnsiColor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class CandidateUI {

    public CandidateUI() {
    }

    public void doShow() {
//        do {
//
//            String hostname = "localhost";
//            int port = 99;
//
//            try (Socket socket = new Socket(hostname, port)) {
//
//                OutputStream output = socket.getOutputStream();
//                do {
//                    output.write(ConsoleUtils.readIntegerFromConsole("Type a code:"));
//                    BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//                    String message = input.readLine();
//                    System.out.println("Server says: " + message);
//                } while (ConsoleUtils.confirm("Continue? (y/n)"));
//
//                socket.close();
//            } catch (IOException ex) {
//                System.out.println("Client exception: " + ex.getMessage());
//                ex.printStackTrace();
//            }
//        } while (!ConsoleUtils.confirm("Do you want to exit? (y/n)"));
        while(ConsoleUtils.confirm("I've started candidate UI. Continue? (y/n)")){
            ConsoleUtils.showMessageColor("Looping", AnsiColor.CYAN);
        }
    }
}
