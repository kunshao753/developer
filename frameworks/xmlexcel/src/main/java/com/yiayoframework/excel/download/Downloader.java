package com.yiayoframework.excel.download;//package com.yiayoframework.base.download;
//
//
//import com.google.common.collect.Lists;
//import com.yiayoframework.base.mapper.JsonMapper;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.*;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.TimeUnit;
//
///**
// * @authher 李银 2017年12月2日 08:10:35
// */
//public class Downloader {
//
//    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
//
//    private Map</**下载类型*/String, Downloadable> downloaderMap;
//
//    private String appName;
//
//    private String downloadDomain;
//
//    private RedisTool redisTool;
//
//    /**
//     * 生成下载文件的缓存类型
//     *
//     * @see StorageType
//     */
//    private StorageType storageType = StorageType.nfs;
//
//    /**
//     * 存文件的基础地址
//     */
//    private String rootDir;
//
//    public static final String DOWNLOAD_TITLES = "downloadTitles";
//
//    public String getRootDir() {
//        if (rootDir == null) {
//            switch (EnvConstants.getType()) {
//                case dev:
//                case test:
//                case pre:
//                case online:
//                    this.rootDir = "/download/" + this.appName + "/";
//                    break;
////                return "/download/" + downloader.getAppName() + "/" + YiayoDateUtils.toString(new Date(), "yyyy-MM-dd") + "/";
//                default:
//                    this.rootDir = "C://download/" + this.appName + "/";
////                return "C://download/" + downloader.getAppName() + "/" + YiayoDateUtils.toString(new Date(), "yyyy-MM-dd") + "/";
//            }
//        }
//        return rootDir;
//    }
//
//    /**
//     * 文件保存路径，按文件日期分档
//     *
//     * @return 获取保存目录
//     */
//    public String getSavePath() {
//        return this.getRootDir() + JPDateUtils.toString(new Date(), "yyyy-MM-dd") + "/";
//    }
//
//    public Downloadable.TaskStateDto check(String key) throws Exception {
//
//        Boolean has = _hasProcessingTask(key);
//        if (!has) {
//            /*没有任务在跑，要么重来没跑过，要么跑完了， 直接返回完成，并把url返到前端*/
//            String url = _getDownloadUrl(key);
//            if (JPStringUtils.isNotBlank(url)) {
//                return new Downloadable.TaskStateDto(Downloadable.State.finish, key, url);
//            } else {
//                return new Downloadable.TaskStateDto(Downloadable.State.nothing, key, null);
//            }
//        }
//
//        return new Downloadable.TaskStateDto(Downloadable.State.running, key, null);
//    }
//
//    public Downloadable.TaskStateDto generate(
//            Map<String, Object> params,
//            final List<DownloaderTitles> titles,
//            final String type,
//            final String userId,
//            final Object... extraData) throws Exception {
//
//        /*if (titles == null) {
//            throw new CustomException("未设置下载表头");
//        }*/
//
//        /*生成唯一计算任务key*/
//        final String key = _generateKey(type, userId, params);
//
//        /*取此任务对应的进行状态*/
//        Boolean has = _hasProcessingTask(key);
//        if (!has) {
//            /*不存在runing中的任务， 开始跑生成数据任务*/
//            Downloadable downloadable = downloaderMap.get(type);
//            AssertUtils.notNull(downloadable, "未知下载类型 ：【" + type + "】");
//
//            /*获取下载文件名*/
//            final String filePath = getSavePath() + _generateUniqueFileName(downloadable.getFileName());
//
//            Map<String, Object> cloneParams = new HashMap<>();
//            if (params != null) {
//                for (String pKey : params.keySet()) {
//                    if (!JPStringUtils.equals(DOWNLOAD_TITLES, pKey)) {
//                        cloneParams.put(pKey, params.get(pKey));
//                    }
//                }
//            }
//
//            _runTask(key, downloadable, cloneParams, titles,
//                    filePath,
//                    new Callback() {
//                        @Override
//                        public void doCmd(Object... o) {
//                            if ((Boolean) o[0]) {
//
//                            }
//                        }
//                    },
//                    extraData);
//        }
//
//        return new Downloadable.TaskStateDto(Downloadable.State.running, key, null);
//    }
//
//    public static void main(String[] args) {
//        System.out.println(_generateUniqueFileName("lkasflk.xls"));
//        System.out.println(_generateUniqueFileName("纠结啊师傅.xls"));
//        System.out.println(_generateUniqueFileName("啊设立法律身份xls"));
//        System.out.println(_generateUniqueFileName("据啊司法所xls.xls.xls"));
//        System.out.println(_generateUniqueFileName(".xls"));
//        System.out.println(_generateUniqueFileName(".xls.xls"));
//        System.out.println(_generateUniqueFileName(".xls.xl"));
//        System.out.println(_generateUniqueFileName(".xlsxls"));
//    }
//
//    private static String _generateUniqueFileName(String fileName) {
//        if (JPStringUtils.isBlank(fileName)) {
//            return UUID.randomUUID().toString() + ".xls";
////            throw new CustomException("文件名为空");
//        } else {
//            String suffix = JPStringUtils.getFileSuffix(fileName);
////            System.out.println("--------------" + suffix);
//            if (JPStringUtils.equals(suffix, fileName)) {
//                fileName = fileName + "-" + UUID.randomUUID().toString() + ".xls";
//            } else {
//                fileName = fileName.substring(0, Math.max(fileName.length() - suffix.length() - 1, 0))
//                        + "-" + UUID.randomUUID().toString() + "." + suffix;
//            }
//        }
//        return fileName;
//    }
//
//    public Downloadable.TaskStateDto generate(Map<String, Object> params, final String type, final String userId, final Object... extraData) throws Exception {
//        return generate(params,
//                JsonMapper.buildNonNullMapper().fromJsonToList(String.valueOf(params.get(DOWNLOAD_TITLES)), DownloaderTitles.class),
//                type,
//                userId,
//                extraData);
//    }
//
//    public Downloadable.TaskStateDto generate(HttpServletRequest request, final String type, final String userId, final Object... extraData) throws Exception {
//        return generate(ServletUtils.getParametersStartingWith(request, null), type, userId, extraData);
//    }
//
//    private void _runTask(final String key,
//                          final Downloadable downloader,
//                          final Map<String, Object> params,
//                          final List<DownloaderTitles> titles,
//                          final String savePath,
//                          final Callback callback,
//                          final Object... extraData) {
//
//        try {
//            /*锁定最多10分钟， 也就是说一个任务十分钟都没跑完是有问题 或者需要优化的*/
//            boolean success = redisTool.lock(key, Downloadable.State.running.name(), 600L);
//            if (!success) {
//                this.logger.info("key:[" + key + "]发生并发！");
//                return;
//            }
//        } catch (Exception e) {
//            this.logger.error("key:[" + key + "]调用redis失败！", e);
//            return;
//        }
//
//        /*删除已经存在的 url */
//        _deleteDownloadUrl(key);
//
//        //启动线程开始生成数据
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        executorService.submit(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    DownloaderUtils.generateFile(downloader, params, titles, savePath, extraData);
//
//                    _setDownloadUrl(key, savePath);
//
//                    if (callback != null) {
//                        callback.doCmd(Boolean.TRUE);
//                    }
//
//                } catch (Exception e) {
//
//                    logger.error("key:[" + key + "]跑任务失败了，参数：" + params, e);
//                    if (callback != null) {
//                        callback.doCmd(false);
//                    }
//
//                } finally {
//                    try {
//                        redisTool.unlock(key);
//                    } catch (Exception e) {
//                        logger.error("key:[" + key + "] 解锁删除失败了", e);
//                    }
//                }
//            }
//        });
//
//        executorService.shutdown();
//    }
//
//    private static String DOWNLOAD_URL_PREFIX = "download_url_prefix_";
//
//    private void _setDownloadUrl(String key, String fileName) {
//        redisTool.setValue(DOWNLOAD_URL_PREFIX + key,
//                this.storageType.getDownloadUrl(this, fileName),
//                1L,
//                TimeUnit.DAYS);
//    }
//
//    private String _getDownloadUrl(String key) throws Exception {
//        return redisTool.getValue(DOWNLOAD_URL_PREFIX + key);
//    }
//
//    private void _deleteDownloadUrl(String key) {
//        redisTool.delValue(DOWNLOAD_URL_PREFIX + key);
//    }
//
//    private String _getTaskState(String key) throws Exception {
//        return redisTool.getValue(key);
//    }
//
//    /**
//     * @param key
//     * @return
//     * @throws Exception
//     */
//    private boolean _hasProcessingTask(String key) throws Exception {
//        String state = _getTaskState(key);
//        return JPStringUtils.isNotBlank(state);
//        //return Downloadable.State.running.name().equals(state);
//    }
//
//    private String _generateKey(String type, String userId, Map<String, Object> params) {
//        List<String> paramKeyList = Lists.newArrayList();
//        if (params != null) {
//            for (String key : params.keySet()) {
//                paramKeyList.add(key + String.valueOf(params.get(key)));
//            }
//        }
//        Collections.sort(paramKeyList);
//        StringBuilder source = new StringBuilder();
//        /*如果没有参数， 则需要置一个默认的数保证不重复*/
//        if (paramKeyList.size() > 0) {
//            for (String key : paramKeyList) {
//                source.append(key);
//            }
//        }/* else {
//            source.append(UUID.randomUUID().toString());
//        }*/
//
//        return this.appName + "_" + type + "_" + new Md5Hash(userId + "_" + source.toString()).toHex();
//    }
//
//    public void setDownloaderMap(Map<String, Downloadable> downloaderMap) {
//        this.downloaderMap = downloaderMap;
//    }
//
//    public void setAppName(String appName) {
//        this.appName = appName;
//    }
//
//    public void setRedisTool(RedisTool redisTool) {
//        this.redisTool = redisTool;
//    }
//
//    public void setStorageType(String storageType) {
////        try {
////            this.storageType = StorageType.valueOf(storageType);
////        } catch (Exception e) {
////            this.storageType = StorageType.nfs;
////        }
//    }
//
//    public String getAppName() {
//        return appName;
//    }
//
//    public String getDownloadDomain() {
//        return downloadDomain;
//    }
//
//    public void setDownloadDomain(String downloadDomain) {
//        this.downloadDomain = downloadDomain;
//    }
//}
