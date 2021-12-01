package com.yuxing.trainee.generator.infrastructure.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * 文件工具类型
 *
 * @author yuxing
 */
@Slf4j
public class FileUtils {

    private FileUtils() {
    }

    public static File create(String path) throws IOException {
        File file = new File(path);

        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                log.error("创建文件{}时，创建父级文件夹失败", path);
                return null;
            }
        }

        if (file.createNewFile()) {
            return file;
        }

        log.error("创建文件{}失败", path);
        return null;
    }
}
