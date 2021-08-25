逻辑

gettext是开源老大gnu给的i18n的一个方案
- 代码里出现字符串的地方  用标识 gettext("hello world")    <- 字符串先用英语 形成一个英语版本
- pybabel extract . -o my.pot                              <- 产生一个基模板 (从代码里抽取gettext)
- pybabel init -i my.pot -d . -l zh_CN                     <- 产生具体的某个方言版本 (文本版本)
- pybabel compile -d . -l zh_CN                            <- 编译成具体的某个方言版本 (二进制版本) 