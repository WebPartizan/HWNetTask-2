package com.kosmocourses.java.junior.io.files.lesson.client;

import java.io.*;
import java.net.Socket;

public class Client2 {

    public static void main(String[] args) {
        Socket socket = null;
        try {
            socket = new Socket("localhost", 10000);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {

            ServerListenClient serverListenClient = new ServerListenClient(socket);
            serverListenClient.start();
            ConsolReadClient consolReadClient = new ConsolReadClient(socket);
            consolReadClient.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
