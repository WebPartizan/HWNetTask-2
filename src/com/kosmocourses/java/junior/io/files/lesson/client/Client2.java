package com.kosmocourses.java.junior.io.files.lesson.client;

import com.kosmocourses.java.junior.io.files.lesson.server.ServerListener;

import java.io.*;
import java.net.Socket;

public class Client2 {

    public static void main(String[] args) {
        try {
            try ( Socket socket = new Socket("localhost", 10000);
                  BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                  BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                  BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            ){
                String consoleMsg = ConsoleCommand.UNKNOWN.toString();

                while (!consoleMsg.contains(ConsoleCommand.EXIT.toString())) {
                    System.out.println("Enter you message:");
                    consoleMsg = reader.readLine();
                    out.write(consoleMsg + System.lineSeparator());
                    out.flush();

                    String serverMsg = in.readLine();
                    System.out.println(serverMsg);
                }

                out.write(ServerListener.ServerCommand.EXIT + System.lineSeparator());
                out.flush();

            } finally {
                System.out.println("Close connection...");
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

        public static ConsoleCommand toEnum(String cmd){
            switch (cmd.trim().toUpperCase()){
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
