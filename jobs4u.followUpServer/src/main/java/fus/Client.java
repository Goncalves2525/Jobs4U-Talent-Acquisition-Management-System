package fus;

import java.io.*;
import java.net.Socket;

public class Client {
    public static void run() {

        String hostname = "frodo.dei.isep.ipp.pt";
        int port = 25;

        try (Socket socket = new Socket(hostname, port)) {
            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message = input.readLine();
            System.out.println("Server says: " + message);

            sendEmail(socket);

            socket.close();
        } catch (IOException ex) {
            System.out.println("Client exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static void sendEmail(Socket socket) throws IOException {

        // Writing to the server
        OutputStream output = socket.getOutputStream();
        PrintWriter writer = new PrintWriter(output, true);
        writer.println("HELO frodo.dei.isep.ipp.pt");

        BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String message = input.readLine();

        System.out.println("Server says: " + message);

    }
}
