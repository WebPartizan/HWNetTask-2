package com.kosmocourses.java.junior.io.files.lesson.server;

import java.net.Socket;

public class NetworkUtils {
    public static String getRemoteClientName(Socket client) {
        return client.getInetAddress() + ":" + client.getPort();
    }
}
