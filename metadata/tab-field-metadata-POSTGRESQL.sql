with
-- <table_catalog, table_schema, column_name, column_default>
columns_default AS
(SELECT
table_catalog,
table_schema,
table_name,
column_name,
column_default
-- column_name, column_default
FROM information_schema.columns
WHERE table_schema not in ('pg_catalog', 'information_schema')
),
--
columns_commons AS (
SELECT '${systemCode}'                                                                        as SYSID,
       '${dbid}'                                                                              as DBID,
       'postgresql'                                                                           as dbtype,
       n.nspname                                                                              as SCHNAME,
       c.relname                                                                              as TABNAME,
       a.attname                                                                              as COLNAME,
       COALESCE(dsc.description, '')                                                          as COLCHNAME,
       t.typname                                                                              as COLTYPE,
       ''                                                                                     as coldefault,
       ''                                                                                     as PKFLAG,
       case when a.attnotnull OR (t.typtype = 'd' AND t.typnotnull) then 'YES' else 'NO' end  as NVLFLAG,
       ''                                                                                     as colcharset,
       ''                                                                                     as colprivileges,
       '${metaDate}'                                                                          as version
FROM
(select nspacl, nspname, oid from pg_catalog.pg_namespace where nspname not in ('pg_catalog', 'information_schema')) n
         JOIN pg_catalog.pg_class c ON (c.relnamespace = n.oid)
         JOIN pg_catalog.pg_attribute a ON (a.attrelid = c.oid)
         JOIN pg_catalog.pg_type t ON (a.atttypid = t.oid)
         LEFT JOIN pg_catalog.pg_attrdef def ON (a.attrelid = def.adrelid AND a.attnum = def.adnum)
         LEFT JOIN pg_catalog.pg_description dsc ON (c.oid = dsc.objoid AND a.attnum = dsc.objsubid)
         LEFT JOIN pg_catalog.pg_class dc ON (dc.oid = dsc.classoid AND dc.relname = 'pg_class')
         LEFT JOIN pg_catalog.pg_namespace dn ON (dc.relnamespace = dn.oid)
WHERE c.relkind in ('r')
  and a.attnum > 0
  AND NOT a.attisdropped
  and n.nspname not in ('pg_catalog', 'information_schema')
),
columns_primary_key AS (
-- <table_catalog, table_schema, table_name, primary_key>
select
kcu.constraint_catalog AS table_catalog,
kcu.table_schema AS table_schema,
kcu.table_name AS table_name,
kcu.column_name AS primary_key
from information_schema.table_constraints tco
join information_schema.key_column_usage kcu
     on kcu.constraint_name = tco.constraint_name
     and kcu.constraint_schema = tco.constraint_schema
     and kcu.constraint_name = tco.constraint_name
where tco.constraint_type = 'PRIMARY KEY' and kcu.table_schema not in ('pg_catalog', 'information_schema')
),
-- 添加默认值
columns_commons_add_defaults AS (
SELECT SYSID,
       DBID,
       dbtype,
       SCHNAME,
       TABNAME,
       COLNAME,
       COLCHNAME,
       COLTYPE,
	   COALESCE(columns_default.column_default, 'null') AS column_default,
	   NVLFLAG,
       colcharset,
       colprivileges,
       version
	from columns_commons
	left join columns_default on columns_commons.SCHNAME = columns_default.table_schema
	and columns_commons.TABNAME = columns_default.table_name
	and columns_commons.COLNAME = columns_default.column_name
),
-- add primary key flag
columns_add_primary_key AS (
	select
	   SYSID,
	   DBID,
       dbtype,
       SCHNAME,
       TABNAME,
       COLNAME,
       COLCHNAME,
       COLTYPE,
	   column_default,
	   CASE WHEN t1.primary_key = t0.COLNAME THEN 'YES' ELSE 'NO' END AS PKFLAG,
	   NVLFLAG,
       colcharset,
       colprivileges,
       version
	from columns_commons_add_defaults t0
	left join columns_primary_key t1
	on t0.SCHNAME = t1.table_schema and t0.TABNAME = t1.table_name
)
select * from columns_add_primary_key
