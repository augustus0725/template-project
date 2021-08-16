Flask Web 开发的大型应用的示例

# 环境
python3 -m venv venv
pip3 install -r requirements.txt

# run
$env:FLASK_APP = "flasky.py"
$env:FLASK_ENV = "development"

flask db init
flask db migrate -m "init migration"
flask db upgrade