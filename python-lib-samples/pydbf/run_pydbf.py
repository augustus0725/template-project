import dbfpy.dbf
from time import time

def test1():
    dbf = dbfpy.dbf.Dbf("a.dbf", readOnly = True)

    for (i, name) in enumerate(dbf.fieldNames):
        print("Field {}, name {}".format(i, name))

    for row in range(1, len(dbf)):
        for col in range(len(dbf.fieldNames)):
            print("row {}, col {} ---> [{}]".format(row, col, dbf[row][col]))


if __name__ == "__main__":
    start = time()
    test1()
    end = time()
    print end - start
