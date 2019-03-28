package com.mytomcat.server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.mytomcat.server.config.ServletConfig;
import com.mytomcat.server.config.ServletMapping;
import com.test.servlet.TestServlet;

/**
 * MyTomcat启动类
 * 
 * @author 四爷
 *
 */
public class TomcatServer {
	private static Logger logger = Logger.getLogger(TomcatServer.class);
	// 默认端口号8080
	private static int port = 8080;
	// ServletMapping
	public static HashMap<String, Class> servletMap = new HashMap<String, Class>();
	// 使用线程池来处理接受到的客户端的Socket
	private static ExecutorService fixedThreadPool=Executors.newFixedThreadPool(3);

	/**
	 * 可以通过构造方法修改监听端口号
	 * 
	 * @param port
	 */
	public TomcatServer(int port) {
		this.port = port;
	}

	public TomcatServer() {
	}

	/**
	 * 启动MyTomcat方法
	 */
	public void start() {
		initMyTomcat();
		initServlets();
		ServerSocket server;
		Socket socket;
		try {
			server = new ServerSocket(port);
			logger.info("MyTomcat started on:" + port);
			while (true) {
				socket = server.accept();
				System.out.println();
				logger.info(socket);
				// 处理任务
				Thread thread = new SocketProcessor(socket);
				fixedThreadPool.execute(thread);
				logger.info("Now ThreadPool:"+fixedThreadPool);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

		}

	}

	/**
	 * 初始化ServletMapping方法，将配置文件中的请求路径与对应的类路径读取到内存中。
	 * 
	 * @throws ClassNotFoundException
	 */
	private void initServlets() {
		try {
			for (ServletConfig servletConfig : ServletMapping.getConfigs()) {
				servletMap.put(servletConfig.getUrlMapping(), (Class<Servlet>) Class.forName(servletConfig.getClz()));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 初始化MyTomcat配置
	 */
	private static void initMyTomcat() {
		Properties pro = new Properties();
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader("./config/server.properties"));
			pro.load(reader);
		} catch (Exception e) {
			e.printStackTrace();
		}
		port = Integer.parseInt(pro.getProperty("port"));
		fixedThreadPool = Executors.newFixedThreadPool(Integer.parseInt(pro.getProperty("threadPoolSize")));
		logger.info("MyTomcat port:"+port+",ThreadPool:"+fixedThreadPool);
	}

	public static void main(String[] args) throws IOException {
		new TomcatServer().start();
	}
}
