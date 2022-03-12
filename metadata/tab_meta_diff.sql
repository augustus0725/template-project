insert into
dxp_events (type, event_level, who_id, who, issue_id, issue_name, title, content, p1, p2, p3, p4, p5, p6)
-- The following is the data.
with
-- v1v0: 获取版本号 v1, v0
-- v1 = max(v1v0)
-- v0 = min(v1v0)
v1v0 as
((select distinct version from meta_tlb order by version desc limit 2)
union all
(select p6 as version from dxp_events where p6 <> '' order by p6 desc limit 1)),
dbid_description as
(select  t0.id::varchar(128), (t1.system_name || '->' || t0.ip_address ||  ',' || t0.db_type ||  ',' || t0.user_name || '->' || t0.db_name) as description  from tb_db_source t0 left join tb_system_code t1 on t0.system_code = t1.system_code),
-- v0v1两个版本同一张表，同一个属性放到一行记录里，方便比较
t_v0v1 as
(select
 a0.sysid as sysid, a0.dbid dbid, a0.dbtype dbtype, a0.schname schname, a0.tabtype tabtype, a0.tabname tabname, a0.version as version,
 a0.tabcharset a0tabcharset, a1.tabcharset a1tabcharset,
 a0.tabengine a0tabengine, a1.tabengine a1tabengine,
 a0.tabchname a0tabchname, a1.tabchname a1tabchname,
 a0.tabspacename a0tabspacename, a1.tabspacename a1tabspacename,
 a0.rowcount a0rowcount, a1.rowcount a1rowcount,
 a0.tsize a0tsize, a1.tsize a1tsize,
 a0.freesize a0freesize, a1.freesize a1freesize,
 a0.crtdate a0crtdate, a1.crtdate a1crtdate,
 a0.modifydate a0modifydate, a1.modifydate a1modifydate,
 a0.partitionsinfo a0partitionsinfo, a1.partitionsinfo a1partitionsinfo,
 a0.indexesinfo a0indexesinfo, a1.indexesinfo a1indexesinfo,
 a0.rowformat a0rowformat, a1.rowformat a1rowformat,
 a0.fileformat a0fileformat, a1.fileformat a1fileformat,
 a0.filelocation a0filelocation, a1.filelocation a1filelocation
 from meta_tlb a0
 left join meta_tlb a1
 on a0.dbid = a1.dbid and a0.schname = a1.schname and a0.tabname = a1.tabname
 where a0.version = (select min(version) from v1v0) and a1.version = (select max(version) from v1v0)
),
-- 库下面的表
v0v1_db_tlb as
(select distinct version, (sysid || '=>' || dbid) as db, tabname, sysid, dbid, schname from meta_tlb where version in (select version from v1v0)),
v0v1_db_tlb_p as
(select distinct version, db, array_agg(tabname) over (partition by version,db), sysid, dbid, schname from v0v1_db_tlb where version in (select version from v1v0)),
v01_db_tlb_p as
(select a0.sysid, a0.dbid, a0.schname, a0.version, a0.db db, a0.array_agg v0tab, a1.array_agg v1tab from v0v1_db_tlb_p a0 left join v0v1_db_tlb_p a1 on a0.db = a1.db where a0.version = (select min(version) from v1v0) and a1.version =  (select max(version) from v1v0)),
-- 根据表属性将比对的结果查询出来，采用union all
-- type, event_level, who_id, who, issue_id, issue_name, title, content, p1, p2, p3, p4, p5, p6
v_result as
(select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tabcharset' as issue_name, '' as title, ('tabcharset: ' || a0tabcharset || '=>' || a1tabcharset) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0tabcharset <> a1tabcharset
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tabengine' as issue_name, '' as title, ('tabengine: ' || a0tabengine || '=>' || a1tabengine) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0tabengine <> a1tabengine
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tabchname' as issue_name, '' as title, ('tabchname: ' || a0tabchname || '=>' || a1tabchname) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0tabchname <> a1tabchname
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tabspacename' as issue_name, '' as title,  ('tabspacename: ' || a0tabspacename || '=>' || a1tabspacename) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0tabspacename <> a1tabspacename
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'rowcount' as issue_name, '' as title, ('rowcount: ' || a0rowcount || '=>' || a1rowcount) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0rowcount <> a1rowcount
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tsize' as issue_name, '' as title, ('tsize: ' || a0tsize || '=>' || a1tsize) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0tsize <> a1tsize
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'freesize' as issue_name, '' as title, ('freesize: ' || a0freesize || '=>' || a1freesize) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0freesize <> a1freesize
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'crtdate' as issue_name, '' as title, ('crtdate: ' || a0crtdate || '=>' || a1crtdate)  as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0crtdate <> a1crtdate
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'modifydate' as issue_name, '' as title, ('modifydate: ' || a0modifydate || '=>' || a1modifydate) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0modifydate <> a1modifydate
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'partitionsinfo' as issue_name, '' as title, ('partitionsinfo: ' || a0partitionsinfo || '=>' || a1partitionsinfo) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0partitionsinfo <> a1partitionsinfo
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'indexesinfo' as issue_name, '' as title, ('indexesinfo: ' || a0indexesinfo || '=>' || a1indexesinfo) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0indexesinfo <> a1indexesinfo
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'rowformat' as issue_name, '' as title, ('rowformat: ' || a0rowformat || '=>' || a1rowformat) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0rowformat <> a1rowformat
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'fileformat' as issue_name, '' as title, ('fileformat: ' || a0fileformat || '=>' || a1fileformat) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0fileformat <> a1fileformat
union all
select 4 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'filelocation' as issue_name,'' as title, ('filelocation: ' || a0filelocation || '=>' || a1filelocation) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from t_v0v1 where a0filelocation <> a1filelocation
-- 增加的表
union all
select 5 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tab_add' as issue_name, '' as title, unnest(array(select unnest(v1tab) except select unnest(v0tab))) as content, sysid as p1, dbid as p2, schname as p3, '' as p4, '' as p5, version as p6 from v01_db_tlb_p
-- 删除的表
union all
select 5 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tab_delete' as issue_name, '' as title, unnest(array(select unnest(v0tab) except select unnest(v1tab))) as content, sysid as p1, dbid as p2, schname as p3, '' as p4, '' as p5, version as p6 from v01_db_tlb_p
)
select a0.type, a0.event_level,a0.who_id, a0.who, a0.issue_id, a0.issue_name, a1.description as title, a0.content, a0.p1, a0.p2, a0.p3, a0.p4, a0.p5, a0.p6 from v_result a0 left join dbid_description a1 on a0.p2 = a1.id
