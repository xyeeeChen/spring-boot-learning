# Java Web 的簡史

* Java Applet (1990)
    * 在瀏覽器安裝 client plugin，才可與 server 溝通

* Common Gateway Interface (CGI, 1993)
    * 在作業系統上編譯 CGI 程式 (shell code)，以處理每個 request
    * 每個Request都會用一個Process來處理，會消耗很多資源

    ![CGI](https://miro.medium.com/max/1222/1*L6ThJHFx5O2_QKc9CJH_vQ.jpeg)

* Java Servlet 1.0 (1996)
    * Java Servlet，也就是「在 Server 端的 Applet」，他實現了將動態的Web內容於 Server 端生成，再回傳給 Client 端。
    * 所有的 code 都在 .java 裡
    * 產生HTML語法的部分與商業邏輯混為一談

    ![Servlet](https://miro.medium.com/max/1400/1*eAh2zfwFOZH9elU-W0_jpw.jpeg)
    
*  Java Server Pages 1.0 (JSP, 1999)
    * 可以在 .jsp 的頁面中編寫 HTML 的內容，並透過 <%=...%> 的標籤來使用 Java 相關的內容
    * 實際上還是會將 JSP 編譯成 Servlet 檔(.java)
    * 一樣還是將HTML語法的部分與商業邏輯混為一談

以下是 jsp 的範例

```xml
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head></head>
  <body>
    <div> This is home page. <div>
    <div> Login: <%= request.getSession()%> <div>
  </body>
</html>
```

* JSP 2.0 (2003)
    * MVC 概念普及
        * Model: 專注業務邏輯的處理
        * View: 專注於頁面的呈現
        * Controller: 專注於 Request 的接收與處理
    * JSP 用 `${...}` 取代 `<%=…%> `
    * 可在 web.xml 中設定 Servlet 與 JSP 的映射關係

    ![MVC](https://miro.medium.com/max/1078/1*tGcj5dHjVbxUmmN426c72Q.jpeg)

web.xml 範例

```xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app
 xmlns:javaee="http://java.sun.com/xml/ns/javaee"
 xmlns:web="http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd">
    <servlet-mapping>
        <servlet-name>HomePage</servlet-name>
        <url-pattern>/HomePage</url-pattern>
    </servlet-mapping>
</web-app>
```

* spring framework 1.0 (2003)
    * 核心技術：dependency injection, events, resources, i18n, validation, data binding, type conversion, SpEL, AOP.
    * 容易測試：mock objects, TestContext framework, Spring MVC Test
    * 資料庫造訪與數據的持久層：transactions, DAO support, JDBC, ORM, Marshalling XML
    * 網頁框架：Spring MVC and Spring WebFlux
    * 其他應用整合：remoting, JMS, JCA, JMX, email, tasks, scheduling, cache
    * 所有的設定都放在 WEB-INF 的資料夾中

* Spring MVC (2003)
    * spring 中最常用的模組
    * 一種符合 MVC 設計模式的架構，以實現邏輯分離的目的
    * Model：將數據透過POJO的方式封裝成物件以進行數據之間的傳遞
    * View：負責頁面渲染的內容，將HTML等內容(JSP)傳至Client端呈現
    * Controller：負責處理 Request的接收與處理

    ![MVC](https://miro.medium.com/max/882/1*Cb3Q0RFKFeNm6jnjTKbnTQ.jpeg)


Spring MVC 透過 DispatcherServlet 處理 Request 的流程

![DispatcherServlet](https://miro.medium.com/max/1204/1*D_6LZ7uYYKIyXVQ5LtdvrA.jpeg)

1. DispatcherServlet 收到 Request
2. DispatcherServlet 根據 Request 的 URL 向 HandlerMapping 查詢要調用的Controller
3. DispatcherServlet 調用 Controller
4. Controller 根據 Request 代的參數進行對應的處理，並將處理完的數據與View的名稱封裝成 ModelAndView 的物件後傳回 DispatcherServlet
5. DispatcherServlet 根據 View的名稱 從 xml 中檢查對應 View 的路徑
6. DispatcherServlet 調用指定的 View元件
7. 最後將 View 回傳給前端

* Spring Boot (2013)
    * 簡化 spring 大量的配置
    * 透過各種 starter 簡化各種複雜的設定以及第三方的 Library 的 xml 配置
    * 例如 spring-boot-starter-web
    > Starter for building web, including RESTful, applications using Spring MVC. Uses Tomcat as the default embedded container

以下這張圖說明 spring MVC 與 spring boot 建立一個可以印出 Hello World 專案的差別

![spring-boot-improve](https://miro.medium.com/max/1400/1*i1YSemFi_17WXPAlKbjFbQ.jpeg)