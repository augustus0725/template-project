# -*- coding: utf-8 -*-
import os
import shutil


def error_handler(function, path, exc_info):
    print("delete {} error: {}".format(path, exc_info))


def rm_recursive(path):
    for root, dirs, files in os.walk(path):
        for entry in dirs:
            if entry == "node_modules" and os.path.exists(os.path.join(root, entry)):
                print("delete...  root: {} dir: {}".format(root, entry))
                shutil.rmtree(os.path.join(root, entry), onerror=error_handler)


def main(path):
    rm_recursive(path)


if __name__ == '__main__':
    main("D:\\")
