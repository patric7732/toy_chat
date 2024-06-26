import java.io.*;
import java.net.Socket;

public class ChatClient {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("127.0.0.1", 8008);
        BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("닉네임을 입력해주세요.");
        String name = keyboard.readLine();
        out.println(name);
        new InputThread(socket, in).start();
        try {
            String line = null;
            while ((line = keyboard.readLine()) != null) {
                out.println(line);
                if ("/quit".equals(line)) {
                    break;
                }
            }
            socket.close();
        } catch (Exception ex) {
            System.out.println("...");
        }
    }

}

class InputThread extends Thread {
    private Socket socket;
    BufferedReader in;

    public InputThread(Socket socket, BufferedReader in) {
        this.in = in;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            String line = null;
            while ((line = in.readLine()) != null) {

                System.out.println(line);
            }
        } catch (Exception ex) {
            System.out.println("프로그램을 종료합니다.");
        }
    }
}