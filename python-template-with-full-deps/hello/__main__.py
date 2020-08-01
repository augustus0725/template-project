# -*- coding: utf-8 -*-
import sys

from hello.hello import Hello


def main(args=None):
    """The main routine."""
    if args is None:
        args = sys.argv[1:]
    print("args : " + str(args))
    Hello.say()


if __name__ == '__main__':
    main()
