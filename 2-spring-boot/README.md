# First Sprint Boot

首先創建專案

```sh
mvn archetype:generate -DgroupId=com.springboot.management -DartifactId=management-system  -DarchetypeArtifactId=maven-archetype-quickstart -DarchetypeVersion=1.4 -DinteractiveMode=false
```

打開 pom.xml，加入

```xml
<parent>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-parent</artifactId>
  <version>2.4.2</version>
  <relativePath/>
</parent>
```

再加入 boot 的 dependency

```xml
<dependencies>
  <!-- others -->
  <dependency></dependency>

  <dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter</artifactId>
  </dependency>
</dependencies>
```

以 Intellij 加入專案，`Import Project` -> 選擇 `pom.xml` (稍待片刻)

重新命名 src/main/com.xxx.xxx/App.java 為 ManagementSystemApplication.java

並將內容換成

```java
@SpringBootApplication
public class ManagementSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManagementSystemApplication.class, args);
	}

}
```

> * 主要類別的名稱通常會以 xxxApplication
> * 一定要對主要類別加上標註 @SpringBootApplication
> * 程式進入點 main() 的方法內使用 SpringApplication.run() 啟動 Spring Boot 專案
> * @SpringBootApplication 標註同等於以下三個標註的特性(參考)：
>   * @EnableAutoConfiguration，使用 Spring Boot 的自動配置機制
>   * @ComponentScan，自動掃描 @Component 元件
>   * @Configuration，允許導入其他 Bean 的配置

接著就可以執行了。

## 另外一種創建專案的方式

到 https://start.spring.io/ 下載專案

> 提供了一個快速幫我們把要使用的 Maven 或者 Gradle 的專案建立起來,並且將版本,依賴包成一個專案

* 選擇 Maven
* Group 為 GroupId
* Artifact 為 ArtifactId
* Packaging 選擇 Jar
* Java 版本用 default (11)

產生專案下載後並解壓縮。
> 查看專案中的 pom.xml 會發現跟上一步驟產生的 pom.xml 幾乎一樣。

### Maven Wrapper

在根目錄下可以看到 mvnw 的執行檔

* mvnw：適用於Linux系統使用的script
* mvnw.cmd：適用於Windows系統使用的script 
* .mvn/wrapper/maven-wrapper.properties：設定Mavan Wrapper下載路徑的設定檔
* .mvn/wrapper/maven-wrapper.jar：作為 mvnw 或 mvnw.cmd 的 script 中，引導使用maven 所需的 jar 檔
* .mvn/wrapper/MavenWrapperDownloader.java：如果 maven-wrapper.jar 無法被正常調用，則會先從 maven-wrapper.properties 的 wrapperUrl 所指定的 URL 中下載 maven-wrapper.jar。如果無法下載，那麼就會編譯此 java 檔，作為可執行的 maven-wrapper.jar。

mvnw 為 maven wrapper 提供不需要安裝 mvn 也能在支援 java 的環境下執行 maven 指令
> 前提是專案內要有 mvnw

安裝 mvnw 到專案中的方式

```sh
mvn -N io.takari:maven:wrapper -Dmaven=3.6.0
```

其中 -Dmaven=3.6.0 為限定專案使用的 maven 版本為 3.6.0

> 之後其他使用這個專案的人，就可以不用安裝 maven，而是能改成使用 mvnw

可以用 mvnw 取代 mvn

原本執行 

```sh
mvn clean
```

可換成

```sh
./mvnw clean
```

也可以這樣編譯

```sh
./mvnw package
```

