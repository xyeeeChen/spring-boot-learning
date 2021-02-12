# JDBC and H2

本篇是以 H2 DB 作為開發範例

先簡介幾個 Java database 的名詞

* JDBC: Java Database Connectivity
    * Java 透過各種 SQL driver 與資料庫連接的技術
    * 缺點是對資料庫的操作 （創建、查詢、插入）需要寫複雜的 SQL 語句
* ORM: Object-Relational Mapping
    * 是一種 Java 物件映射到資料庫的表的概念
    * 使用 ORM 框架時，讓開發者開發不需要撰寫 SQL 語句
    * 缺點是當碰到複雜的查詢時，還是需要自己編寫 SQL 語句
    * 常見的 ORM 框架有 Hibernate、Spring Data JPA 等
* JPA: Java Persistence API
    * 指 Java 與資料庫進行持久化資料存取的 API
    * 只是一種規範
    * 可以使用 JPQL (Java Persistence Query Language) 向資料庫下達命令，不需要隨著資料庫的不同而更改
* CP: Connection Pool
    * 指一開始 pool 就有一些已初始化的物件，當需要使用時，從裡面拿出，用完後放回去，不需要反覆的創建與銷毀
    * 其中以 HikariCP 效能較好

# 專案引入 JDBC、H2

在 pom.xml 中加入 JDBC

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-jdbc</artifactId>
</dependency>
```

> 此 starter-jdbc 中包含了 spring-jdbc 及 HikariCP

引入 JDBC 介面後，需要於 properties 中加入關於資料庫的設定，如下

```yml
spring:
  datasource:
    type: 設定ConnectionPool的DataSource (或從classPath中自動檢測)
    url: 設定jdbc連線至資料庫的url
    username: 設定jdbc連線至資料庫的帳號
    password: 設定jdbc連線至資料庫的密碼
    driver-class-name: 設定JDBC的Driver (或依照url自動檢測)
```

> 依照使用不同的資料庫有不同的設定，以下用 H2 作為範例。

H2 是一個以 Java 開發的開源資料庫，其有著速度快、體積小、支援 JDBC、可以直接在瀏覽器中看到 Console 的頁面等優點，適合作為嵌入式的資料庫，也很適合作為測試時使用的資料庫。

有兩種不同的形式

* Memory-Base: 資料以存放在記憶體中，因此當應用程式關閉時，資料庫也會被清除
* Disk-Base: 資料以存放在硬碟中，因此當應用程式關閉時，資料會以 File 的方式存放在某個資料夾位置內，其資料夾位置可以被設定

引入 H2

```xml
<dependency>
  <groupId>com.h2database</groupId>
  <artifactId>h2</artifactId>
  <scope>runtime</scope>
</dependency>
```

引入 H2 後， properties 的預設值

```yml
spring:
  datasource:
    url: jdbc:h2:mem:<SpringBoot產生的字串>;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    username: sa
    password:
    driver-class-name: org.h2.Driver
```

> * 可以根據需要進行修改
> * 若將 mem 改成 file，則為使用 disk-based
> * DB_CLOSE_DELAY 為 -1 代表最後一個 statement 結束時，不關掉與 DB 的連線; 0 代表立刻關閉; > 0 代表經過幾秒後關閉

以下為本專案使用的範例

```yml
spring:
  datasource:
    url: jdbc:h2:file:./target/h2db/db/local;DB_CLOSE_DELAY=-1
    username: admin
    password: mypassword
```


啟動專案時，可以在 log 看到
> ```sh
> # HikariCP 啟動
> * HikariPool-1 - Starting... 
> * HikariPool-1 - Start completed.
> # H2 console 啟動在 /h2-console
> # 可以在 ./target/h2db 底下看到資料庫的檔案
> * H2 console available at '/h2-console'. Database available at 'jdbc:h2:file:./target/h2db/db/local'
> ```

關於 H2 其他的 properties 設定，可[從此](https://docs.spring.io/spring-boot/docs/current/reference/html/appendix-application-properties.html#data-properties)看到。
> 例如是否啟用 console、console 路徑等。

接著我們瀏覽 http://localhost:8080/h2-console/login.jsp 

並輸入 properties 設定的值，即可瀏覽資料庫內容。
> JDBC URL 、 User Name、Password 要一模一樣

