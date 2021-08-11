insert into table_1(column1, column2, column3)
with table_2 as (select distinct f.column1, f.column2
                from table_3 f,
                     table_4 b
                where f.column1 = b.column1),
     table_5 as (select nvl(c.column1, c.column2) as column1 from table_6)
SELECT *
FROM table_7;