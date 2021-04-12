package com.libre.mybatis.prop;

import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Data;
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

}
