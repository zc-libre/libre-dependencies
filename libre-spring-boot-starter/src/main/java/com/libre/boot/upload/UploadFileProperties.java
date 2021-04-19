package com.libre.boot.upload;

import com.libre.core.toolkit.PathUtils;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;

/**
 * 文件上传配置
 *
 * @author L.cm
 */
@Getter
@Setter
@ConfigurationProperties("libre.upload")
public class UploadFileProperties {

	/**
	 * 上传的文件 路径匹配
	 */
	private String uploadPathPattern = "/upload/**";

	/**
	 * 文件保存目录，默认：jar 包同级目录
	 */
	@Nullable
	private String savePath = PathUtils.getJarPath() + "/upload";

}
