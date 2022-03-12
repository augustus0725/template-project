WITH v0tables AS
  (SELECT (t.table_catalog || '.' || t.table_schema) AS schname,
          CASE
              WHEN t.table_type = 'BASE TABLE' THEN 'TABLE'
              ELSE 'VIEW'
          END AS table_type,
          t.table_name tabname,
          '' AS tabengine,
          '0' AS freesize,
          '' AS crtdate,
          '' AS mofifydate,
          '' AS rowformat,
          '' AS fileformat,
          '' AS filelocation,
          t.table_schema nspname,
          d.datcollate AS tabcharset,
          '${metaDate}' AS version
   FROM information_schema.tables t
   left join pg_catalog.pg_database d on t.table_catalog = d.datname
   where table_schema not in ('pg_catalog', 'information_schema')),
   -- <nspname, table_name, tabchname, tsize>
   v1tables AS
  (SELECT COALESCE(obj_description(C.oid), '') AS tabchname,
          N.nspname nspname,
          C.relname table_name,
          pg_total_relation_size (C.oid) AS tsize
   FROM pg_class C
   LEFT JOIN pg_namespace N ON (N.oid = C.relnamespace)
   WHERE nspname NOT IN ('pg_catalog',
                         'information_schema')
     AND C.relkind = 'r' ),
   -- <nspname, table_name, partitionsinfo>
   v2tables AS
  (SELECT distinct child_schema nspname,
          parent table_name,
                 array_to_string((array_agg(child) OVER (PARTITION BY child_schema, parent)), ',') AS partitionsinfo
   FROM
     (SELECT parent.relname AS parent,
             nmsp_child.nspname AS child_schema,
             child.relname AS child
      FROM pg_inherits
      JOIN pg_class parent ON pg_inherits.inhparent = parent.oid
      JOIN pg_class child ON pg_inherits.inhrelid = child.oid
      JOIN pg_namespace nmsp_parent ON nmsp_parent.oid = parent.relnamespace
      JOIN pg_namespace nmsp_child ON nmsp_child.oid = child.relnamespace
      WHERE nmsp_child.nspname not in ('pg_catalog',
                                       'information_schema') ) t),
	-- <nspname, table_name, indexesinfo>
	v3tables AS
  (SELECT DISTINCT schemaname nspname,
                   tablename TABLE_NAME,
                             array_to_string((array_agg(indexdef) OVER (PARTITION BY schemaname, tablename)), ',') AS indexesinfo
   FROM
     (SELECT *
      FROM pg_indexes
      WHERE schemaname not in ('pg_catalog',
                               'information_schema')) t),
   -- <nspname, table_name, rowcount>
   v4tables AS
   (
	   SELECT schemaname nspname,relname table_name,n_live_tup rowcount FROM pg_stat_user_tables
   ),
   -- join all info
   v01234tables AS
   (
	   select * from v0tables
	   left join v1tables on v0tables.nspname = v1tables.nspname and v0tables.tabname = v1tables.table_name
	   left join v2tables on v0tables.nspname = v2tables.nspname and v0tables.tabname = v2tables.table_name
	   left join v3tables on v0tables.nspname = v3tables.nspname and v0tables.tabname = v3tables.table_name
	   left join v4tables on v0tables.nspname = v4tables.nspname and v0tables.tabname = v4tables.table_name
   )
SELECT
  '${systemCode}' AS SYSID,
   '${dbid}' AS DBID,
   'postgresql' AS dbtype,
   v01234tables.schname SCHNAME,
   v01234tables.table_type AS tabtype,
   v01234tables.tabcharset AS tabcharset,
   v01234tables.tabengine AS tabengine,
   v01234tables.tabname AS tabname,
   v01234tables.tabchname AS tabchname, 
   '' AS tabspacename,
   v01234tables.rowcount AS rowcount,
   v01234tables.tsize AS tsize,
   '0' AS freesize,
   '' AS crtdate,
   '' AS modifydate, 
   COALESCE(v01234tables.partitionsinfo, '') AS partitionsinfo, 
   COALESCE(v01234tables.indexesinfo, '') AS indexesinfo,
   '' AS ROWFORMAT,
   '' AS FILEFORMAT,
   '' AS FILELOCATION,
   '${metaDate}' AS version
FROM v01234tables
