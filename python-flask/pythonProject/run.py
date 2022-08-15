# -*- coding: utf-8 -*-

# f = open('file.txt', 'w')
import codecs
import os
import sys



def grep(filename):
  c = []
  r = []
  with open(filename, 'rb') as p:
    byte = p.read(1)
    while byte:
      if byte == b'\x01':
        if len(c) > 0:
          try:
            v = b''.join(c).decode('utf-8').strip()
          except:
            bad = open("bad.log", 'ab')
            for b in c:
              bad.write(b)
            bad.close()
            byte = p.read(1)
          if len(v) > 10:
            parts = v.split(',')
            if len(parts) > 4 and parts[4] == 'rcp_ngsp':
              r.append(v)
              r.append('\n')
        c.clear()
      else:
        c.append(byte)
      byte = p.read(1)
    if len(c) > 0:
      v = b''.join(c).decode('utf-8').strip()
      if len(v) > 10:
        parts = v.split(',')
        if len(parts) > 4 and parts[4] == 'rcp_ngsp':
          r.append(v)
          r.append('\n')
    return  r


if __name__ == '__main__':
    w = open("result.log", 'a+')
    for p in os.listdir(sys.argv[1]):
      if p.endswith(".log"):
        rf = sys.argv[1] + '/' + p
        print("begin to process : " + rf)
        r = grep(rf)
        w.writelines(r)
    w.close()




