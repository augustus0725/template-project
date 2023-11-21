package com.example;

import org.flowable.engine.*;
import org.flowable.engine.history.HistoricActivityInstance;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.runtime.ProcessInstance;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class FlowableApp {
    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration().setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1").setJdbcUsername("sa").setJdbcPassword("").setJdbcDriver("org.h2.Driver").setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deployment = repositoryService.createDeployment().addClasspathResource("holiday-request.bpmn20.xml").deploy();  /* 不需要重复执行吧, 怎么处理的? */

        Scanner scanner = new Scanner(System.in);

        System.out.println("Who are you?");
        String employee = scanner.nextLine();

        System.out.println("How many holidays do you want to request?");
        Integer nrOfHolidays = Integer.valueOf(scanner.nextLine());

        System.out.println("Why do you need them?");
        String description = scanner.nextLine();

        RuntimeService runtimeService = processEngine.getRuntimeService();

        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("employee", employee);
        variables.put("nrOfHolidays", nrOfHolidays);
        variables.put("description", description);

        // 输入参数 variables, 开始业务流程
        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("holidayRequest", variables);

        // 管理员需要审核, 能查到任务列表
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("You have " + tasks.size() + " tasks:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ") " + tasks.get(i).getName());
        }

        // 管理者界面
        System.out.println("Which task would you like to complete?");
        int taskIndex = Integer.parseInt(scanner.nextLine());
        Task task = tasks.get(taskIndex - 1);
        Map<String, Object> processVariables = taskService.getVariables(task.getId());
        System.out.println(processVariables.get("employee") + " wants " + processVariables.get("nrOfHolidays") + " of holidays. Do you approve this?");

        boolean approved = scanner.nextLine().equalsIgnoreCase("y");
        variables = new HashMap<String, Object>();
        variables.put("approved", approved);
        taskService.complete(task.getId(), variables);

        // =====>  上面的会接着调用外部的一些我们自己实现的方法 org.flowable.CallExternalSystemDelegate

        // 一些审计日志
        HistoryService historyService = processEngine.getHistoryService();
        List<HistoricActivityInstance> activities =
                historyService.createHistoricActivityInstanceQuery()
                        .processInstanceId(processInstance.getId())
                        .finished()
                        .orderByHistoricActivityInstanceEndTime().asc()
                        .list();

        for (HistoricActivityInstance activity : activities) {
            System.out.println(activity.getActivityId() + " took "
                    + activity.getDurationInMillis() + " milliseconds");
        }

        // flowable提供了rest api, 可以独立部署, 独立的微服务
        // 有没自己封装Feign之类的接口, rest调用的方式不方便
        // 自己可以封装一层, 然后提供Feign
        // 自己提供一套语义
        // 意义不大减少不了多少工作量
    }
}
