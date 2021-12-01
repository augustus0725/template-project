
# spring

-------------- exchange方法有机会设置header
final RestTemplate restTemplate = new RestTemplate();
HttpHeaders headers = new HttpHeaders();
headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString());
HttpEntity<String> entity = new HttpEntity<>(queryDsl, headers);

HwFtsPage.EsResponse response = restTemplate.exchange(this.queryPath, HttpMethod.POST, entity, HwFtsPage.EsResponse.class).getBody();
---------------

HttpHeaders headers = new HttpHeaders();

headers.add("PRIVATE-TOKEN", this.token);
headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

HttpEntity<GitCommit> entity = new HttpEntity<>(commit, headers);
logger.info("update a file from gitlab: {}", path);
String response = restTemplate.postForObject(this.gitLabUri + "/repository/commits", entity, String.class);
---------------

# fluent-hc, 返回的结果没有用对象序列化, 整体没有spring的方便

<dependency>
    <groupId>org.apache.httpcomponents</groupId>
    <artifactId>fluent-hc</artifactId>
</dependency>

Request.Post("http://targethost/login")
           .bodyForm(Form.form()
                 .add("username",  "vip")
                 .add("password",  "secret").build())  
           .execute().returnContent(); 

Request.Post("")
    .addHeader("", "")
    .addHeader("", "")
    .bodyForm(Form.form().add("one", "two").build())
    .execute().returnContent().asString(Charsets.UTF_8)           