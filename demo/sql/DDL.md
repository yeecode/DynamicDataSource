In order to show the effect, we set up two databases.

Then create the same table in each of the two databases.

The resulting structure is as follows:

- DataBase: datasource01
    - Table: sampletable
- DataBase: datasource02
    - Table: sampletable

The DDL to create "sampletable"

```sql
create table if not exists sampletable
(
	name varchar(500) null
);
```

And then, we insert different strings to two sampletable. E.g:

- info from datasource01
- info from datasource02

In this way, when switching the database type dynamically, we can know which database is queried.

---

为了能够体现DynamicDataSource的效果，我们建立两个数据库。

然后在这两个数据库中建立相同的数据表。

例如可以组成下面的结构：

- 数据库: datasource01
    - 数据表: sampletable
- 数据库: datasource02
    - 数据表: sampletable
    
其中，创建sampletable的DDL语句为：

```sql
create table if not exists sampletable
(
	name varchar(500) null
);
```
这样，我们再在两个sampletable中插入不同的语句，例如可以插入：

- info from datasource01
- info from datasource02

这样一来，我们在动态切换数据源时就能够通过结果判断出系统查询了哪一个数据库中的表。