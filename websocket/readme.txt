# springboot�����������Ӵ���ĳ���
client
    - ��������/topic, callback -> �첽���ؽ��
    - Զ�̵��� (�첽)

// �ͻ�����������, ��������/topic, �첽����
function sendName() {
    stompClient.publish({
        destination: "/app/hello",
        body: JSON.stringify({'name': $("#name").val()})
    });
}


/*
  @MessageMapping("/hello")
  @SendTo("/topic/greetings")
  public Greeting greeting(HelloMessage message) throws Exception {
    Thread.sleep(1000); // simulated delay
    return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
  }
*/



# ��һ�����õĳ���
server:
   - ����ʵʱ����  -> /topic

client:
   - ��������/topic, callback �첽����UI

���ֳ���ǰ�˲���Ҫ�仯, �����Ҫʵʱ�� ����/topic �﷢������
������=> 
// ȡ�� @SendTo ���ע��
this.simpMessagingTemplate.convertAndSend("/topic/news", message)



## ?? ���ֶ��ĵĳ����ǲ���ֱ���ÿͻ�����kafka/rabbitmq/...��ֱ��