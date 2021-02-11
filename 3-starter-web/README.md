# Web Starter

這篇文章會使用 spring-boot-starter-web 建立 web 服務

Web Starter 提供
* RESTful API
* Spring MVC (以自動配置取代那些繁瑣的 xml 配置)
* 內建預設的 Tomcat 容器 (可以直接執行服務而不需使用其他網頁伺服器容器)

可以基於上一次的結果加入

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

> Web Starter 已經含有 
>   * spring-boot-starter
>   * jackson
>   * spring-core
>   * spring-mvc
>   * spring-boot-starter-tomcat
>
> 因此原本的 spring-boot-starter dependency 可以拿掉 

或者使用 https://start.spring.io/ 並在右邊的 dependency 輸入 web 

產生含有 web 的專案。

一樣用 Intellij import 後執行，可從 log 訊息看到程式聽在 localhost:8080

接著用瀏覽器連時，會看到 error page，這是因為我們還沒有加入任何的 controller，處理請求
> 注意 chrome 預設會使用 https 連接，建議使用無痕

## MVC

* M：Model，對傳遞的數據進行封裝
* V：View，生成呈現資料相關的頁面給客戶端
* C：Controller，處理Request並回傳相關頁面或數據給客戶端

其中 View 的部分，在早期是使用 JSP 進行開發，而現在前後端分離的概念成為主流，也因此能使用更適合的前端框架 (Vue.js、Angular、React) 專注於 View 的開發。

後端則專注於 API 、業務邏輯、資料庫存取等。

![MVC](https://miro.medium.com/max/1282/0*bR9SCA9tN1bSw4L3.png)

* Controller：API的部分，也就是處理 Request 的部分
* Service：處理業務邏輯的部分
* Repository：造訪資料庫，對資料庫進行取得資料等相關動作
* Model：與資料庫交換資料時對資料的封裝層

在 spring boot 中，使用各種 Annotation 簡化開發，以下分別介紹。

## Controller

處理 API 接口

* `@Controller`：標記 class 作為 controller
* `@RestController`：同等於 @Controller + @ResponseBody 

開發 RESTful API 使用

* `@GetMapping()`: 對應到 Get 
* `@PostMapping()`: 對應到 Post
* `@PutMapping()`: 對應到 Put 
* `@DeleteMapping()`: 對應到 Delete
* `@RequestMapping()`: 自訂或修改 Request URL 的請求路徑

例如，取得列表的 RESTful API

```java
@Controller
@RequestMapping("/api")
public class Controller {  
  @ResponseBody
  @GetMapping("/items")
  public List<Object> getItems() { ... };
}
```

其等同於

```java
@RestController
@RequestMapping("/api")
public class Controller {
  @GetMapping("/items")
  public List<Object> getItems() { ... };
}
```

可以用以下 API 存取

```
http://{ip}:{port}/api/items
```

### URL 帶參數

若需要參數，例如 `http://.../items?id=12&name=apple`

```java
@GetMapping("/items")
public void getItems(Long id, String name) {...}
```

> 如果呼叫 API 沒有帶參數，則這些值會被填上 NULL。

用 `@RequestParam` 指定必要參數

```java
@GetMapping("/items")
public void getItems(@RequestParam Long id, String name) {...}
```

> 如果呼叫 API 沒有加上指定參數，則會回傳 400 Bad Request。

### 參數放在 URL 後面

例如，Get `http://.../items/12/apple`，使用 `@PathVariable`

```java
@GetMapping("/items/{id}/{name}")
public void getItems(
  @PathVariable Long id,
  @PathVariable String name
) {...}
```

### 參數放在 Body 裡面

例如，Get

```
http://.../items
[BODY]
{
  "id":12,
  "name":"apple"
}
```

則寫成

```java
@GetMapping("/items")
public void getItems(@RequestBody Item item) {
  item.getId();
  item.getName();
  ...
}
```

其中 Item 為一個定義好的 POJO。

### 回傳參數

什麼都不回傳

```java
@GetMapping("/items")
public void getItems(Long id) { ... }
```

指定回傳的資料型別

```java
@GetMapping("/items")
public Integer getItems(Long id) { 
  Integer result;
  ...
  return result;
}
```

回傳一個物件

```java
@GetMapping("/items")
public Item getItems(@RequestBody Item item) {
  item.setName("banana");
  return item;
}
```

使用 ResponseEntity 回傳

```java
@GetMapping("/items")
public ResponseEntity<Item> getItems(@RequestBody Item item) {
  item.setName("banana");
  return new ResponseEntity<>(item, HttpStatus.OK);
}
@GetMapping("/items")
public ResponseEntity<Item> getItems(@RequestBody Item item) {
  item.setName("banana");
  HttpHeaders headers = new HttpHeaders();
  headers.add("total-item-count", "20");
  return new ResponseEntity<>(item, headers, HttpStatus.OK);
}
```

> 可以設定 HTTP 的 Response Code、Response Header、Response Body。

也能使用 ResponseEntity 的 static 方式

```java
@GetMapping("/items")
public ResponseEntity<Item> getItems(@RequestBody Item item) {
  item.setName("banana");
  return ResponseEntity.status(HttpStatus.OK).body(item);
}
@GetMapping("/items")
public ResponseEntity<Item> getItems(@RequestBody Item item) {
  item.setName("banana");
  return ResponseEntity
    .status(HttpStatus.OK)
    .header("total-item-count", "20")
    .body(item);
}
```

## 融會貫通

在專案 src 中新增如下，結構

```
+---src
    ＋---main
        ＋---java.com.springboot.management.managementsystem
            +---ManagementSystemApplication.java
            |
            +---controller
            |   +---ProductController.java
            |
            +---dto
                +---ProductDTO.java
```

ProductDTO.java 內容

```java
@AllArgsConstructor
@Data
public class ProductDTO {
    private Long id;        // 產品的ID
    private String name;    // 產品的名稱
    private Integer remain; // 產品的剩餘數量
}
```
> `@AllArgsConstructor` 與 `@Data` 為套件 lombok 提供的 Annotation，
>
> `@AllArgsConstructor` 產生有所有參數的 constructor。
> 
> `@Data` 產生 getter、setter、ToString、EqualsAndHashCode、RequiredArgsConstructor。
>
> 第一次引入 `@Data` 時，可以使用 Intellij 提供的修正鍵 （`Windows`: Alt+Enter、`Ｍac`: Option+Enter） 幫忙引入套件，選擇 Add lombok to classpath，即可看到 pom.xml 自動引入 lombok。
>
> 注意：在 Intellij 上開發時，還需要安裝 lombok 的 plugin，否則自動補齊會找不到 lombok 產生的程式碼。
>
> 更多 lombok 可參考：https://kucw.github.io/blog/2020/3/java-lombok/

在 ProductController.java 中新增如下 (以下只顯示一部分程式碼，其他可直接參考專案內容)

```java
@RestController
@RequestMapping("/products")
public class ProductController {
    // ... others
    @GetMapping("/list1")
    public ResponseEntity<ArrayList<ProductDTO>> getBook() {
        return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<ProductDTO>(this.products.values()));
    }
    // ... others
}
```

回到 ManagementSystemApplication.java 執行專案。

接著就可以透過瀏覽器或是 postman 輸入 `http://localhost:8080/products/list1` 就可以看到如下結果

```json
[{"id":1,"name":"apple","remain":10},{"id":2,"name":"book","remain":20},{"id":3,"name":"car","remain":30}]
```

只要寫幾行 @Annotation 就可以有 web api 的功能，真的是超級方便呢！

