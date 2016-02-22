package com.lnb.simpleframework.adapter.binder;

import java.io.Serializable;

/**
 * 实体类的基类
 *
 * 提供数据类型成员变量,用于在工厂类中区分对应的Item
 */
public class BaseResource implements Serializable {
	private static final long serialVersionUID = 1L;
	/**数据类型*/
	public DataType dataType;
}
