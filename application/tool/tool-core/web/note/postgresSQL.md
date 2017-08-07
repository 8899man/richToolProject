## 需求描述

碰到需求,需要往表里插入5万条数据, 打算使用存储过程,但是postgres, 但是postgres没有建存储过程的SQL, 使用函数来实现.

表数据结构完整性要求一次插入两条记录, 两条记录相互外键约束, record1 的 partner_id 字段值是 record2 的主键id的值, record2 的 partner_id 字段值是 record1 的主键id的值.

## 实现

```
create
	or replace function creatData() returns boolean as $BODY$

declare ii integer;
declare id1 integer;
declare id2 integer;

begin
	II := 1;
	id1 = nextval('seq_table');
	id2 = nextval('seq_table');

FOR i IN 1..50000 LOOP
insert
	into
		dock_appt
	values(
		id1,
		10,
		10250,
		5001,
		'2017-08-07 14:00:00',
		'2017-08-07 15:00:00',
		id2,
		true,
		864,
		16950,
		0,
		0,
		0,
		null,
		20,
		null,
		18050,
		'2017-08-07 13:55:08',
		18051,
		'2017-08-07 13:57:28',
		false,
		401,
		10,
		null,
		null,
		null,
		'DA-HZ001000003',
		'2017-08-07 13:54:08',
		'2017-08-07 13:57:28',
		10251
	);

insert
	into
		dock_appt
	values(
		id2,
		10,
		10251,
		5001,
		'2017-08-07 14:00:00',
		'2017-08-07 15:00:00',
		id1,
		true,
		864,
		16950,
		0,
		0,
		0,
		null,
		20,
		null,
		18050,
		'2017-08-07 13:55:08',
		18051,
		'2017-08-07 13:57:28',
		false,
		401,
		10,
		null,
		null,
		null,
		'DA-HZ001000003',
		'2017-08-07 13:54:08',
		'2017-08-07 13:57:28',
		10250
);
end LOOP;
return true;

end;
$BODY$ LANGUAGE plpgsql;
```

## 问题

这样子插入只能插入一次, 因为取得序列值的地方在for循环的外面, id的值不会随着循环再赋值, 主键冲突.

## 办法

想到可以再对函数进行循环, 于是再写一个函数循环执行上一个函数, 去掉上个函数中的for 循环语句FOR i IN 1..500000 LOOP 和 end LOOP;
再写一个下面函数循环执行函数1

```
create
	or replace function loopCreate() returns void LANGUAGE plpgsql as $BODY$ begin for i in 1..50000 LOOP PERFORM creatData();
end LOOP;
end;

```

select * from loopCreate() as tab;