package com.ets.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * 
 * @ClassName: ExcelResources
 * @Description: 说明某个属性对应的标题
 *
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {
	/**
	 * 属性的标题名称
	 * 
	 * @return
	 */
	String title();

	/**
	 * 在excel的顺序
	 * 
	 * @return
	 */
	int order() default 9999;
}
