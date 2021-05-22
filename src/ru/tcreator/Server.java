package ru.tcreator;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class Server {

    public static void main(String[] args) throws Exception {
        final int port = 5002;
        System.out.println("Ожиданем подключения клиента...");
        try(ServerSocket server = new ServerSocket(port);
            Socket socket = server.accept();
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader terminalReader = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.printf("Подключен клиент на порте: %s \n", socket.getPort());
            Optional<String> fromClient = Optional.of(in.readLine());
            fromClient.ifPresentOrElse(
                    str -> System.out.println(str.toString()),
                    () -> System.out.println("Пришёл пустой контейнер")
            );
//            Optional<String> termStringToClient = Optional.of(terminalReader.readLine());
//            if(termStringToClient.isPresent()) {
//                out.write(termStringToClient.toString());
//            }
//            out.flush();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
