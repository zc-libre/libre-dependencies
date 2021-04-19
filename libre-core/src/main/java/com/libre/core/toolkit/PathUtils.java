package com.libre.core.toolkit;

import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.util.URLUtil;
import org.springframework.lang.Nullable;
import org.springframework.util.ResourceUtils;
import java.io.File;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * @author zhao.cheng
 * @date 2021/4/19 14:29
 */
public class PathUtils extends PathUtil {


    /**
     * 获取jar包运行时的当前目录
     * @return {String}
     */
    @Nullable
    public static String getJarPath() {
        try {
            URL url = Objects.requireNonNull(PathUtil.class.getResource(StringPool.SLASH)).toURI().toURL();
            return PathUtils.toFilePath(url);
        } catch (Exception e) {
            String path = Objects.requireNonNull(PathUtil.class.getResource(StringPool.EMPTY)).getPath();
            return new File(path).getParentFile().getParentFile().getAbsolutePath();
        }
    }

    @Nullable
    public static String toFilePath(@Nullable URL url) {
        if (url == null) { return null; }
        String protocol = url.getProtocol();
        String file = URLUtil.decode(url.getPath(), StandardCharsets.UTF_8.name());
        if (ResourceUtils.URL_PROTOCOL_FILE.equals(protocol)) {
            return new File(file).getParentFile().getParentFile().getAbsolutePath();
        } else if (ResourceUtils.URL_PROTOCOL_JAR.equals(protocol)
                || ResourceUtils.URL_PROTOCOL_ZIP.equals(protocol)) {
            int ipos = file.indexOf(ResourceUtils.JAR_URL_SEPARATOR);
            if (ipos > 0) {
                file = file.substring(0, ipos);
            }
            if (file.startsWith(ResourceUtils.FILE_URL_PREFIX)) {
                file = file.substring(ResourceUtils.FILE_URL_PREFIX.length());
            }
            return new File(file).getParentFile().getAbsolutePath();
        }
        return file;
    }

}
