# 1.
python3 -m venv venv
antlr -Dlanguage=Python3 PlSqlLexer.g4 PlSqlParser.g4
# 2. 用pycharm打开这个项目
pip3 install antlr4-python3-runtime==4.8
# 3. 打包