package com.kosmocourses.java.junior.io.files.lesson.client;

import com.kosmocourses.java.junior.io.files.lesson.server.ServerListener;

import java.io.*;
import java.net.Socket;

public class Client1 {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            String consoleMsg = ConsoleCommand.UNKNOWN.toString();

            while (!consoleMsg.contains(Client2.ConsoleCommand.EXIT.toString())) {

                ConsolReadClient consolReadClient = new ConsolReadClient(socket);
                ServerListenClient serverListenClient = new ServerListenClient(socket);
                serverListenClient.start();
                consolReadClient.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        //if(socket.isOutputShutdown()) System.exit(0);
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
