## Ubuntu redis 安装

- 下载压缩包, tar xvfz redis-***.tar.gz
- 进入解压目录, cd redis-***
- 编译, sudo make
- 安装, sudo make install
- 测试安装结果, sudo make test

## redis 启动, 关闭

- 启动 : cd redis-***, redis-server [redis.conf] (指定配置文件, 默认redis.conf, 单机多实例时, 指定redis运行示例)
- 关闭 : redis-cli shutdown

## redis 连接客户端

- redis-cli [-h 127.0.0.1 -p 6379](默认本地, 6379, 单机多实例时或访问其他redis, 可以添加)
- 操作redis, 参考 http://redisdoc.com/

## redis 单机多实例

- cd redis-***
- c 一份redis.conf, cp -r redis.conf redis_6380.conf
- 修改redis_6380.conf, vi redis_6380.conf
    - 修改PID存放位置 pidfile , /var/run/redis/redis_6480.pid
    - 修改端口 port, 6380
    - 修改日志文件 logfile , /var/log/redis/redis_6380.log
    - 修改数据库文件 rdbfile / dbfilename , dbfilenamedump_6380.rdb / dbfilename dump_6380.rdb

- 启动两个实例
    - redis-server redis.conf
    - redis-server redis_6380.conf

- 连接客户端
    - redis-cli -h 127.0.0.1 -p 6379
    - redis-cli -h 127.0.0.1 -p 6380
- ok.

## redis 应用场景

- http://www.cnblogs.com/shanyou/archive/2012/09/04/2670972.html
