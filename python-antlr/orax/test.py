import unittest

from orax.OraxImp import find_tables_and_fields, print_tables_and_fields


class MyTestCase(unittest.TestCase):
    def test_insert(self):
        r = find_tables_and_fields("insert into my_table m (m.a, m.b) select * from my_table")
        print_tables_and_fields(r)
        self.assertEqual([4, 25], r[0])
        self.assertEqual([11, 16], r[1])

    def test_select(self):
        r = find_tables_and_fields(
            "select INITCAP('hi  there'),TO_CHAR(ts_col, 'DD-MON-YYYY HH24:MI:SSxFF'),CONCAT('A','BC'),"
            "'abc',max(t.a),t.a,t.b from ods.my_table t left join your_table p on t.id = p.id where a100 > 2")
        print_tables_and_fields(r)
        self.assertEqual([43, 51], r[0])
        self.assertEqual([9, 28, 33, 37, 59, 65, 69], r[1])

    def test_demo3(self):
        with open("examples/demo3.sql") as f:
            sql = f.read()
        r = find_tables_and_fields(sql)
        print_tables_and_fields(r)
        self.assertEqual([2, 64, 108, 150, 188], r[0])
        self.assertEqual(
            [11, 16, 25, 33, 39, 54, 60, 72, 80, 86, 97, 118, 123, 126, 134, 142, 146, 159, 161, 174, 184, 199, 206,
             214, 222, 233, 249], r[1])

    def test_demo4(self):
        with open("examples/demo4.sql") as f:
            sql = f.read()
        r = find_tables_and_fields(sql)
        print_tables_and_fields(r)
        self.assertEqual([4, 17, 37, 42, 60, 85, 94], r[0])
        self.assertEqual([6, 9, 12, 28, 33, 50, 56, 69, 71, 74, 76, 81], r[1])

    def test_demo4(self):
        with open("examples/demo4.sql") as f:
            sql = f.read()
        r = find_tables_and_fields(sql)
        print_tables_and_fields(r)
        self.assertEqual([4, 17, 37, 42, 60, 85, 94], r[0])
        self.assertEqual([6, 9, 12, 28, 33, 50, 56, 69, 71, 74, 76, 81], r[1])

    def test_demo5(self):
        sql = "UPDATE table_1 SET column1= 'Fred' WHERE column2 = 'Wilson'"
        r = find_tables_and_fields(sql)
        print_tables_and_fields(r)
        self.assertEqual([2], r[0])
        self.assertEqual([6, 13], r[1])


if __name__ == '__main__':
    unittest.main()
