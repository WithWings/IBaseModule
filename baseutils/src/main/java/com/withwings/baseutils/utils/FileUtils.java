package com.withwings.baseutils.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;

/**
 * 文件工具类
 * 创建：WithWings 时间 2018/2/9
 * Email:wangtong1175@sina.com
 */
@SuppressWarnings({"unused", "WeakerAccess", "ConstantConditions", "SameParameterValue", "SuspiciousNameCombination", "UnusedReturnValue"})
public class FileUtils {

    private static final String ROOT_FILE_NAME = "withwings";

    public final static String FILE_EXTENSION_SEPARATOR = ".";

    /** URI类型：file */
    public static final String URI_TYPE_FILE = "file";

    /**
     * APP保存根目录
     */
    public static final int FILE_TYPE_ROOT = 0;

    /**
     * 图片文件
     */
    public static final int FILE_TYPE_IMAGE = 1;

    /**
     * 视频文件
     */
    public static final int FILE_TYPE_VIDEO = 2;

    /**
     * APK文件
     */
    public static final int FILE_TYPE_APK = 3;

    private FileUtils() {
        throw new AssertionError();
    }

    /**
     * 获取项目根存储目录: 因为SD卡文件夹乱用问题，请根据实际情况调用  getOpenFile  getOpenCacheFile  getInsideFile  getInsideCacheFile  getSDCardPath
     */
    public static String getAppUseFile(String path) {
        return getAppUseFile(path, FILE_TYPE_ROOT);
    }

    /**
     * 获取指定类型的存储目录
     * @param fileType 目录类型
     * @return 目录地址
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static String getAppUseFile(String path, int fileType) {
        String rootPath = path + File.separator;
        switch (fileType) {
            case FILE_TYPE_ROOT:
                //noinspection DuplicateBranchesInSwitch
                return rootPath;
            case FILE_TYPE_IMAGE:
                String imagePath = rootPath + "image" + File.separator;
                File imageFile = new File(imagePath);
                if (!imageFile.exists()) {
                    imageFile.mkdirs();
                }
                return imagePath;
            case FILE_TYPE_VIDEO:
                String videoPath = rootPath + "video" + File.separator;
                File videoFile = new File(videoPath);
                if (!videoFile.exists()) {
                    videoFile.mkdirs();
                }
                return videoPath;
            case FILE_TYPE_APK:
                String apkPath = rootPath + "apk" + File.separator;
                File apkFile = new File(apkPath);
                if (!apkFile.exists()) {
                    apkFile.mkdirs();
                }
                return apkPath;
            default:
                return rootPath;
        }
    }

    /**
     * 获取对外开放的文件夹
     * /sdcard/Android/data/包名/files
     * @param context 上下文 getExternalFilesDir 预设了一些文件夹类型  如：Environment.DIRECTORY_PICTURES
     * @return 路径
     */
    public static String getOpenFile(Context context) {
        return context.getExternalFilesDir("").getAbsolutePath();
    }

    /**
     * 获取对外开放的缓存文件夹
     * /sdcard/Android/data/包名/cache
     * @param context 上下文
     * @return 路径
     */
    public static String getOpenCacheFile(Context context) {
        return context.getExternalCacheDir().getAbsolutePath();
    }

    /**
     * 获取内部的文件夹
     * /data/data/包名/files
     * @param context 上下文
     * @return 路径
     */
    public static String getInsideFile(Context context) {
        return context.getFilesDir().getAbsolutePath();
    }

    /**
     * 获取内部的缓存文件夹
     * /data/data/包名/cache
     * @param context 上下文
     * @return 路径
     */
    public static String getInsideCacheFile(Context context) {
        return context.getCacheDir().getAbsolutePath();
    }

    /**
     * 获得SD 卡路径，如果没有则获取内存卡路径
     * /storage/emulated/0 + / + ROOT_FILE_NAME
     * @return 路径
     */
    public static String getSDCardPath() {
        return getSDCardPath(false) + File.separator + ROOT_FILE_NAME;
    }

