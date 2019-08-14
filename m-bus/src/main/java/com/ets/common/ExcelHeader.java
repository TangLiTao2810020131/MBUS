package com.ets.common;

/**
 * 
 * @ClassName: ExcelHeader
 * @Description: excel表头
 *
 */
public class ExcelHeader implements Comparable<ExcelHeader> {
	/**
	 * excel的标题名称
	 */
	private String title;
	/**
	 * 每一个标题的顺序
	 */
	private int order;
	/**
	 * 对应方法名称
	 */
	private String methodName;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	@Override
	public int compareTo(ExcelHeader o) {
		return order > o.order ? 1 : (order < o.order ? -1 : 0);
	}

	@Override
	public boolean equals(Object obj) {
		return super.equals(obj);
	}

	@Override
	public int hashCode() {
		return super.hashCode();
	}

	public ExcelHeader(String title, int order, String methodName) {
		super();
		this.title = title;
		this.order = order;
		this.methodName = methodName;
	}

	@Override
	public String toString() {
		return "ExcelHeader [title=" + title + ", order=" + order + ", methodName=" + methodName + "]";
	}
}