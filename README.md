# 手写Tomcat
   ## 概述：
   本项目是为了探究Tomcat底层原理，在学习之余自己实现了一个乞丐版Tomcat。目前主流的通信技术有WebSocket、NIO、Netty，本项目选择WebSocket进行实现
   ## 功能实现：
   1.项目紧依赖于log4j日志包和dom4j解析xml包<br/>
   2.实现了静态页面请求。<br/>
   3.实现了Servlet处理请求。<br/>
   4.可以读取web.xml中的ServletMapping。<br/>
   5.可以在server.properties中设置端口号及线程池大小，对Tomcat进行初始化<br/>
   6.对于不同的客户端Socket，用线程池进行处理。<br/>
   7.对不存在页面的404处理，页面异常的500处理<br/>
   ## 功能展示：
   ### 这是项目结构：
   
<div align=center><img width="50%" height="50%" src="imgs/微信截图_20190328150736.png"/></div>

   ### 项目启动：
   
<div align=center><img width="100%" height="100%" src="imgs/微信截图_20190328150957.png"/></div>

   ### 处理静态页面：
   
<div align=center><img width="100%" height="100%" src="imgs/微信截图_20190328151235.png"/></div>

   ### 处理Servlet：
   
<div align=center><img width="100%" height="100%" src="imgs/微信截图_20190328151445.png"/></div>

   ### 自定义的web.xml结构为:
   
<div align=center><img width="100%" height="100%" src="imgs/微信截图_20190328151920.png"/></div>

   ### 处理404：
   
<div align=center><img width="100%" height="100%" src="imgs/微信截图_20190328151534.png"/></div>

   ### 处理500：
   
<div align=center><img width="100%" height="100%" src="imgs/微信截图_20190328151728.png"/></div>
