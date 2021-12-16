`/signin`
1. 현재 로그인 중인지 확인
   1. 로그인 중이라면 로그인 중이던 기기 강제 로그아웃
   2. 아닐 경우 토큰 발급

`/refresh`
1. refresh 토큰이 유효하면 access 토큰 재발급

`/signup`
1. 항상 api-server로 라우팅

`/index`
1. 토큰 검사

`/admin`
1. 토큰 검사시 권한까지 검사