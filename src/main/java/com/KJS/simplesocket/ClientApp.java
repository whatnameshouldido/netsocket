package com.KJS.simplesocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public class ClientApp {
    private final static int port = 33333;
//    private final static String serverIp = "127.0.0.1";
    private final static String serverIp = "192.168.0.6";


    public static void main(String[] args) {
        // 클라이언트 소켓을 만든다. (IP주소, 포트번호) (clientSocket)
        // 클라이언트 소켓으로 서버에 접속 시도 한다.
        // 서버와 연결된 클라이언트 소켓으로 읽거나 쓴다.
        // 읽을때는 동기상태 (블로킹)

        try {
            Socket clientSocket = init();
            BufferedWriter writer = new BufferedWriter(
                    new OutputStreamWriter(clientSocket.getOutputStream())
            );
            writer.write(String.format("클라이언트[%s]에서 문자열 전송함", serverIp));
            writer.newLine();
            writer.flush();
            System.out.println("서버에 문자열 전송");

            writer.close();
            clientSocket.close();
        } catch (IOException ioE) {
            System.out.println("IOException");
            System.out.println(ioE.toString());
        } catch (Exception ex) {
            System.out.println("Exception");
            System.out.println(ex.toString());
        } finally {
            System.out.println("클라이언트 프로그램 종료");
        }
    }
    public static Socket init() throws IOException {
        Socket socket = new Socket(serverIp, port);
        System.out.println("클라이언트 소켓 생성후 서버에 접속 성공");
        return socket;
    }
}