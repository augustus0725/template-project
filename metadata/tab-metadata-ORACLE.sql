with
-- basic table info
basic_tables AS (
select
'oracle' AS dbtype,
'TABLE' AS tabtype,
userenv('language') AS tabcharset,
'' AS tabengine,
table_name, 
tablespace_name, 
num_rows,
avg_row_len 
from user_tables where num_rows is not null
union all
-- 基本视图信息
select
'oracle' AS dbtype,
'VIEW' AS tabtype,
userenv('language') AS tabcharset,
'' AS tabengine,
view_name AS table_name,
'' AS tablespace_name,
0 AS num_rows,
0 AS avg_row_len
from user_views
),
-- 添加表描述
tables_v0 AS (
  select t0.*, t1.comments from
  basic_tables t0
  left join user_tab_comments t1
  on t0.table_name = t1.table_name and t0.tabtype = t1.table_type
),
-- 添加表创建时间
tables_v1 AS (
  select t0.*, t1.crtdate from
  tables_v0 t0
  left join (
    SELECT object_name, TO_CHAR(created, 'yyyyMMddHH24miss') AS crtdate
  FROM user_objects where object_type in ('TABLE', 'VIEW')
  ) t1 on t0.table_name = t1.object_name
),
-- 添加表修改时间
tables_v2 AS (
  select t0.*, TO_CHAR(t1.TIMESTAMP, 'yyyyMMddHH24miss') AS modifydate, t1.table_owner AS schname from
  tables_v1 t0
  left join all_tab_modifications t1
  on t0.table_name = t1.table_name where t1.table_owner is not null
),
-- 添加分区信息
tables_v3 AS (
  select t0.*, t1.partitionsinfo from tables_v2 t0
  left join (
    select table_owner, table_name, wm_concat(partition_name) as partitionsinfo
    from all_tab_partitions group by table_owner, table_name
  ) t1 on t0.table_name = t1.table_name
),
-- 添加索引信息
tables_v4 AS (
  select t0.*, t1.indexesinfo from tables_v3 t0
  left join (
    select
    index_owner,
    table_name,
    wm_concat(indexesinfo) AS indexesinfo from (
        select
        index_owner,
        table_name,
        concat(concat(index_name, '->'), wm_concat(column_name)) AS indexesinfo
        from
           all_ind_columns
        group by
        index_owner,table_name,index_name
    ) group by index_owner,table_name
  ) t1 on t0.table_name = t1.table_name and t0.schname = t1.index_owner
)
select
  '${systemCode}' AS SYSID,
   '${dbid}' AS DBID,
   'oracle' AS dbtype,
   t.SCHNAME,
   t.tabtype,
   t.tabcharset,
   t.tabengine,
   t.table_name AS tabname,
   t.comments AS tabchname,
   t.tablespace_name AS tabspacename,
   to_char(t.num_rows) AS rowcount,
   to_char(t.num_rows * t.avg_row_len) AS tsize,
   '0' AS freesize,
   t.crtdate,
   t.modifydate,
   t.partitionsinfo,
   t.indexesinfo,
   '' AS ROWFORMAT,
   '' AS FILEFORMAT,
   '' AS FILELOCATION,
   '${metaDate}' AS version
from tables_v4 t
