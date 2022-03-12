SELECT SYSID,
       DBID,
	   dbtype,
       SCHNAME,
	   -- 表类型
	   tabtype,
	   -- 表字符集
	   tabcharset,
       tabengine,
	   TABNAME,
       coalesce(TABCHNAME, '') AS TABCHNAME,
       TABSPACENAME,
	   cast([row_count] as varchar(128)) AS [rowcount],
	   cast(t_size as varchar(128)) TSIZE,
	   freesize,
	   CRTDATE,
	   MODIYDATE,
	   -- 分区信息
	   coalesce(partitionsinfo, '') AS partitionsinfo,
	   -- 索引信息
	   coalesce(indexinfo, '') AS indexesinfo,
	   rowformat,
	   fileformat,
	   filelocation,
	   '${metaDate}'                                                                                       as version
from
(
select t7.*, t8.[t_size] from
(
select t5.*, t6.partitionsinfo from (
select t3.*, t4.indexinfo from
(
select t0.*, t1.row_count from
(
SELECT '${systemCode}'                                                                                     AS SYSID,
       '${dbid}'                                                                                           AS DBID,
	   'sqlserver'                                                                                         as dbtype,
       db_name() + '.' +  object_schema_name(t.object_id)                                                  as SCHNAME,
	   -- 表类型
	   case when t.type_desc = 'USER_TABLE' then 'TABLE' else 'VIEW' end                                   as tabtype,
	   -- 表字符集
	   (select collation_name from sys.databases where name = db_name())                                   as tabcharset,
       ''                                                                                                  AS tabengine,
	   t.name                                                                                              AS TABNAME,
       convert(varchar (1000), ep.value)                                                                   AS TABCHNAME,
       ''                                                                                                  AS TABSPACENAME,
	   ''                                                                                                  as TSIZE,
	   '0'                                                                                                 as freesize,
	   format(create_date,'yyyyMMddhhmmss')                                                                as CRTDATE,
	   format(modify_date,'yyyyMMddhhmmss')                                                                as MODIYDATE,
	   -- 分区信息
	   -- 索引信息
	   ''                                                                                                  AS rowformat,
	   ''                                                                                                  AS fileformat,
	   ''                                                                                                  AS filelocation,
	   '${metaDate}'                                                                                       as version,
	   t.object_id                                                                                         as object_id
FROM sys.objects t
         LEFT JOIN sys.extended_properties AS ep ON ep.major_id = t.object_id AND ep.class = 1 AND ep.minor_id = 0
         LEFT JOIN (select name as pk_name, parent_object_id
                    from sys.objects
                    where type_desc = 'PRIMARY_KEY_CONSTRAINT') TMP ON TMP.parent_object_id = t.object_id
WHERE t.type_desc in ('USER_TABLE', 'VIEW')
  AND t.object_id > 0
) t0
-- add row count
left join (
SELECT
obj.object_id,
SUM(dmv.row_count) AS row_count
FROM sys.objects AS obj
  INNER JOIN sys.dm_db_partition_stats AS dmv
  ON obj.object_id = dmv.object_id
WHERE obj.type = 'U'
  AND obj.is_ms_shipped = 0x0
  AND dmv.index_id in (0, 1)
GROUP BY obj.object_id
) t1
on t0.object_id = t1.object_id
) t3
-- add index info
left join (
select  object_id, string_agg(([type_desc] collate SQL_Latin1_General_CP1_CI_AS + ',' + [name] collate SQL_Latin1_General_CP1_CI_AS), ',') as indexinfo
from sys.indexes group by object_id
having string_agg(([type_desc]  + ',' + [name] collate SQL_Latin1_General_CP1_CI_AS), ',') is not null
) t4
on t3.object_id = t4.object_id
) t5
-- add partitions info
left join (
select string_agg(cast(partition_id as varchar(128)) + ',' + cast(rows as varchar(128)), ',') as partitionsinfo, object_id from sys.partitions group by object_id
) t6
on
t5.object_id = t6.object_id
) t7
left join (
select t1.object_id,(sum(t2.used_pages) * 8 * 1024) AS [t_size] from sys.partitions t0
left join
sys.tables t1
on t0.object_id = t1.object_id
left join sys.allocation_units t2
on t0.partition_id = t2.container_id
where t1.object_id is not null
group by t1.object_id
) t8
on t7.object_id = t8.object_id
) t_all
