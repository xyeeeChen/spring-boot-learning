# Spring Boot Properties

我們可以透過修改 src/main/resources 中的 application*.* 來設定服務，例如更改 port 監聽的位置

> 一開始我們引入的 spring-boot-starter-parent 中規定
> Spring Boot 所支援的 Profile 的檔名可以是 
> * application*.yml
> * application*.yaml
> * application*.properties
>
> 此外，Profile 的許可路徑預設為「src/main/resources」底下的任意位置。
> 
>
> [YAML vs properties](https://www.baeldung.com/spring-yaml-vs-properties)

我認為 YAML 比較好讀，因此把 application.properties 修改為 application.yml

接著加入

```yml
server:
  port: 8888
```

然後啟動，會發現改聽到 8888 port 了。

## 根據不同環境配置不同的 profile

分別建立兩個檔案

application-testing.yml

```yml
server:
  port: 8080
```

application-production.yml

```yml
server:
  port: 8090
```

接著設定何時使用哪個檔案

### 在 application.yml 中指定 Profile

application.yml Spring Boot 預設會吃的設定檔

所以可以在 application.yml 中加入如下

```yml
spring:
  profiles:
    active: production
```

> 因此就會以 application-production.yml 作為設定檔。
> 
> 也可以加入多組設定檔，若有相同參數，後讀的會蓋掉之前的。

這樣做的缺點是每次都必須針對不同環境修改這個檔案

### 執行maven時透過參數指定 Profile

在 pom.xml 中加入如下內容

```xml

<project>
  ...
  <properties>...</properties>
  <dependencies>...</dependencies>
  <build>...</build>
  
  <!-- 加入testing與prod環境的profile -->
  <profiles>
    <profile>
      <id>test</id>
      <properties>
        <activatedProperties>testing</activatedProperties>
      </properties>
      <activation>
        <activeByDefault>true</activeByDefault>
      </activation>
    </profile>
    <profile>
      <id>prod</id>
      <properties>
        <activatedProperties>production</activatedProperties>
      </properties>
    </profile>
  </profiles>

</project>
```

> `<activeByDefault>true</activeByDefault>` 代表若沒有使用 -P 則預設使用此 profile

接著就可以透過 `mvn -P <id>` 指定要用哪個 profile

```sh
mvn -P prod spring-boot:run
```

當執行上述指令時，就會以 activatedProperties = production 啟動程式

因此我們必須要在 application.yml 中，加入

```yml
spring:
  profiles:
    active: "@activatedProperties@"
```

如此一來，就會以 production 替換掉 yml 中的 activatedProperties 進而使用 application-production.yml。