## 架构使用文档

#### 1.代码生成器

> 主要生成controller，service，entity，dao，mapper基础CURD
>
> src/test/java 
>
> 			->code.example.demo.CodeGenerator.java 生成器入口
> 	
> 			->code.example.demo.ProjectConstant.java 生成基础配置
>
> CodeGenerator中可以配置java文件路径，资源文件路径
>
> 			->resources.template.generator  包下为生成模板
>
> 实体生成后会存在报错，导入包即可

#### 2.common

> com.libertad.demo.common.aspects 日志切面
>
> com.libertad.demo.common.core  boot基础配置及多数据源
>
> com.libertad.demo.common.excel 导入导出
>
> com.libertad.demo.common.exceptions 自定义异常
>
> com.libertad.demo.common.fileupload 多文件上传
>
> com.libertad.demo.common.jsonserializer 序列化
>
> com.libertad.demo.common.mail 邮件
>
> com.libertad.demo.common.pojo 基础实体
>
> com.libertad.demo.common.redis dedis工具
>
> com.libertad.demo.common.result 返回值模板
>
> com.libertad.demo.common.utils 一些工具
>
> com.libertad.demo.common.startuprunner 启动初始化资源
>
> com.libertad.demo.common.token token登录工具
>
> com.libertad.demo.common.Interceptor 拦截器
>
> com.libertad.demo.common.dense 加密解密

#### 3.基础功能

> com.s.t.m.log 异步日志功能（操作日志）

#### 4.项目个性配置

``` properties
   config.druidUserName=admin
   config.druidPassword=admin
   config.druidAllows=127.0.0.1,127.0.0.3
   config.uploadPath=E://img
   config.uploadMaxFileSize=10MB
   config.uploadMaxRequestSize=10MB
   swagger.path=com.libertad.demo
   swagger.enable=trueonProperties=druid.stat.mergeSql=true;druid.stat.slowSqlMillis=5000
```

#### 5.druid使用

**http://localhost:1234/druid**

>  账号：admin  密码：admin
>
>  org.springframework.boot.templates.servlet.ServletRegistrationBean 可以更改账号密码，并且配置白名单和黑名单

#### 6.使用Swagger

**http://localhost:1234/doc.html**

> 自动生成Api文档可以debug，在controller层注解
>
> http://www.xiaominfo.com/2018/08/29/swagger-bootstrap-ui-description/#%E8%AF%A6%E7%BB%86%E8%AF%B4%E6%98%8E详细使用说明

```java
//controller类注解，描述接口类型
@Api(tags = { "日志操作接口" })
//描述方法名称
@ApiOperation(value = "动态参数分页查询")
//在实体中使用注解文档中显示对象名称
@ApiModel(value="日志对象")
//显示属性名称
@ApiModelProperty(value="主键",name="id")
```
#### 7.Test

> code.example.test单元测试，有一些方法的使用方式

#### 8.redis缓存使用(默认缓存使用)

``` java 
	
	@Cacheable("product")
	@Cacheable(value = {"product","order"}, key = "#root.targetClass+'-'+#id")
	@Cacheable(value = "product", key = "#root.targetClass+'-'+#id")
	自定义cacheManager
	@Cacheable(value = "product", key = "#root.targetClass+'-'+#id” cacheManager="cacheManager")
	
	应用到写数据的方法上，如新增/修改方法
	@CachePut(value = "product", key = "#root.targetClass+'-'+#product.id")

	即应用到移除数据的方法上，如删除方法
	@CacheEvict(value = "product", key = "#root.targetClass+'-'+#id")
	提供的SpEL上下文数据
```

| 名字          | 位置       | 描述                                                         |         示例         |
| ------------- | ---------- | ------------------------------------------------------------ | :------------------: |
| methodName    | root对象   | 当前被调用的方法名                                           |   #root.methodName   |
| method        | root对象   | 当前被调用的方法                                             |  #root.method.name   |
| target        | root对象   | 当前被调用的目标对象                                         |     #root.target     |
| targetClass   | root对象   | 当前被调用的目标对象类                                       |  #root.targetClass   |
| args          | root对象   | 当前被调用的方法的参数列表                                   |    #root.args[0]     |
| caches        | root对象   | 当前方法调用使用的缓存列表（如@Cacheable(value={"cache1", "cache2"})），则有两个cache | #root.caches[0].name |
| argument name | 执行上下文 | 当前被调用的方法的参数，如findById(Long id)，我们可以通过#id拿到参数 |       #user.id       |
| result        | 执行上下文 | 方法执行后的返回值（仅当方法执行之后的判断有效，如‘unless’，'cache evict'的beforeInvocation=false） |       #result        |

#### 9.新增登录,异步操作日志
