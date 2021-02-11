# DevTool Reload

在最近幾年各種加速開發的方法不斷出來，讓開發者最有感的應該就是 hot reload

也就是不用因為修改程式碼，而要一直重啟程式

`他會自動更新！`

我們繼續使用之前的專案

將 Devtools 加入 pom.xml，同時也把之前專案中的 testing 改為 dev
> 記得修改 src/main/resource 的 application-testing.yml

```xml
<profile>
    <id>dev</id>
    <properties>
        <activatedProperties>dev</activatedProperties>
    </properties>
    <activation>
        <activeByDefault>true</activeByDefault>
    </activation>
    <dependencies>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-devtools</artifactId>
            <optional>true</optional>
        </dependency>
    </dependencies>
</profile>
```

> 只需在 dev 中才引入 devtools 的 dependency。

接下來執行程式，會看到 log 中一些關於 live reload 的訊息

```
LiveReload server is running on port 35729
```

之後只要程式有任何更改，例如將 ProductController 的 apple 改成 banana

接著執行 build project ，就會看到服務立刻重新執行

透過 `http://localhost:8080/products/list1` 會看到 apple 變成了 banana。

事實上引入 devtools 後，會自動引入相關設置的預設值

```yml
spring:
  devtools:
    add-properties: true # 是否要使用預設的配置設定
    livereload:
      enabled: true # 是否要使用live reload的功能
      port: 35729 # 指定live reload的port號
    remote:
      restart:
        enabled: true # 是否於遠端的服務中啟用devtool的restart功能
      secret: # 如果啟用remote功能，則必須設定secret的key
      secret-header-name: X-AUTH-TOKEN # HTTP的header，用於傳輸共用的密鑰
      context-path: /.~~spring-boot!~ # 設定於remote時，所連接的路徑
      proxy:
        host:  # remote的ip位置
        port:  # remote的port號
    restart: 
      enabled: true # 是否於本機端的服務中啟用devtool的restart功能
      exclude: META-INF/maven/**,META-INF/resources/**,resources/**,static/**,public/**,templates/**,**/*Test.class,**/*Tests.class,git.properties,META-INF/build-info.properties # 指定某些路徑底下的文件被修改後不啟用restart功能
      additional-exclude: # 若不想改動到exclude的預設值，則可將欲排除啟用restart路徑的內容放在這裡
      trigger-file:  # 新增對於特定的file名稱修改時啟動restart
      additional-paths:  # 新增有哪些路徑底下的檔案被修改時restart
      log-condition-evaluation-delta: true # 是否在restart時印出CONDITION EVALUATION DELTA訊息
      poll-interval: 1s # 每固定秒數檢查一次是不是有文件被修改
      quiet-period: 400ms # 在發現有文件被修改時，等待固定秒數確保其他文件都沒有被修改，此值必須比poll-interval小
```

以下列出幾個要注意的點

* additional-exclude: 若有放新的值，則會蓋掉原本的預設值
* livereload: 此功能為當 view 修改並重新 build 後，會自動刷新網頁。網頁必須安裝特別的 [extension](http://livereload.com/extensions/)，並於連上頁面後，點擊 LiveReload icon 進行連線。
* remote: 可以更新遠端已部署的專案