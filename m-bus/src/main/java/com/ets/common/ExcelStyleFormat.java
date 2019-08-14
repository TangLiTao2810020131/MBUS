package com.ets.common;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;

public class ExcelStyleFormat{
	/**
	 * 文本类型
	 */
	private CellStyle numberStyle;
	
	/**
	 * 文本类型格式化
	 */
	private DataFormat numberformat;
	
	/**
	 * 文本类型格式 千分位
	 */
	private CellStyle numberStyleSplit;
	
	/**
	 * 文本类型格式化 千分位
	 */
	private DataFormat numberformatSplit;

	public CellStyle getNumberStyle() {
		return numberStyle;
	}

	public void setNumberStyle(CellStyle numberStyle) {
		this.numberStyle = numberStyle;
	}

	public DataFormat getNumberformat() {
		return numberformat;
	}

	public void setNumberformat(DataFormat numberformat) {
		this.numberformat = numberformat;
	}

	public CellStyle getNumberStyleSplit() {
		return numberStyleSplit;
	}

	public void setNumberStyleSplit(CellStyle numberStyleSplit) {
		this.numberStyleSplit = numberStyleSplit;
	}

	public DataFormat getNumberformatSplit() {
		return numberformatSplit;
	}

	public void setNumberformatSplit(DataFormat numberformatSplit) {
		this.numberformatSplit = numberformatSplit;
	}

	public ExcelStyleFormat(CellStyle numberStyle, DataFormat numberformat, CellStyle numberStyleSplit,
			DataFormat numberformatSplit) {
		super();
		this.numberStyle = numberStyle;
		this.numberformat = numberformat;
		this.numberStyleSplit = numberStyleSplit;
		this.numberformatSplit = numberformatSplit;
	}

	public ExcelStyleFormat() {
		super();
	}
	
	
}
