package hello;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class MyWsHandler extends TextWebSocketHandler {
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(new DaemonThreadFactory());

    // 所有会话的句柄都保存了，可以往所有连接的客户端发送数据
    private Set<WebSocketSession> sessions = Collections.synchronizedSet(new HashSet<>());

    public MyWsHandler() {
        scheduler.scheduleAtFixedRate(new Runnable() {
                                          @Override
                                          public void run() {
                                              for (WebSocketSession session : sessions) {
                                                  try {
                                                      session.sendMessage(new TextMessage("\"hello\""));
                                                  } catch (IOException e) {
                                                      e.printStackTrace();
                                                  }
                                              }
                                          }
                                      },
                1,
                1,
                TimeUnit.SECONDS
        );
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        super.handleTextMessage(session, message);
        // TODO 如果有需要, 在这里处理单个会话
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        sessions.remove(session);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        sessions.add(session);
    }

    /**
     * 调度线程设置为守护线程，可以让外面的tomcat挂掉之后线程直接退出
     *
     */
    private class DaemonThreadFactory implements ThreadFactory {
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        }
    }

    // do test
    private void run() throws InterruptedException {
        Thread.sleep(99999999);
    }

    public static void main(String[] args) throws InterruptedException {
        new MyWsHandler().run();
    }
}
