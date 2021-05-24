package ru.tcreator;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Optional;

public class Client {
    static Socket socket;
    public static void main(String[] args) throws IOException {
        InetAddress host = InetAddress.getByName("netology.homework");
        final int port = 5002;
        socket = new Socket(host, port);
        try (
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in));
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));) {
            System.out.println("Клиент подключился к серверу на порту: " + socket.getPort());


            while (true) {
                Optional<String> fromServer = Optional.of(in.readLine());
                fromServer.ifPresent(System.out::println);

                Optional<String> fromConsole = Optional.of(terminalReader.readLine());
                fromConsole.ifPresentOrElse(
                        element -> {
                            try {
                                out.write(element + '\n');
                                out.flush();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        },
                        () -> System.out.println("нет данных с консоли")
                );
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
