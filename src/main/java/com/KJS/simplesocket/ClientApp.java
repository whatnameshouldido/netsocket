package com.KJS.simplesocket;

import java.io.*;
import java.net.Socket;

public class ClientApp {
    private final static int port = 33333;
    private final static String serverIp = "127.0.0.1";

    public static void main(String[] args) {
        // 클라이언트 소켓을 만든다. (IP 주소, 포트번호) (clientSocket)
        // 클라이언트 소켓으로 서버에 접속 시도 한다.
        // 서버와 연결된 클라이언트 소켓으로 읽거나 쓴다.
        // 읽을때는 동기상태 (블로킹)

        try {
            Socket clientSocket = init();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            writer.write(String.format("클라이언트[%s] 접속함", serverIp));
            writer.flush();
            writer.close();
            clientSocket.close();
        } catch (IOException ioE) {
            System.out.println("IOException");
            System.out.println(ioE.toString());
        } catch (Exception ex) {
            System.out.println("Exception");
            System.out.println(ex.toString());
        }
    }
    public static Socket init() throws IOException {
        Socket socket = new Socket(serverIp, port);
        return socket;
    }

}