    /**
     * 获取sd卡路径
     * 请注意：如果你使用外置sd卡，那么你只能有读取的权限，想要写文件，必须单独申请权限
     * 我在下面一个方法封装了权限请求的方式，但是因为要处理权限回调，这里只做跳转到申请界面，回调处理请自行操作
     * @param isMust 如果必须是外置sd卡
     * @return 外置为空
     */
    public static String getSDCardPath(boolean isMust){
        String SDPath = System.getenv("SECONDARY_STORAGE");
        if (TextUtils.isEmpty(SDPath) && !isMust) {
            SDPath = getPhonePath();
        }
        return SDPath;
    }

    public static void requestSDCardPermission(Activity activity, int requestCode) {
        activity.startActivityForResult(new Intent("android.intent.action.OPEN_DOCUMENT_TREE"),requestCode);
    }

    /**
     * 返回值
     * @param resultCode 返回的请求码 判断requestCode 符合调用该方法处理
     * @return 获得的sd uri，如果为空则代表未获得
     */
    public static DocumentFile checkSDCardPermission(Context context, int resultCode, Intent data, String filePath){
        if (resultCode == RESULT_OK) {
            Uri treeUri = data.getData();
            if(treeUri == null){
                return null;
            }
            if (!":".equals(treeUri.getPath().substring(treeUri.getPath().length() - 1)) || treeUri.getPath().contains("primary")) {
                // 选择的内部存储卡
                return null;
            } else {
                return getDocumentFilePath(context, Uri.parse(treeUri.toString()), filePath, true);
            }
        }
        return null;
    }

    /**
     * 获得可操作的文件
     * @param context 上下文
     * @param path 文件路径 file.getAbsolutePath()
     * @param createDirectories 新建还是删除文件操作
     * @return File对象
     */
    public static DocumentFile getDocumentFilePath(Context context, Uri uri, String path, boolean createDirectories) {
        DocumentFile document = DocumentFile.fromTreeUri(context, uri);

        String[] parts = path.split("/");
        for (int i = 3; i < parts.length; i++) {
            DocumentFile nextDocument = document.findFile(parts[i]);
            if (nextDocument == null) {
                if (i < parts.length - 1) {
                    if (createDirectories) {
                        nextDocument = document.createDirectory(parts[i]);
                    } else {
                        return null;
                    }
                } else {
                    nextDocument = document.createFile("image", parts[i]);
                }
            }
            document = nextDocument;
        }
        return document;
    }

