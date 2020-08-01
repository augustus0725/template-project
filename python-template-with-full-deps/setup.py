# -*- coding: utf-8 -*-
import codecs
import os
import sys

from setuptools import setup, find_packages


def _find_version():
    current_version = "1.0.0"
    if os.path.exists("VERSION"):
        current_version = codecs.open("VERSION", "r", "utf-8").read()
        if not current_version or current_version == "":
            print("VERSION should not be empty!!!")
            sys.exit(-1)
    return current_version


setup(
    name=os.path.realpath(__file__).split(os.path.sep)[-2],
    version=_find_version(),
    packages=find_packages(exclude=("tests",)),
    url='',
    license='',
    author='crazy',
    # pip/setup.py install 的时候自动安装下面的依赖
    install_requires=["requests"],
    # manifest.in sdist的时候包含
    # bdist得时候可以包含用package_data
    # data_files=[('bin', ['bin/run.sh', 'bin/run.bat'])],
    python_requires='>=2.7, <=3',
    zip_safe=False,
    entry_points={
          'console_scripts': [
              'hello = hello.hello:main'
          ]
      },
    author_email='canbin.zhang@qq.com',
    description='demo'
)
