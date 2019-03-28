package com.mytomcat.server.config;

/**
 * Servlet�����࣬����<servlet-name><url-pattern><servlet-class>��ǩ����
 * 
 * @author ��ү
 *
 */
public class ServletConfig {
	private String name;
	private String urlMapping;
	private String clz;

	public ServletConfig(String name, String urlMapping, String clz) {
		this.name = name;
		this.urlMapping = urlMapping;
		this.clz = clz;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrlMapping() {
		return urlMapping;
	}

	public void setUrlMapping(String urlMapping) {
		this.urlMapping = urlMapping;
	}

	public String getClz() {
		return clz;
	}

	public void setClz(String clz) {
		this.clz = clz;
	}

	@Override
	public String toString() {
		return "ServletConfig [name=" + name + ", urlMapping=" + urlMapping + ", clz=" + clz + "]";
	}

}
