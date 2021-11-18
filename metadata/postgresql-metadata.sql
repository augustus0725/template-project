SELECT 'SYSTEM_CODE' AS SYSID,
       'DB_CODE' AS DBID,
       n.nspname AS SCHNAME,
       '' AS MODNAME,
       c.relname AS TABNAME,
       a.attname AS COLNAME,
       dsc.description AS COLCHNAME,
       t.typname AS COLTYPE,
       a.attnum AS COLSEQ,
       '' AS PKFLAG,
       '' AS PDKFLAG,
       CASE
           WHEN a.attnotnull
                OR (t.typtype = 'd'
                    AND t.typnotnull) THEN 'Y'
           ELSE 'N'
       END AS NVLFLAG,
       '' AS CCOLFLAG,
       '' AS INDXFLAG,
       '' AS CODETAB,
       '' AS SENTYPE,
       '' AS REMARK,
       to_char(now(), 'yyyyMMddHHmmSS') AS F_STARTDATE,
       '29991231235959' AS end_time,
       '' AS FLAG
FROM
  (SELECT nspacl,
          nspname,
          oid
   FROM pg_catalog.pg_namespace
   WHERE nspname in ('public')) n
JOIN pg_catalog.pg_class c ON (c.relnamespace = n.oid)
JOIN pg_catalog.pg_attribute a ON (a.attrelid = c.oid)
JOIN pg_catalog.pg_type t ON (a.atttypid = t.oid)
LEFT JOIN pg_catalog.pg_attrdef def ON (a.attrelid = def.adrelid
                                        AND a.attnum = def.adnum)
LEFT JOIN pg_catalog.pg_description dsc ON (c.oid = dsc.objoid
                                            AND a.attnum = dsc.objsubid)
LEFT JOIN pg_catalog.pg_class dc ON (dc.oid = dsc.classoid
                                     AND dc.relname = 'pg_class')
LEFT JOIN pg_catalog.pg_namespace dn ON (dc.relnamespace = dn.oid
                                         AND dn.nspname = 'pg_catalog')
WHERE c.relkind in ('r')
  AND a.attnum > 0
  AND NOT a.attisdropped
  AND n.nspname != 'information_schema'
ORDER BY c.relname,
         a.attnum