    /**
     * 获得写入流
     * @param context 上下文
     * @param documentFile DocumentFile
     * @return 写入流
     */
    public static OutputStream documentFileToOutputStream(Context context, DocumentFile documentFile){
        documentFile.canWrite();
        try {
            return context.getContentResolver().openOutputStream(documentFile.getUri());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得读取流
     * @param context 上下文
     * @param documentFile DocumentFile
     * @return 读取流
     */
    public static InputStream documentFileToInputStream(Context context, DocumentFile documentFile){
        documentFile.canWrite();
        try {
            return context.getContentResolver().openInputStream(documentFile.getUri());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获得路径 “/storage/emulated/legacy” 之所以不是 “/storage/emulated/0” 是因为 0 是新的格式，但是存储位置还是软链接到 legacy
     * @return 路径
     */
    public static String getPhonePath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 创建文件夹
     * @param file 文件对象
     * @return 是否是文件夹/如果没有会自动创建
     */
    public static boolean makeDir(File file) {
        return file.exists() && file.isDirectory() || file.mkdirs();
    }

    /**
     * 创建文件
     * @param file 创建文件
     * @return 创建结果
     */
    public static boolean makeFile(File file){
        if(file.exists() && file.isFile()) {
            return true;
        } else {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
    }


    /**
     * read file
     *
     * @param filePath    路径
     * @param charsetName The name of a supported {@link
     * java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator
     * BufferedReader
     */
    public static StringBuilder readFile(String filePath, String charsetName) {

        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder();
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(
                    new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            close(reader);
        }
    }


    /**
     * 写入文件
     * @param filePath 文件路径
     * @param content 上下文
     * @param append 是否追加
     * @return 写入是否完成
     */
    public static boolean writeFile(String filePath, String content, boolean append) {

        if (TextUtils.isEmpty(content)) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            fileWriter.write(content);
            fileWriter.close();
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            close(fileWriter);
        }
    }


    /**
     * write file
     *
     * @param filePath  路径
     * @param contentList  集合
     * @param append is append, if true, write to the end of file, else clear
     * content of file and write into it
     * @return return false if contentList is empty, true otherwise
     * @throws RuntimeException if an error occurs while operator FileWriter
     */
    public static boolean writeFile(String filePath, List<String> contentList, boolean append) {

        if (contentList.size() == 0) {
            return false;
        }

        FileWriter fileWriter = null;
        try {
            makeDirs(filePath);
            fileWriter = new FileWriter(filePath, append);
            int i = 0;
            for (String line : contentList) {
                if (i++ > 0) {
                    fileWriter.write("\r\n");
                }
                fileWriter.write(line);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            close(fileWriter);
        }
    }


    /**
     * write file, the string will be written to the begin of the file
     * @param filePath    地址
     * @param content  上下文
     * @return  是否写入成功
     */
    public static boolean writeFile(String filePath, String content) {

        return writeFile(filePath, content, false);
    }


    /**
     * write file, the string list will be written to the begin of the file
     * @param filePath    地址
     * @param contentList    集合
     * @return  是否写入成功
     */
    public static boolean writeFile(String filePath, List<String> contentList) {
        return writeFile(filePath, contentList, false);

    }


    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param filePath   路径
     * @param stream  输入流
     * @return 返回是否写入成功
     */
    public static boolean writeFile(String filePath, InputStream stream) {
        return writeFile(filePath, stream, false);

    }


    /**
     * write file
     *
     * @param filePath 路径
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the
     * end
     * of the file rather than the beginning
     * @return return true
     * FileOutputStream
     */
    public static boolean writeFile(String filePath, InputStream stream, boolean append) {

        return writeFile(filePath != null ? new File(filePath) : null, stream,
                append);
    }


    /**
     * write file, the bytes will be written to the begin of the file
     *
     * @param file    文件对象
     * @param stream 输入流
     * @return 返回是否写入成功
     */
    public static boolean writeFile(File file, InputStream stream) {
        return writeFile(file, stream, false);

    }


    /**
     * write file
     *
     * @param file the file to be opened for writing.
     * @param stream the input stream
     * @param append if <code>true</code>, then bytes will be written to the
     * end
     * of the file rather than the beginning
     * @return return true
     * @throws RuntimeException if an error occurs while operator
     * FileOutputStream
     */
    public static boolean writeFile(File file, InputStream stream, boolean append) {
        OutputStream o = null;
        try {
            makeDirs(file.getAbsolutePath());
            o = new FileOutputStream(file, append);
            byte[] data = new byte[1024];
            int length;
            while ((length = stream.read(data)) != -1) {
                o.write(data, 0, length);
            }
            o.flush();
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            close(o);
            close(stream);
        }
    }


    /**
     * move file
     * @param sourceFilePath    资源路径
     * @param destFilePath  删除的路径
     */
    public static void moveFile(String sourceFilePath, String destFilePath) {

        if (TextUtils.isEmpty(sourceFilePath) ||
                TextUtils.isEmpty(destFilePath)) {
            throw new RuntimeException(
                    "Both sourceFilePath and destFilePath cannot be null.");
        }
        moveFile(new File(sourceFilePath), new File(destFilePath));
    }


    /**
     * move file
     * @param srcFile    文件对象
     * @param destFile  对象
     */
    public static void moveFile(File srcFile, File destFile) {

        boolean rename = srcFile.renameTo(destFile);
        if (!rename) {
            copyFile(srcFile.getAbsolutePath(), destFile.getAbsolutePath());
            deleteFile(srcFile.getAbsolutePath());
        }
    }


    /**
     * copy file
     *
     * @param sourceFilePath    资源路径
     * @param destFilePath  删除的文件
     * @throws RuntimeException if an error occurs while operator
     * FileOutputStream
     * @return  返回是否成功
     */
    public static boolean copyFile(String sourceFilePath, String destFilePath) {

        InputStream inputStream;
        try {
            inputStream = new FileInputStream(sourceFilePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("FileNotFoundException occurred. ", e);
        }
        return writeFile(destFilePath, inputStream);
    }


    /**
     * read file to string list, a element of list is a line
     *
     * @param filePath    路径
     * @param charsetName The name of a supported {@link
     * java.nio.charset.Charset </code>charset<code>}
     * @return if file not exist, return null, else return content of file
     * @throws RuntimeException if an error occurs while operator
     * BufferedReader
     */
    public static List<String> readFileToList(String filePath, String charsetName) {

        File file = new File(filePath);
        List<String> fileContent = new ArrayList<>();
        if (!file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(
                    new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line;
            while ((line = reader.readLine()) != null) {
                fileContent.add(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            close(reader);
        }
    }


    /**
     *
     * @param filePath  文件的路径
     * @return 返回文件的信息
     */
    public static String getFileNameWithoutExtension(String filePath) {


        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (filePosi == -1) {
            return (extenPosi == -1
                    ? filePath
                    : filePath.substring(0, extenPosi));
        }
        if (extenPosi == -1) {
            return filePath.substring(filePosi + 1);
        }
        return (filePosi < extenPosi ? filePath.substring(filePosi + 1,
                extenPosi)           : filePath.substring(filePosi + 1));
    }


    /**
     * get file name from path, include suffix
     *
     * <pre>
     *      getFileName(null)               =   null
     *      getFileName("")                 =   ""
     *      getFileName("   ")              =   "   "
     *      getFileName("a.mp3")            =   "a.mp3"
     *      getFileName("a.b.rmvb")         =   "a.b.rmvb"
     *      getFileName("abc")              =   "abc"
     *      getFileName("c:\\")              =   ""
     *      getFileName("c:\\a")             =   "a"
     *      getFileName("c:\\a.b")           =   "a.b"
     *      getFileName("c:a.txt\\a")        =   "a"
     *      getFileName("/home/admin")      =   "admin"
     *      getFileName("/home/admin/a.txt/b.mp3")  =   "b.mp3"
     * </pre>
     *
     * @param filePath    路径
     * @return file name from path, include suffix
     */
    public static String getFileName(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? filePath : filePath.substring(filePosi + 1);
    }


    /**
     * get folder name from path
     *
     * <pre>
     *      getFolderName(null)               =   null
     *      getFolderName("")                 =   ""
     *      getFolderName("   ")              =   ""
     *      getFolderName("a.mp3")            =   ""
     *      getFolderName("a.b.rmvb")         =   ""
     *      getFolderName("abc")              =   ""
     *      getFolderName("c:\\")              =   "c:"
     *      getFolderName("c:\\a")             =   "c:"
     *      getFolderName("c:\\a.b")           =   "c:"
     *      getFolderName("c:a.txt\\a")        =   "c:a.txt"
     *      getFolderName("c:a\\b\\c\\d.txt")    =   "c:a\\b\\c"
     *      getFolderName("/home/admin")      =   "/home"
     *      getFolderName("/home/admin/a.txt/b.mp3")  =   "/home/admin/a.txt"
     * </pre>
     * @param filePath    路径
     * @return  file name from path, include suffix
     */
    public static String getFolderName(String filePath) {


        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int filePosi = filePath.lastIndexOf(File.separator);
        return (filePosi == -1) ? "" : filePath.substring(0, filePosi);
    }


    /**
     * get suffix of file from path
     *
     * <pre>
     *      getFileExtension(null)               =   ""
     *      getFileExtension("")                 =   ""
     *      getFileExtension("   ")              =   "   "
     *      getFileExtension("a.mp3")            =   "mp3"
     *      getFileExtension("a.b.rmvb")         =   "rmvb"
     *      getFileExtension("abc")              =   ""
     *      getFileExtension("c:\\")              =   ""
     *      getFileExtension("c:\\a")             =   ""
     *      getFileExtension("c:\\a.b")           =   "b"
     *      getFileExtension("c:a.txt\\a")        =   ""
     *      getFileExtension("/home/admin")      =   ""
     *      getFileExtension("/home/admin/a.txt/b")  =   ""
     *      getFileExtension("/home/admin/a.txt/b.mp3")  =   "mp3"
     * </pre>
     * @param filePath    路径
     * @return  信息
     */
    public static String getFileExtension(String filePath) {

        if (TextUtils.isEmpty(filePath)) {
            return filePath;
        }

        int extenPosi = filePath.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        int filePosi = filePath.lastIndexOf(File.separator);
        if (extenPosi == -1) {
            return "";
        }
        return (filePosi >= extenPosi) ? "" : filePath.substring(extenPosi + 1);
    }


    /**
     *
     * @param filePath 路径
     * @return 是否创建成功
     */
    public static boolean makeDirs(String filePath) {

        String folderName = getFolderName(filePath);
        if (TextUtils.isEmpty(folderName)) {
            return false;
        }

        File folder = new File(folderName);
        return (folder.exists() && folder.isDirectory()) || folder.mkdirs();
    }


    /**
     *
     * @param filePath 路径
     * @return  是否创建成功
     */
    public static boolean makeFolders(String filePath) {
        return makeDirs(filePath);

    }


    /**
     *
     * @param filePath 路径
     * @return  是否存在这个文件
     */
    public static boolean isFileExist(String filePath) {
        if (TextUtils.isEmpty(filePath)) {
            return false;
        }

        File file = new File(filePath);
        return (file.exists() && file.isFile());

    }


    /**
     *
     * @param directoryPath 路径
     * @return  是否有文件夹
     */
    public static boolean isFolderExist(String directoryPath) {

        if (TextUtils.isEmpty(directoryPath)) {
            return false;
        }

        File dire = new File(directoryPath);
        return (dire.exists() && dire.isDirectory());
    }


    /**
     *
     * @param path  路径
     * @return  是否删除成功
     */
    public static boolean deleteFile(String path) {

        if (TextUtils.isEmpty(path)) {
            return true;
        }

        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        if (file.isFile()) {
            return file.delete();
        }
        if (!file.isDirectory()) {
            return false;
        }
        for (File f : file.listFiles()) {
            if (f.isFile()) {
                boolean delete = f.delete();
            }
            else if (f.isDirectory()) {
                deleteFile(f.getAbsolutePath());
            }
        }
        return file.delete();
    }


    /**
     *
     * @param path  路径
     * @return  返回文件大小
     */
    public static long getFileSize(String path) {

        if (TextUtils.isEmpty(path)) {
            return -1;
        }

        File file = new File(path);
        return (file.exists() && file.isFile() ? file.length() : -1);
    }


    /**
     * 保存多媒体数据为文件.
     *
     * @param data 多媒体数据
     * @param fileName 保存文件名
     * @return 保存成功或失败
     */
    public static boolean save2File(InputStream data, String fileName) {
        File file = new File(fileName);
        FileOutputStream fos;
        try {
            // 文件或目录不存在时,创建目录和文件.
            if (!file.exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
                boolean newFile = file.createNewFile();
            }

            // 写入数据
            fos = new FileOutputStream(file);
            byte[] b = new byte[1024];
            int len;
            while ((len = data.read(b)) > -1) {
                fos.write(b, 0, len);
            }
            fos.close();

            return true;
        } catch (IOException ex) {

            return false;
        }
    }


    /**
     * 读取文件的字节数组.
     *
     * @param file 文件
     * @return 字节数组
     */
    public static byte[] readFile4Bytes(File file) {

        // 如果文件不存在,返回空
        if (!file.exists()) {
            return null;
        }
        FileInputStream fis = null;
        try {
            // 读取文件内容.
            fis = new FileInputStream(file);
            byte[] arrData = new byte[(int) file.length()];
            int read = fis.read(arrData);
            // 返回
            return arrData;
        } catch (IOException e) {

            return null;
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 读取文本文件内容，以行的形式读取
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName) {
        try {
            return readFileContent(filePathAndName, null, null, 1024);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  null;
    }


    /**
     * 读取文本文件内容，以行的形式读取
     *
     * @param filePathAndName 带有完整绝对路径的文件名
     * @param encoding 文本文件打开的编码方式 例如 GBK,UTF-8
     * @param sep 分隔符 例如：#，默认为\n;
     * @param bufLen 设置缓冲区大小
     * @return String 返回文本文件的内容
     */
    public static String readFileContent(String filePathAndName, String encoding, String sep, int bufLen)
    {
        if (filePathAndName == null || filePathAndName.equals("")) {
            return "";
        }
        if (sep == null || sep.equals("")) {
            sep = "\n";
        }
        if (!new File(filePathAndName).exists()) {
            return "";
        }
        StringBuilder str = new StringBuilder();
        FileInputStream fs = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            fs = new FileInputStream(filePathAndName);
            if (encoding == null || encoding.trim().equals("")) {
                isr = new InputStreamReader(fs);
            }
            else {
                isr = new InputStreamReader(fs, encoding.trim());
            }
            br = new BufferedReader(isr, bufLen);

            String data;
            while ((data = br.readLine()) != null) {
                str.append(data).append(sep);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
                if (isr != null) isr.close();
                if (fs != null) fs.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str.toString();
    }


    /**
     * 把Assets里的文件拷贝到sd卡上
     *
     * @param assetManager AssetManager
     * @param fileName Asset文件名
     * @param destinationPath 完整目标路径
     * @return 拷贝成功
     */
    public static boolean copyAssetToSDCard(AssetManager assetManager, String fileName, String destinationPath) {

        try {
            InputStream is = assetManager.open(fileName);
            FileOutputStream os = new FileOutputStream(destinationPath);

            if (is != null) {
                byte[] data = new byte[1024];
                int len;
                while ((len = is.read(data)) > 0) {
                    os.write(data, 0, len);
                }

                os.close();
                is.close();
            }
        } catch (IOException e) {
            return false;
        }
        return true;
    }


    /**
     * 调用系统方式打开文件.
     *
     * @param context    上下文
     * @param file 文件
     */
    public static void openFile(Context context, File file) {

        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), MimeTypeMap.getSingleton()
                    .getMimeTypeFromExtension(
                            MimeTypeMap
                                    .getFileExtensionFromUrl(
                                            file.getAbsolutePath())));
            context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "打开失败.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 根据文件路径，检查文件是否不大于指定大小
     *
     * @param filepath 文件路径
     * @param maxSize    最大
     * @return  是否
     */
    public static boolean checkFileSize(String filepath, int maxSize) {

        File file = new File(filepath);
        return !(!file.exists() || file.isDirectory()) && file.length() <= maxSize * 1024;
    }


    /**
     *
     * @param context  上下文
     * @param file  文件对象
     */
    public static void openMedia(Context context, File file) {

        if (file.getName().endsWith(".png") ||
                file.getName().endsWith(".jpg") ||
                file.getName().endsWith(".jpeg")) {
            viewPhoto(context, file);
        }
        else {
            openFile(context, file);
        }
    }


    /**
     * 打开多媒体文件.
     *
     * @param context    上下文
     * @param file 多媒体文件
     */
    public static void viewPhoto(Context context, String file) {

        viewPhoto(context, new File(file));
    }


    /**
     * 打开照片
     * @param context    上下文
     * @param file  文件对象
     */
    public static void viewPhoto(Context context, File file) {

        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "image/*");
            context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "打开失败.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 将字符串以UTF-8编码保存到文件中
     * @param str    保存的字符串
     * @param fileName 文件的名字
     * @return  是否保存成功
     */
    public static boolean saveStrToFile(String str, String fileName) {

        return saveStrToFile(str, fileName, "UTF-8");
    }


    /**
     * 将字符串以charsetName编码保存到文件中
     * @param str    保存的字符串
     * @param fileName  文件的名字
     * @param charsetName  字符串编码
     * @return  是否保存成功
     */
    public static boolean saveStrToFile(String str, String fileName, String charsetName) {

        if (str == null || "".equals(str)) {
            return false;
        }

        FileOutputStream stream = null;
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                boolean mkdirs = file.getParentFile().mkdirs();
            }

            byte[] b;
            if (charsetName != null && !"".equals(charsetName)) {
                b = str.getBytes(charsetName);
            }
            else {
                b = str.getBytes();
            }

            stream = new FileOutputStream(file);
            stream.write(b, 0, b.length);
            stream.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 将content://形式的uri转为实际文件路径
     * @param context    上下文
     * @param uri  地址
     * @return  uri转为实际文件路径
     */
    public static String uriToPath(Context context, Uri uri) {

        Cursor cursor = null;
        try {
            if (uri.getScheme().equalsIgnoreCase(URI_TYPE_FILE)) {
                return uri.getPath();
            }
            cursor = context.getContentResolver()
                    .query(uri, null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                return cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA)); //图片文件路径
            }
        } catch (Exception e) {
            if (null != cursor) {
                cursor.close();
            }
            return null;
        }
        return null;
    }


    /**
     * 打开多媒体文件.
     *
     * @param context    上下文
     * @param file 多媒体文件
     */
    public static void playSound(Context context, String file) {

        playSound(context, new File(file));
    }


    /**
     * 打开多媒体文件.
     *
     * @param context    上下文
     * @param file 多媒体文件
     */
    public static void playSound(Context context, File file) {

        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            //			intent.setClassName("com.android.music", "com.android.music.MediaPlaybackActivity");
            intent.setDataAndType(Uri.fromFile(file), "audio/*");
            context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "打开失败.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 打开视频文件.
     *
     * @param context    上下文
     * @param file 视频文件
     */
    public static void playVideo(Context context, String file) {

        playVideo(context, new File(file));
    }


    /**
     * 打开视频文件.
     * @param context    上下文
     * @param file 视频文件
     */
    public static void playVideo(Context context, File file) {
        try {
            // 调用系统程序打开文件.
            Intent intent = new Intent(Intent.ACTION_VIEW);
            //			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setDataAndType(Uri.fromFile(file), "video/*");
            context.startActivity(intent);
        } catch (Exception ex) {
            Toast.makeText(context, "打开失败.", Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 文件重命名
     *
     * @param oldPath    旧的文件名字
     * @param newPath    新的文件名字
     */
    public static void renameFile(String oldPath, String newPath) {

        try {
            if (!TextUtils.isEmpty(oldPath) && !TextUtils.isEmpty(newPath)
                    && !oldPath.equals(newPath)) {
                File fileOld = new File(oldPath);
                File fileNew = new File(newPath);
                boolean b = fileOld.renameTo(fileNew);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Close closable object and wrap {@link IOException} with {@link
     * RuntimeException}
     *
     * @param closeable closeable object
     */
    public static void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                throw new RuntimeException("IOException occurred. ", e);
            }
        }
    }

    //把文件转换成字节数组
    public static byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }

    /**
     * 该方法需要配置 FileProvider
     * @param context 上下文
     * @param file 文件对象
     * @return 格式化后的Uri
     */
    public static Uri getUriForFile(Context context, File file) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return FileProvider.getUriForFile(context, context.getPackageName() + ".webFileProvider", file);//通过FileProvider创建一个content类型的Uri
        } else {
            return Uri.fromFile(file);
        }
    }
}
