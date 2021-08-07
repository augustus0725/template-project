import unittest

from main import find_tables_and_fields, print_tables_and_fields


class MyTestCase(unittest.TestCase):
    def test_insert(self):
        r = find_tables_and_fields("insert into my_table select * from my_table")
        print_tables_and_fields(r)
        self.assertEqual([4, 12], r[0])
        self.assertEqual([], r[1])

    def test_select(self):
        r = find_tables_and_fields(
            "select INITCAP('hi  there'),TO_CHAR(ts_col, 'DD-MON-YYYY HH24:MI:SSxFF'),CONCAT('A','BC'),"
            "'abc',max(t.a),t.a,t.b from ods.my_table t left join your_table p on t.id = p.id where a100 > 2")
        print_tables_and_fields(r)
        self.assertEqual([43, 51], r[0])
        self.assertEqual([9, 28, 33, 37, 57, 59, 65, 69], r[1])


if __name__ == '__main__':
    unittest.main()