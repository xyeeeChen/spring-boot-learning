# Maven

Maven 是一個可以簡單管理 Java 專案的工具，更白話一點的說，就是在一套規則下，可以用腳本、設定檔管理程式碼、各種版本的函式庫 (jar)。

更棒的是，當要使用其他人寫的 jar 時，可以直接從 Maven Repository 中下載。

> 除了 Maven 外，gradle 也是很多人會使用的專案管理工具

## Maven 專案的核心 pom.xml

先來看看，一個 Maven 專案的長相

```
.
+-- pom.yml
+-- src
    +-- main
    +-- test
```

其中，pom.xml 內容大致如下

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	
  <modelVersion>4.0.0</modelVersion>
	
  <!-- 專案的名稱、描述、版本等專案基本資訊 -->
  <groupId></groupId>
  <artifactId></artifactId>
  <version></version>
  <name></name>
  <description></description>
	
  <properties>
    <java.version>1.8</java.version>
  </properties>
	
	
  <!-- 管理專案中使用的套件 -->
  <dependencies>
    <!-- 使用了 "junit" 套件，版本為 "4.11" -->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.11</version>
      <scope>test</scope>
    </dependency>
  </dependencies>
	
	
  <!-- 使用mvn執行建構的plugin -->
  <build>
    <plugins>
      <!-- 例如當執行 "mvn clean" 時，則會執行這個plugin -->
      <plugin>
        <artifactId>maven-clean-plugin</artifactId>
        <version>3.1.0</version>
      </plugin>
    </plugins>
  </build>
	
	
  <!-- 設定當使用mvn執行建構時可透過 "mvn -P" 執行不同的建構程序(流程) -->
  <profiles>
    <profile>
      <!-- 例如當執行 "mvn -Pprod" 時，則會使用這個profile的建構內容 -->
      <id>prod</id>
      <!-- 可以在每個profile中加上特定的dependency -->
      <dependencies/>
      <!-- 也可以在每個profile中定義不同的建構流程 -->
      <build/>
    </profile>
  </profiles>
	
</project>
```

一個 Maven 的專案中，一定會有一個檔案叫做 pom.xml，其中描述整個專案的名稱、版本、建置流程...等。

> gradle 專案的核心是 build.gradle

## 建立第一個 Maven 專案

安裝 Maven 工具 (以 mac os 為例)

* brew install maven

檢查安裝是否成功

```sh
mvn --version
```

> 通常我們要檢查 XX 工具是否安裝成功，都會試著抓抓看這個工具的版本、說明手冊，常見的指令有 `XX version`、`XX -version`、`XX --version`、`XX help`、`XX -help`、`XX --help`，或是把 help 簡化為 h。
>
> 如果顯示`command not found: xx`代表安裝未完成，也有可能是路徑還沒加入到系統或本地的環境變數中。

用指令創建第一個 Maven 專案

```sh
mvn archetype:generate \
  -DgroupId=com.sprintboot.management \
  -DartifactId=management-system \
  -DarchetypeArtifactId=maven-archetype-quickstart \ 
  -DarchetypeVersion=1.4 \
  -DinteractiveMode=false
```

* -DgroupId: 專案所屬的公司或組織，也是程式碼的資料夾結構 (java 的 package name)
* -DartifactId: 專案名稱，也是創建後的資料夾名稱
* -DarchetypeArtifactId: 專案的類型，maven提供了幾種範本。範本類型可以從官網的 [Archetypes](https://mvnrepository.com/artifact/org.apache.maven.archetypes) 看到。
* -DarchetypeVersion: 選擇用DarchetypeArtifactId 的哪一個版本。
* -DinteractiveMode: 默認為 true，是否要用一問一答的方式建立專案。

## 新增 dependency 的方法

pom.xml 的 `<dependencies>` 標籤定義了專案使用的 dependency，只要按照其格式就可以增加新的 dependency。

例如，若要加入 「XX」套件，只要上網查詢 `XX maven`，找到 `<dependency>` 複製貼到 pom.xml 即可。

## 打包專案

產生 jar 於 target 資料夾中

```sh
mvn package
```

執行

```sh
java -cp target/management-system-1.0-SNAPSHOT.jar com.sprintboot.management.App
```

## Maven 生命週期

每個 Maven 專案執行時，大致經歷以下 phase

1. validate: 檢查專案設定是否正確
2. compile: 編譯專案程式碼
3. test: 執行單元測試
4. package: 打包專案，產生 .jar 或 .war
5. verify: 檢查測試結果
6. install: 安裝打包的結果到本地的 maven repository，供其它本地專案使用
7. deploy: 上傳打包結果到遠端的 maven repository 給全世界的人使用

可以透過指定 phase ，執行特定流程

例如以下

```sh
# 執行 validate -> compile -> test
mvn test
```

也可以只執行特定 plugin 中的 goal

```sh
# 執行 clean 這個 plugin 中的 clean goal
mvn clean:clean
```

```sh
# 執行 archetype plugin 的 generate goal
mvn archetype:generate
```