## 后端技术栈
- 1、druid数据库连接池
- 2、mybatis及SqlGen工具类
- 3、配置统一返回值
- 4、登录功能，使用JWT返回token
  - 作用：前端解析token获取对应的信息
- 5、集成Redis
  - 用来保存邮箱验证码
- 6、配置logback，导入logback.xml文件
- 7、导入分页插件pagehelper
  - 通过AOP和注解的方式来拦截并执行分页操作
- 8、使用拦截器+使用注解放行指定的接口
- 9、引入OSS并创建对应的接口（并未使用，但是留存接口）
- 10、使用Hutool工具中的树结构工具TreeUtil处理分页数据，将查询的数据形成树形结构

## 前端技术栈
- 1、Vite
- 2、Vue3
- 3、TypeScript
- 4、element-ui-plus
- 4、vue-router路由
- 5、pinia
  - 用于将数据存储到浏览器的Local Storage
- 6、jwt
  - 用于解析后端传回的token数据
- 7、axios
  - 对axios进行了封装，封装后的文件放置在/http/index.ts中
- 8、MD5加密