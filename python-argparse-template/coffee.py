#!/usr/bin/env python
# -*- coding: utf-8 -*-
import argparse
import sys

"""
name or flags - Either a name or a list of option strings, e.g. foo or -f, --foo.
action - The basic type of action to be taken when this argument is encountered at the command line.
nargs - The number of command-line arguments that should be consumed.
const - A constant value required by some action and nargs selections.
default - The value produced if the argument is absent from the command line.
type - The type to which the command-line argument should be converted.
choices - A container of the allowable values for the argument.
required - Whether or not the command-line option may be omitted (optionals only).
help - A brief description of what the argument does.
metavar - A name for the argument in usage messages.
dest - The name of the attribute to be added to the object returned by parse_args().
"""


def handle_config(args):
    print args.b


def git():
    parser = argparse.ArgumentParser(description=u'子命令')
    subparsers = parser.add_subparsers()
    parser_branch = subparsers.add_parser('branch', help=u'manage branch')
    parser_branch.add_argument('-a', '--all', help=u'find all branch')
    # 参数之间的依赖
    parser_config = subparsers.add_parser('config', help=u'manage branch')
    parser_config.add_argument('-b', type=int)
    # 子命令设置处理参数的方法
    parser_config.set_defaults(func=handle_config)
    args = parser.parse_args()
    args.func(args)



def main():
    parser = argparse.ArgumentParser(description=u'Process some integers.')
    parser.add_argument('integers', metavar='N', type=int, nargs='+',
                        help=u'an integer for the accumulator')
    parser.add_argument('--sum', dest='accumulate', action='store_const',
                        const=sum, default=max,
                        help=u'sum the integers (default: find the max)')
    args = parser.parse_args()
    print args.accumulate(args.integers)


if __name__ == '__main__':
    git()
