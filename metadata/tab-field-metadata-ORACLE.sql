with columnsv0
AS (
select t2.*, t3.owner,
CASE WHEN t3.column_name = t2.column_name THEN 'YES' ELSE 'NO' END AS pkflag from (
SELECT t0.table_name,
       t0.column_name,
       t0.data_type,
       t0.nullable,
       t0.data_default,
       userenv('language') AS colcharset,
       t1.comments
FROM user_tab_cols t0
LEFT JOIN user_col_comments t1 ON t0.table_name = t1.table_name
AND t0.column_name = t1.column_name where t1.comments is not null
) t2 left join (
SELECT distinct cols.table_name, cols.column_name, cons.owner
FROM user_constraints cons, user_cons_columns cols
where cons.constraint_type = 'P'
) t3 on t2.table_name = t3.table_name and t2.column_name = t3.column_name
)
SELECT '${systemCode}'                                             AS SYSID,
       '${dbid}'                                                   AS DBID,
       'oracle'                                                    AS dbtype,
       columnsv0.owner                                             AS SCHNAME,
       columnsv0.TABLE_NAME                                        AS TABNAME,
       columnsv0.COLUMN_NAME                                       AS COLNAME,
       columnsv0.comments                                          AS COLCHNAME,
       columnsv0.data_type                                         AS COLTYPE,
       columnsv0.data_default                                      AS coldefault,
       columnsv0.PKFLAG                                            AS PKFLAG,
       CASE WHEN columnsv0.nullable = 'Y' THEN 'YES' ELSE 'NO' END AS NVLFLAG,
       columnsv0.colcharset                                        AS colcharset,
       ''                                                          AS colprivileges,
       '${metaDate}'                                               AS version
from columnsv0
