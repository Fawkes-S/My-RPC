# My-RPC
## 介绍
在新学习了高并发，网络编程和Netty框架后，我尝试开发了这个基于**Netty、Spring、Zookeeper**实现的RPC框架。

它可以支持服务器客户端长连接，采用IO异步调用，服务器支持心跳检测，采用JSON序列化实现编解码，基于Spring注解和动态代理实现调用，最后基于zookeeper的Watcher机制实现了客户端连接的动态管理、监听和发现功能，并实现了服务器注册功能。

## 架构图
![image]([https://github.com/Fawkes-S/RPC/blob/main/SJC.consumer/target/classes/RPC%20Architecture.png](https://github.com/Fawkes-S/My-RPC/blob/master/RPC%20Architecture.png)https://github.com/Fawkes-S/My-RPC/blob/master/RPC%20Architecture.png)
