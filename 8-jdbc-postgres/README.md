# JDBC and Postgres

要連接 Posrgres 首先要有該資料庫服務存在

可以直接執行以下指令，叫起 postgres

```sh
sh ./run_postgres.sh
```

> 必須要先安裝好 docker

接著連上 docker 中的 postgres

```sh
# your-container-name = 執行剛剛那個 script 最後顯示 id (可以只打前四碼)
docker exec -it your-container-name /bin/bash
```

進入到 container 後，使用 psql 連上 postgres

```sh
psql -h localhost -U admin -W
```

> 密碼為 123456

成功連上後，查看目前連線狀態

```sql
SELECT application_name FROM pg_stat_activity;
```

只會看到 `psql` 的字眼

接著在上次的專案，引入

```xml
<dependency>
    <groupId>org.postgresql</groupId>
    <artifactId>postgresql</artifactId>
</dependency>
```

並於 properties 中加入

```yml
datasource:
    url: jdbc:postgresql://localhost:5432/admin
    username: admin
    password: 123456
```

執行專案，應該會看到

```sh
* HikariPool-1 - Starting... 
* HikariPool-1 - Start completed.
* H2 console available at '/h2-console'. Database available at 'jdbc:postgresql://localhost:5432/admin'
```

代表成功連上

接著回到 psql 的 terminal，輸入

```sql
SELECT application_name FROM pg_stat_activity;
```

會看到除了 psql 以外，多了 PostgreSQL JDBC Driver，代表已成功連上，而 PostgreSQL JDBC Driver 的數量是 10 個是因為 CP 預設使用的數量為 10 。

> 可透過調整 hikari 的 proporties 更改連線數量。