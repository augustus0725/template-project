# -*- coding: utf-8 -*-
import os
import re
import sys

if __name__ == '__main__':
    os.chdir(os.path.join(os.path.dirname(os.path.abspath(__file__)), '..'))
    if os.path.exists("lib"):
        sys.path.append("lib")
    from hello.hello import Hello
    sys.exit(
        Hello.say()
    )
