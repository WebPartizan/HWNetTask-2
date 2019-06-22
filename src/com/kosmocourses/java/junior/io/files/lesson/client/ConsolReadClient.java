package com.kosmocourses.java.junior.io.files.lesson.client;

import java.io.*;
import java.net.Socket;

public class ConsolReadClient extends Thread {
    private Socket client;
    private BufferedWriter out;
    private BufferedReader reader;

    public ConsolReadClient(Socket socket) throws IOException {
        try {
            this.client = socket;
            reader = new BufferedReader(new InputStreamReader(System.in));
            out = new BufferedWriter(new OutputStreamWriter(client.getOutputStream()));
        } catch (IOException e) {
            closeConnection(socket);
            throw e;
        }
    }

    private void closeConnection(Socket socket) throws IOException {
        out.close();
        socket.close();
    }


    @Override
    public void run() {
        try {
            try {
                String consoleMsg = ConsoleCommand.UNKNOWN.toString();
                while (!consoleMsg.contains(ConsoleCommand.EXIT.toString())) {
                    sleep(500);
                    System.out.println("Enter you message:");
                    consoleMsg = reader.readLine();
                    if (!consoleMsg.equals(".UNKNOWN")) {
                        out.write(consoleMsg + System.lineSeparator());
                        out.flush();
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                System.out.println("Close connection...");
                closeConnection(client);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public enum ConsoleCommand {
        UNKNOWN(".UNKNOWN"),
        EXIT(".EXIT");

        private String txt;

        ConsoleCommand(String txt) {
            this.txt = txt;
        }

        public static ConsoleCommand toEnum(String cmd) {
            switch (cmd.trim().toUpperCase()) {
                case "EXIT":
                    return EXIT;
                default:
                    return UNKNOWN;
            }
        }

        @Override
        public String toString() {
            return txt;
        }
    }
}

