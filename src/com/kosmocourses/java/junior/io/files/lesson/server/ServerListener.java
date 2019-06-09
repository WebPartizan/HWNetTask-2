package com.kosmocourses.java.junior.io.files.lesson.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerListener {

    private static LinkedList<ServerWorker> workers = new LinkedList<>();

    private static boolean isRunned = true;

    public static void main(String[] args) {
        try (
                ServerSocket server = new ServerSocket(10000)
        ) {
            System.out.println("Server was running");

            while(isRunned) {
                workers.add(new ServerWorker(server.accept()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Server listener was stopped");
        }
    }

    public static Collection<ServerWorker> getWorkers() {
        return Collections.unmodifiableList(workers);
    }

    synchronized public static void setIsRunned(boolean isRunned) {
        ServerListener.isRunned = isRunned;
    }

    public enum ServerCommand {
        UNKNOWN(".UNKNOWN"),
        START(".START"),
        EXIT(".EXIT");

        private String txt;

        ServerCommand(String txt) {
            this.txt = txt;
        }

        public static ServerCommand toEnum(String cmd){
            switch (cmd.trim().toUpperCase()){
                case ".EXIT":
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
