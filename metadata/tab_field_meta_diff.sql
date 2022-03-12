insert into
dxp_events (type, event_level, who_id, who, issue_id, issue_name, title, content, p1, p2, p3, p4, p5, p6)
with
-- 最近的两个版本
v1v0 as
((select distinct version from meta_tlb_field order by version desc limit 2)
union all
(select p6 as version from dxp_events where p6 <> '' order by p6 desc limit 1)),
-- 库表述
dbid_description as
(select t0.id::varchar(128), (t1.system_name || '->' || t0.ip_address ||  ',' || t0.db_type ||  ',' || t0.user_name || '->' || t0.db_name) as description  from tb_db_source t0 left join tb_system_code t1 on t0.system_code = t1.system_code),
-- 同一个字段的两个版本放到一起比较
t_f_v01 as
(select a0.sysid as sysid, a0.dbid dbid, a0.schname schname, a0.tabname tabname, a0.colname colname, a0.version as version,
 a0.colchname a0colchname,a1.colchname a1colchname,
 a0.coltype a0coltype, a1.coltype a1coltype,
 a0.coldefault a0coldefault, a1.coldefault a1coldefault,
 a0.pkflag a0pkflag, a1.pkflag a1pkflag,
 a0.nvlflag a0nvlflag,a1.nvlflag a1nvlflag,
 a0.colcharset a0colcharset,a1.colcharset a1colcharset,
 a0.colprivileges a0colprivileges,a1.colprivileges a1colprivileges
 from meta_tlb_field a0
 left join meta_tlb_field a1
 on a0.dbid = a1.dbid and a0.schname = a1.schname and a0.tabname = a1.tabname and a0.colname = a1.colname
 where a0.version = (select min(version) from v1v0) and a1.version = (select max(version) from v1v0)
),
-- 表下面的字段
v0v1_db_tlb_field as
(select distinct version, (sysid || '=>' || dbid || '=>' || tabname) as db_tlb, colname, sysid, dbid, schname, tabname from meta_tlb_field where version in (select version from v1v0)),
v0v1_db_tlb_field_p as
(select distinct version, db_tlb, array_agg(colname) over (partition by version,db_tlb), sysid, dbid, schname, tabname from v0v1_db_tlb_field where version in (select version from v1v0)),
v01_db_tlb_field_p as
(select a0.db_tlb db_tlb, a0.array_agg v0tab_field, a1.array_agg v1tab_field, a0.sysid, a0.dbid, a0.schname, a0.tabname, a0.version from v0v1_db_tlb_field_p a0 left join v0v1_db_tlb_field_p a1 on a0.db_tlb = a1.db_tlb where a0.version = (select min(version) from v1v0) and a1.version =  (select max(version) from v1v0)),
-- type, event_level, who_id, who, issue_id, issue_name, title, content, p1, p2, p3, p4, p5, p6
v_result as
(
-- 增加字段
select 7 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tab_field_add' as issue_name, '' as title, unnest(array(select unnest(v1tab_field) except select unnest(v0tab_field))) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from v01_db_tlb_field_p
-- 删除字段
union all
select 7 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'tab_field_delete' as issue_name, '' as title, unnest(array(select unnest(v0tab_field) except select unnest(v1tab_field))) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, '' as p5, version as p6 from v01_db_tlb_field_p
-- 字段属性变化
union all
select 6 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'colchname' as issue_name, '' as title, (a0colchname || '=>' || a1colchname) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, colname as p5, version as p6 from t_f_v01 where a0colchname <> a1colchname
union all
select 6 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'coltype' as issue_name, '' as title, (a0coltype || '=>' || a1coltype) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, colname as p5, version as p6 from t_f_v01 where a0coltype <> a1coltype
union all
select 6 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'pkflag' as issue_name, '' as title, (a0pkflag || '=>' || a1pkflag) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, colname as p5, version as p6 from t_f_v01 where a0pkflag <> a1pkflag
union all
select 6 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'nvlflag' as issue_name, '' as title, (a0nvlflag || '=>' || a1nvlflag) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, colname as p5, version as p6 from t_f_v01 where a0nvlflag <> a1nvlflag
union all
select 6 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'colcharset' as issue_name, '' as title, (a0colcharset || '=>' || a1colcharset) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, colname as p5, version as p6 from t_f_v01 where a0colcharset <> a1colcharset
union all
select 6 as type, 1 as event_level, 1 as who_id, 'admin' as who, -1 as issue_id, 'colprivileges' as issue_name, '' as title, (a0colprivileges || '=>' || a1colprivileges) as content, sysid as p1, dbid as p2, schname as p3, tabname as p4, colname as p5, version as p6 from t_f_v01 where a0colprivileges <> a1colprivileges
)
select a0.type, a0.event_level,a0.who_id, a0.who, a0.issue_id, a0.issue_name, a1.description as title, a0.content, a0.p1, a0.p2, a0.p3, a0.p4, a0.p5, a0.p6 from v_result a0 left join dbid_description a1 on a0.p2 = a1.id
