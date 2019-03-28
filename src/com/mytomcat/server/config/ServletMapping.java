package com.mytomcat.server.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.junit.Test;

/**
 * 读取web.xml中的ServletMapping信息
 * 
 * @author 四爷
 *
 */
public class ServletMapping {
	private static Logger logger = Logger.getLogger(ServletMapping.class);
	private static List<ServletConfig> configs = new ArrayList<>();

	public static List<ServletConfig> getConfigs() {
		return configs;
	}

	public static void setConfigs(List<ServletConfig> configs) {
		ServletMapping.configs = configs;
	}

	/**
	 * 在静态代码块中读取web.xml中的ServletMapping信息
	 */
	static {
		String path = System.getProperty("user.dir") + "\\WebContent\\web.xml";
		File web = new File(path);
		if (web.exists()) {
			Document doc = null;
			SAXReader reader = new SAXReader();
			try {
				doc = reader.read(web);
				Element root = doc.getRootElement();
				List<Element> servlets = root.elements("servlet");
				for (Element e : servlets) {
					String name = e.elementText("servlet-name");
					String urlMapping = e.elementText("url-pattern");
					String clz = e.elementText("servlet-class");
					// System.out.println(name + "," + urlMapping + "," + clz);
					ServletConfig config = new ServletConfig(name, urlMapping, clz);
					configs.add(config);
				}
				logger.info("Url-Mapping:" + configs);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		} else {
			logger.error("web.xml is not exists!");
		}

	}

//	@Test
//	public void run() {
//		String path = System.getProperty("user.dir") + "\\WebContent\\web.xml";
//		File web = new File(path);
//		if (web.exists()) {
//			Document doc = null;
//			SAXReader reader = new SAXReader();
//			try {
//				doc = reader.read(web);
//				Element root = doc.getRootElement();
//				List<Element> servlets = root.elements("servlet");
//				for (Element e : servlets) {
//					String name = e.elementText("servlet-name");
//					String urlMapping = e.elementText("url-pattern");
//					String clz = e.elementText("servlet-class");
//					System.out.println(name + "," + urlMapping + "," + clz);
//					ServletConfig config = new ServletConfig(name, urlMapping, clz);
//					configs.add(config);
//				}
//
//			} catch (DocumentException e) {
//				e.printStackTrace();
//			}
//		} else {
//			logger.error("web.xml is not exists!");
//		}

}
