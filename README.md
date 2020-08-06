# wiki-auth使用说明

## 1 场景

本项目主要用于wiki系统(https://github.com/phachon/mm-wiki)的ldap认证系统

## 2 使用

### 2.1 修改配置

修改src/resources/application.properties 文件中的ldap配置为自己的ldap服务器配置


### 2.2 编译并启动端口

在项目根目录（即wiki-auth目录下）下执行：

```shell

./gradlew build

```
编译成功后可以看到wiki-auth/build/libs目录下有刚生成的wiki-auth-1.0.0.jar

```java

java -jar build/libs/wiki-auth-1.0.0.jar

```

执行上述命令即可启动项目，项目默认使用8080端口接收请求

### 2.3 接口

目前项目只有一个针对wiki的接口：/mmwiki/auth

#### 2.3.1 请求数据设置
如下：
1. Content-Type 设置为：application/x-www-form-urlencode即可
2. 填写form的数据
   username:用户名
   password:密码
   ext_data:固定写"mmwiki"
   
eg: 
```shell

curl -X POST -d "username=xdxiaodao&password=123456@&ext_data=mmwiki" http://localhost:8080/mmwiki/auth

```
   
#### 2.3.2 正确返回结果
如果用户名正确会返回如下数据

```json
{
  "message": "",
  "data": {
    "mobile": "",
    "phone": "",
    "email": "xdxiaodao@gmail.com",
    "department": "xxx>>xxx",
    "position": "",
    "location": "",
    "im": "",
    "given_name": "xdxiaodao"
  }
}
```

#### 2.3.3 错误返回结果

如果用户名不存在或者校验失败，则返回如下内容：

```json
{
  "message": "认证失败",
  "data": {}
}
```

## 3 增加mmwiki登录

1. 点击配置管理->登录认证->新增登录认证，依次填写如下信息：

认证名称：ldap认证
用户名前缀： wiki （也可以是其他自己定义的前缀)
认证URL: http://localhost:8080/mmwiki/auth  （接口服务地址）
扩展数据：mmwiki

2. 配置后使能统一登录即可正常使用ldap登录。
