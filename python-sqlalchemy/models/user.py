# -*- coding: utf-8 -*-
from sqlalchemy import Column, String, create_engine
from sqlalchemy.orm import sessionmaker
from sqlalchemy.ext.declarative import declarative_base


# 创建对象的基类:
Base = declarative_base()


# 定义User对象:
class User(Base):
    # 表的名字:
    __tablename__ = 'user'

    # 表的结构:
    id = Column(String(20), primary_key=True)
    name = Column(String(20))


# 初始化数据库连接:
# mysql
# engine = create_engine('mysql+mysqlconnector://root:password@localhost:3306/test')
# sqlite
engine = create_engine('sqlite:///foo.db')
# 创建DBSession类型:
DBSession = sessionmaker(bind=engine)

if __name__ == '__main__':
    # 使用这个命令创建表结构
    Base.metadata.create_all(engine)
    # session对象来操作crud
    session = DBSession()

    # user 对象
    user = User()
    user.id = 'two'
    user.name = 'sabo'
    session.add(user)
    session.commit()

    # 查询
    print(session.query(User).all())

    print("")
