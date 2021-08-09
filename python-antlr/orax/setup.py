from setuptools import setup

with open('requirements.txt') as f:
    required = f.read().splitlines()

setup(
    install_requires=required,
    entry_points={
        'console_scripts': [
            'orax = orax.__main__:main'
        ]
    }, )
