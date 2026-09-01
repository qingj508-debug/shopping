package cn.lili.modules.file.plugin;

import cn.lili.modules.file.entity.enums.OssEnum;

import java.io.InputStream;
import java.util.List;

/**
 * 文件插件接口
 *
 * @author Chopper
 */
public interface FilePlugin {


    /**
     * 插件名称
     */
    OssEnum pluginName();

    /**
     * 文件路径上传
     *
     * @param filePath
     * @param key
     * @return
     */
    String pathUpload(String filePath, String key);

    /**
     * 文件流上传
     *
     * @param inputStream
     * @param key
     * @return
     */
    default String inputStreamUpload(InputStream inputStream, String key) {
        return inputStreamUpload(inputStream, key, "application/octet-stream");
    }

    /**
     * 文件流上传（携带内容类型）
     *
     * @param inputStream 文件流
     * @param key         文件key
     * @param contentType 文件类型
     * @return 文件访问地址
     */
    String inputStreamUpload(InputStream inputStream, String key, String contentType);


    /**
     * 删除文件
     *
     * @param key
     */
    void deleteFile(List<String> key);

}
