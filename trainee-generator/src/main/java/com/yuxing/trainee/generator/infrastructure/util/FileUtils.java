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

    /**
     * 创建文件
     *
     * @param path    文件路径
     * @param isCover 文件已存在时是否允许覆盖
     * @return file || null
     * @throws IOException exception
     */
    public static File create(String path, boolean isCover) throws IOException {
        File file = new File(path);

        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                log.error("创建文件{}时，创建父级文件夹失败", path);
                return null;
            }
        }

        // 若文件已存在，且允许覆盖，则创建新文件
        if (file.exists()) {
            if (isCover && file.delete() && (file = new File(path)).createNewFile()) {
                return file;
            } else {
                log.error("创建文件{}失败", path);
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
