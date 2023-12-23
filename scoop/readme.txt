# 安装scoop  (powershell)
> Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
> Invoke-RestMethod -Uri https://get.scoop.sh | Invoke-Expression

# 添加java源
scoop bucket add java
scoop search jdk
scoop install corretto8-jdk

# 查看比较知名的源
scoop bucket known

# 不太知名的源
scoop bucket add extras

# 添加versions的bucket之后能获取单个软件的多个版本
scoop bucket add versions

# 切换版本
scoop reset <指定的版本的软件> ==> 比如有两个 scala scala212, 可以用reset切换