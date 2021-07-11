package com.libre.mybatis.prop;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author zhao.cheng
 * @date 2021/4/12 11:20
 */
@Data
@ConfigurationProperties("libre.mybatis")
public class LibreMyBatisProperties {

	/**
	 * 数据库类型
	 */
	private DbType dbType = DbType.MYSQL;

	private Boolean overflow = Boolean.TRUE;

	private Long maxLimit = 500L;

	/**
	 * 分页配置
	 */
	private Page page = new Page();

	@Getter
	@Setter
	public static class Page {

		/**
		 * 当前页字段名称
		 */
		private String currentField = "page";

		/**
		 * 每页显示条数字段名称
		 */
		private String sizeField = "size";

		/**
		 * 排序字段名称
		 */
		private String sortField = "sort";

	}

}
