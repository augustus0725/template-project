### Run

``` bash
    # 检查配置的主机是不是都可以ping通
    ansible all -i environments/prod/hosts -m ping
    # 在所有的主机上运行命令
    ansible all -i environments/prod/hosts -m command -a uptime
    ansible all -i environments/prod/hosts -a uptime # 可以省略-m command
    ansible all -i environments/prod/hosts -a "tail /var/log/dmesg"
    # 进行部署
    ansible-playbook -i environments/prod main.yml -vvv
    # 查看有哪些任务
    ansible-playbook -i environments/prod main.yml --list-tasks
    # 可以选择从哪个任务继续跑
    ansible-playbook -i environments/prod main.yml --start-at-task "<任务的描述>"
```

