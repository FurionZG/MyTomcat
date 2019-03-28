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
 * MyTomcat������
 * 
 * @author ��ү
 *
 */
public class TomcatServer {
	private static Logger logger = Logger.getLogger(TomcatServer.class);
	// Ĭ�϶˿ں�8080
	private static int port = 8080;
	// ServletMapping
	public static HashMap<String, Class> servletMap = new HashMap<String, Class>();
	// ʹ���̳߳���������ܵ��Ŀͻ��˵�Socket
	private static ExecutorService fixedThreadPool=Executors.newFixedThreadPool(3);

	/**
	 * ����ͨ�����췽���޸ļ����˿ں�
	 * 
	 * @param port
	 */
	public TomcatServer(int port) {
		this.port = port;
	}

	public TomcatServer() {
	}

	/**
	 * ����MyTomcat����
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
				// ��������
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
	 * ��ʼ��ServletMapping�������������ļ��е�����·�����Ӧ����·����ȡ���ڴ��С�
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
	 * ��ʼ��MyTomcat����
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
