# 虚拟现实后台文档

## 接口文档

接口访问地址`http://ip:port/doc.html`

由于权限认证采用了JWT和TOKEN的机制，api文档需要先调用login接口登录后才可以操作
login接口返回的数据如下：
```json

{
  "code": 200,
  "msg": "OK(认证成功)",
  "timestamp": "2021-12-04T16:44:09.762+0000",
  "data": {
    "userinfo": {
      "id": 1,
      "sn": "1231231",
      "username": "林金保",
      "password": null,
      "sex": "男",
      "mobile": "15195355289",
      "qq": null,
      "photo": null,
      "roleID": 2,
      "createDate": "2021-12-02T13:50:35.000+0000",
      "creator": null,
      "updateDate": "2021-12-02T13:50:35.000+0000",
      "updater": null
    },
    "token": "eyJhbGciOiJIUzI1NiIsIlR5cGUiOiJKd3QiLCJ0eXAiOiJKV1QifQ.eyJwYXNzd29yZCI6IjEyMzQ1NiIsImV4cCI6MTYzODYzNzE0OSwidXNlcm5hbWUiOiIxMjMxMjMxIn0.Ys29v7ukt73qm8FjWMBqsgoS31SDe2wJzVBy6iEfJJs"
  },
  "ttl": 126,
  "count": null
}

```
取其中的token字段，在后续的请求中，放入请求头`admin-token`中


### 批量操作API文档
```json
[{
"id":1,
"studentId":1001
},
{
    "id":2,
    "studentId":1000
}
]
```


**AfterScript设置（待实现）**
```javascript

var code=ke.response.data.code;
if(code==200){
    //判断,200
    //获取token
    var token=ke.response.data.data.token;
    var userinfo=JSON.stringify(ke.response.data.userinfo)
    //1、如何参数是Header，则设置当前逻辑分组下的全局Header
    ke.global.setHeader("admin-token",token);
    localStorage.setItem("jwt",res.data.token);  
	localStorage.setItem("userinfo",JSON.stringify(userinfo))
}

```


## 部署文档


