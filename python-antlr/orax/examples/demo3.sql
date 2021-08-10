with table_2 as
         (select ar.column1,
                 ar.column2,
                 case
                     when ar.column3 is null or dbms_lob.getlength(ar.column3) = 0 then
                         null
                     else
                         ar.column3
                     end as str
          from table_1 ar
          where ar.column4 = ''
            and dbms_lob.getlength(ar.column3) > 0
            and ar.column3 is not null
         ),
     table_3 as (
         select ac.column1,
                ac.column2,
                dbms_lob.substr(regexp_substr(ac.column3, '[^&]+]', b.column2)) val
         from table_4 ac,
              (
                  select level lv
                  from (select max(REGEXP_COUNT(a.str, '[^&]+]', 1)) r_count
                        from table_5 a) b connect by level <= b.r_count
              ) b
         where dbms_lob.substr(REGEXP_SUBSTR(ac.str, '[^&]+]', 1, b.lv)) is not null
         order by ac.column3
     )