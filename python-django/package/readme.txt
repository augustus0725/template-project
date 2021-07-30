# 打包

mkdir -p package/django-polls

cp -a mysite/polls package/django-polls

# 
python setup.py sdist
