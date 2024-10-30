package com.KJS.simplesocket;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ClientApp {
//    private final static int port = 33333;
//    private final static String serverIp = "127.0.0.1";
//    private final static String serverIp = "192.168.0.6";  //민준이 서버 IP
//    private final static String serverIp = "192.168.0.10";  //강사님 서버 IP

    private Socket clientSocket = null;
    private BufferedWriter socketWriter = null;
    private BufferedReader socketReader = null;
    private BufferedReader keyboardReader = null;

    public static void main(String[] args) {
        // 클라이언트 소켓을 만든다. (IP주소, 포트번호) (clientSocket)
        // 클라이언트 소켓으로 서버에 접속 시도 한다.
        // 서버와 연결된 클라이언트 소켓으로 읽거나 쓴다.
        // 읽을때는 동기상태 (블로킹)

        try {
            if (args.length != 2) {
                System.out.println("에러 : IP주소와 포트(숫자) 를 입력 하세요 (예 : java -cp . com.softagape.simplesocket.ServerApp IP주소 포트번호)");
            } else {
                String serverIp = args[0];
                Integer port = Integer.parseInt(args[1]);
                ClientApp ca = new ClientApp();
                ca.doNetworking(serverIp, port);
            }
        } catch (NumberFormatException ex) {
            System.out.println("에러 : IP주소와 포트(숫자) 를 입력 하세요 (예 : java -cp . com.softagape.simplesocket.ServerApp IP주소 포트번호)");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
    }

    public void init(String serverIp, Integer port) throws IOException {
        this.clientSocket = new Socket(serverIp, port);
        System.out.println("클라이언트 소켓 생성후 서버에 접속 성공");

        socketWriter = new BufferedWriter(
                new OutputStreamWriter(clientSocket.getOutputStream())
        );
        socketReader = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream())
        );
        keyboardReader = new BufferedReader(
                new InputStreamReader(System.in)
        );
        socketWriter.write(String.format("클라이언트[%s] 에서 첫 문자열 전송 함", getMyIp()));
        socketWriter.newLine();
        socketWriter.flush();
    }
    
    private String getMyIp() {
        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        }
        catch ( UnknownHostException e ) {
            e.printStackTrace();
        }
        if( local == null ) {
            return "0.0.0.0";
        }
        else {
            String ip = local.getHostAddress();
            return ip;
        }

    }

    public void doNetworking(String serverIp, Integer port) {
        try {
            this.init(serverIp, port);

            Thread rsst = new Thread(new ReadServerSocketThread());
            rsst.start();

            while (true) {
//                String readMsg = socketReader.readLine(); // 블로킹 상태
//                System.out.printf("서버 에서 받은 문자열 : %s%n", readMsg);
//                if( "exit".equalsIgnoreCase(readMsg) ) {
//                    break;
//                }
                System.out.print("클라이언트에서 문자열 입력 : ");
                String keyboardMsg = this.keyboardReader.readLine(); // 블로킹 상태
                this.socketWriter.write(keyboardMsg);
                this.socketWriter.newLine();
                this.socketWriter.flush();
                if( "exit".equalsIgnoreCase(keyboardMsg) ) {
                    break;
                }
            }

        } catch (IOException ioE) {
            System.out.println("IOException");
            System.out.println(ioE.toString());
        } catch (Exception ex) {
            System.out.println("Exception");
            System.out.println(ex.toString());
        } finally {
            closeAll();
            System.out.println("클라이언트 프로그램 종료");
        }
    }

    public void closeAll() {
        try {
            if (keyboardReader != null) {
                keyboardReader.close();
            }
            if (socketReader != null) {
                socketReader.close();
            }
            if (socketWriter != null) {
                socketWriter.close();
            }
            if (clientSocket != null) {
                clientSocket.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    class ReadServerSocketThread implements Runnable {
        @Override
        public void run() {
            while(true) {
                try {
                    String readMsg = socketReader.readLine(); // 블로킹 상태
                    System.out.printf("서버 에서 받은 문자열 : %s%n", readMsg);
                    if ("exit".equalsIgnoreCase(readMsg)) {
                        System.exit(-1);
                    }
                } catch (IOException ex) {
                    closeAll();
                    break;
                }
            }
        }
    }
}

// javac -d . ClientApp.java
// java -cp . com.softagape.simplesocket.ClientApp
