# springboot官网给的例子处理的场景
client
    - 订阅主题/topic, callback -> 异步返回结果
    - 远程调用 (异步)

// 客户端主动触发, 订阅主题/topic, 异步更新
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



# 另一个常用的场景
server:
   - 产生实时数据  -> /topic

client:
   - 订阅主题/topic, callback 异步更新UI

这种场景前端不需要变化, 后端需要实时往 主题/topic 里发送数据
方法：=> 
// 取代 @SendTo 这个注解
this.simpMessagingTemplate.convertAndSend("/topic/news", message)



## ?? 这种订阅的场景是不是直接让客户端用kafka/rabbitmq/...最直接