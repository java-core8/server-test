package ru.tcreator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Server {

    public static void main(String[] args) throws Exception {
        final int port = 5002;
        System.out.println("Ожиданем подключения клиента...");
        ServerSocket server = new ServerSocket(port);
        try (
             Socket socket = server.accept();
             BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ) {
            System.out.printf("Подключен клиент на порте: %s \n", socket.getPort());

            out.write("Write your name \n");
            out.flush();

            Optional<String> nameClient = Optional.of(in.readLine());
            out.write("Are you child? (yes/no \n");
            out.flush();

            Optional<String> clientAge = Optional.of(in.readLine());

            clientAge.ifPresentOrElse(
                    str -> {
                        String name = nameClient.get();
                        String answer = null;
                        switch (str) {
                            case "yes" -> answer = String.format("Welcome to the kids area, %s! Let's play!\n", name);
                            case "no" -> answer = String.format("Welcome to the adult zone, %s! Have a good rest, or a good working day!\n", name);
                        }
                        try {
                            out.write(answer);
                            out.flush();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    },
                    () -> System.out.println("Пришёл пустой контейнер")
            );
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
