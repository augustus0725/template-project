select '${systemCode}'                                             AS SYSID,
       '${dbid}'                                                   AS DBID,
	   'sqlserver'                                                 AS dbtype,
       db_name() + '.' + object_schema_name(c.object_id)           as SCHNAME,
       o.name                                                      as TABNAME,
       c.name                                                      AS COLNAME,
       e.value                                                     as COLCHNAME,
       type_name(c.system_type_id)                                 as COLTYPE,
       object_definition(c.default_object_id)                      as coldefault,
       ''                                                          as PKFLAG,
       IIF(is_nullable = 'true', 'YES', 'NO')                      as nvlflag,
       ''                                                          as colcharset,
	   ''                                                          as colprivileges,
       '${metaDate}'                                               as version
from sys.columns c
         join sys.objects o on c.object_id = o.object_id
         LEFT JOIN sys.extended_properties e ON e.major_id = c.object_id AND e.minor_id = c.column_id
WHERE o.type_desc in ('USER_TABLE', 'VIEW')
  AND o.object_id > 0;
