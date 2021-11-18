-- add 事件
with 
v0v1_t as 
(select distinct v, t, array_agg(f) over (partition by v) from t2 where v='v0' or v='v1'),
v01 as
(select a0.t t, a0.array_agg v0f, a1.array_agg v1f from v0v1_t a0 left join v0v1_t a1 on a0.t = a1.t where a0.v = 'v0' and a1.v =  'v1')
select t, unnest(array(select unnest(v1f) except select unnest(v0f))) from v01

-- delete 事件
with 
v0v1_t as 
(select distinct v, t, array_agg(f) over (partition by v) from t2 where v='v0' or v='v1'),
v01 as
(select a0.t t, a0.array_agg v0f, a1.array_agg v1f from v0v1_t a0 left join v0v1_t a1 on a0.t = a1.t where a0.v = 'v0' and a1.v =  'v1')
select t, unnest(array(select unnest(v0f) except select unnest(v1f))) from v01

-- modify 类型事件
with 
v_modified as 
(select a0.t t, a0.f f, a0.type a0type, a1.type a1type from t3 a0 left join t3 a1 on a0.t = a1.t and a0.f = a1.f 
where a0.v = 'v0' and a1.v = 'v1' and a0.type <> a1.type)
select * from v_modified


-- create table t2(v text, t text, f text);
insert into t2 values 
('v0', 't1', 'f1'),
('v0', 't1', 'f2'),
('v1', 't1', 'f1'),
('v1', 't1', 'f2'),
('v1', 't1', 'f3')

-- create table t3(v text, t text, f text, type text);
insert into t3 values 
('v0', 't1', 'f1', 'int'),
('v0', 't1', 'f2', 'int'),
('v1', 't1', 'f1', 'int'),
('v1', 't1', 'f2', 'string'),
('v1', 't1', 'f3', 'int')