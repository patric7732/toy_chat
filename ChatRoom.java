import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ChatRoom {
    private int id;
    private String title;
    protected List<ChatThread> chatThreadList;
    private BufferedWriter logWriter;

    public ChatRoom(int id, String title) {
        chatThreadList = new ArrayList<>();
        this.title = title;
        this.id = id;
        try {
            this.logWriter = new BufferedWriter(new FileWriter("chatLog/chatroom_" + id + "_log.txt", true));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void logMessage(String sender, String message) {
        try {
            logWriter.write(new Date() + " | " + sender + ": " + message + "\n");
            logWriter.flush();
        } catch (IOException e) {
            System.out.println("채팅 내역 저장 에러: " + e.getMessage());
        }
    }

    public void broadcast(String sender, String msg) {
        for (ChatThread chatThread : chatThreadList) {
            chatThread.sendMessage(sender + " : " + msg);
        }
        logMessage(sender, msg);
    }

    public void addChatThread(ChatThread chatThread) {
        chatThreadList.add(chatThread);
        chatThread.setChatRoom(this);
    }

    public void removeChatThread(ChatThread chatThread) {
        chatThreadList.remove(chatThread);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "방 id : " + id +
                ", 방의 제목 : " + title;
    }
    public void broadcastEnterMessage(String nickName) {
        String message = nickName + "님이 입장했습니다.";
        broadcastSystemMessage(message);
    }

    public void broadcastExitMessage(String nickName) {
        String message = nickName + "님이 퇴장했습니다.";
        if (logWriter != null) {
            try {
                logWriter.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        broadcastSystemMessage(message);
    }

    private void broadcastSystemMessage(String message) {
        for (ChatThread chatThread : chatThreadList) {
            chatThread.sendMessage("[시스템] " + message);
        }
    }
}
