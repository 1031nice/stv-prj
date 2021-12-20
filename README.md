stove dev camp 2기
개인 프로젝트

API gateway: http://localhost:8090

API server: http://localhost:8080

# API
## 사용자 관련 API
### 로그인
```
Request:
URI = http://localhost:8090/signin
HTTP Method = POST 
Parameters = {username=value, password=value}

Response:
Status = 200
Body = {"refresh-token":"value", "access-token":"value"}
```
### 회원가입
```
Request:
URI = http://localhost:8090/signup
HTTP Method = POST 
Parameters = {username=value, password=value}

Reponse:
Status = 200
```

## 메뉴 관련 API
### 메뉴 조회(USER, OWNER)
```
Request:
URI = http://localhost:8090/menus
HTTP Method = GET
Header = [Authorization : "Bearer access-token"]

Reponse:
Status = 200
Body = [{"id":1,"name":"menu1","price":1000},{"id":2,"name":"menu2","price":2000}]
```
### 메뉴 추가(OWNER)
```
Request:
URI = http://localhost:8090/menus
HTTP Method = POST 
Header = [Authorization : "Bearer access-token"]
Parameters = {name=value, price=value}

Reponse:
Status = 200
Body = {"id":1,"name":"newMenu","price":15000}
```
