SELECT '${systemCode}'                                             AS SYSID,
       '${dbid}'                                                   AS DBID,
       'mysql5.7'                                                  AS dbtype,
       TABLE_SCHEMA                                                AS SCHNAME,
       TABLE_NAME                                                  AS TABNAME,
       COLUMN_NAME                                                 AS COLNAME,
       ifnull(COLUMN_COMMENT, '')                                  AS COLCHNAME,
       COLUMN_TYPE                                                 AS COLTYPE,
       ifnull(COLUMN_DEFAULT, 'NULL')                              AS coldefault,
       CASE WHEN COLUMN_KEY = 'PRI' THEN 'YES' ELSE 'NO' END       AS PKFLAG,
       IS_NULLABLE                                                 AS NVLFLAG,
       IFNULL(CHARACTER_SET_NAME, '')                              AS colcharset,
       ifnull(PRIVILEGES, '')                                      AS colprivileges,
       '${metaDate}'                                               AS version
FROM information_schema.COLUMNS;
