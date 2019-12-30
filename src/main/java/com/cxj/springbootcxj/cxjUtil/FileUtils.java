package com.cxj.springbootcxj.cxjUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;

import sun.misc.BASE64Encoder;

/**
 * @author ChuXianJie
 * @date 2019年1月1日
 */
public class FileUtils {

	private static FileUtils fileUtils = null;

	public static FileUtils getInstance() {
		if (fileUtils == null) {
			fileUtils = new FileUtils();
		}
		return fileUtils;
	}

	Boolean flag;
	File file;

	/**
	 * 下载文件时，针对不同浏览器，进行附件名的编码
	 * 
	 * @param filename
	 *            下载文件名
	 * @param agent
	 *            客户端浏览器
	 * @param coding
	 *            编码格式
	 * @return 编码后的下载附件名
	 * @throws IOException
	 */
	@SuppressWarnings("restriction")
	public static String encodeDownloadFilename(String filename, String agent, String coding) throws IOException {
		if (agent.contains("Firefox")) { // 火狐浏览器
			filename = "=?" + coding + "?B?" + new BASE64Encoder().encode(filename.getBytes(coding)) + "?=";
			filename = filename.replaceAll("\r\n", "");
		} else { // IE及其他浏览器
			filename = URLEncoder.encode(filename, coding);
			filename = filename.replace("+", " ");
		}
		return filename;
	}

	/**
	 * 判断多级路径是否存在，不存在就创建
	 * 
	 * @param filePath
	 *            支持带文件名的Path：如：D:\news\2014\12\abc.text，和不带文件名的Path：如：D:\news\2014\12
	 */
	public static void isExistDir(String filePath) {
		String[] paths = { "" };
		// 切割路径
		try {
			String tempPath = new File(filePath).getCanonicalPath();// File对象转换为标准路径并进行切割，有两种windows和linux
			paths = tempPath.split("\\\\");// windows
			if (paths.length == 1) {
				paths = tempPath.split("/");
			} // linux
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 判断是否有后缀
		boolean hasType = false;
		if (paths.length > 0) {
			String tempPath = paths[paths.length - 1];
			if (tempPath.length() > 0) {
				if (tempPath.indexOf(".") > 0) {
					hasType = true;
				}
			}
		}
		// 创建文件夹
		String dir = paths[0];
		for (int i = 0; i < paths.length - (hasType ? 2 : 1); i++) {
			// 注意此处循环的长度，有后缀的就是文件路径，没有则文件夹路径
			try {
				dir = dir + "/" + paths[i + 1];// 采用linux下的标准写法进行拼接，由于windows可以识别这样的路径，所以这里采用警容的写法
				File dirFile = new File(dir);
				if (!dirFile.exists()) {
					dirFile.mkdir();
				}
			} catch (Exception e) {
				System.err.println("文件夹创建发生异常");
				e.printStackTrace();
			}
		}
	}

	/**
	 * 创建本地应用服务的文件夹
	 * 
	 * @param localPath
	 */
	public static boolean createLocalPath(String localPath) {
		boolean isDir = false;
		File file = new File(localPath);
		if (!file.exists()) {
			isDir = file.mkdirs();
			System.out.println("应用服务目录[" + localPath + "]创建成功");
		}
		return isDir;
	}

	/**
	 * 创建本地应用服务的多层文件夹
	 * 
	 * @param rmtPath
	 *            目标文件目录
	 */
	public static void createPath(String rmtPath) {
		String[] rmtPathArr = rmtPath.split("\\\\");
		String filePath = "";
		for (int i = 0; i < rmtPathArr.length; i++) {
			if (i == 0) {
				filePath = filePath + rmtPathArr[i];
				continue;
			} else {
				filePath = filePath + "/" + rmtPathArr[i];
			}
			createLocalPath(filePath);
		}
	}

	/**
	 * 下载文件到本地
	 * 
	 * @param inputStream
	 *            输入流
	 * @param filePath
	 *            文件路径_不包含文件名
	 * @param fileName
	 *            文件名
	 * @return 文件路径
	 */
	public static String downloadToLocal(InputStream inputStream, String filePath, String fileName) {
		FileOutputStream out = null;
		try {
			createPath(filePath);
			File tmpFile = new File(filePath + File.separator + fileName);
			if (!tmpFile.exists()) {
				tmpFile.createNewFile();
			}
			// 创建一个文件输出流
			out = new FileOutputStream(tmpFile);
			// 创建一个缓冲区
			byte[] buffer = new byte[1024];
			// 判断输入流中的数据是否已经读完的标识
			int len = 0;
			// 循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
			while ((len = inputStream.read(buffer)) > 0) {
				out.write(buffer, 0, len);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					inputStream = null;
					e.printStackTrace();
				}
			}
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					out = null;
					e.printStackTrace();
				}
			}
		}
		return filePath + File.separator + fileName;
	}

	/**
	 * 删除本地文件
	 * 
	 * @path path 文件路径
	 */
	public Boolean deleteToLocal(String path) {
		flag = false;
		file = new File(path);
		// 判断目录或者文件是否存在
		if (!file.exists()) {
			return flag;// 不存在返回false
		} else {
			// 判断是否为文件
			if (file.isFile()) {
				return deleteFile(path);
			} else {
				return deleteDirectory(path);
			}
		}

	}

	/**
	 * 删除单个文件
	 * 
	 * @param sPath
	 *            被删除文件的文件名
	 * @return 单个文件删除成功返回true，否则返回false
	 */
	public boolean deleteFile(String sPath) {
		flag = false;
		file = new File(sPath);
		// 路径为文件且不为空则进行删除
		if (file.isFile() && file.exists()) {
			file.delete();
			flag = true;
		}
		return flag;
	}

	/**
	 * 删除目录（文件夹）以及目录下的文件
	 * 
	 * @param sPath
	 *            被删除目录的文件路径
	 * @return 目录删除成功返回true，否则返回false
	 */
	public boolean deleteDirectory(String sPath) {
		// 如果sPath不以文件分隔符结尾，自动添加文件分隔符
		if (!sPath.endsWith(File.separator)) {
			sPath = sPath + File.separator;
		}
		File dirFile = new File(sPath);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if (!dirFile.exists() || !dirFile.isDirectory()) {
			return false;
		}
		flag = true;
		// 删除文件夹下的所有文件(包括子目录)
		File[] files = dirFile.listFiles();
		if (files != null) {
			for (int i = 0; i < files.length; i++) {
				// 删除子文件
				if (files[i].isFile()) {
					flag = deleteFile(files[i].getAbsolutePath());
					if (!flag) {
						break;
					}
					// 删除子目录
				} else {
					flag = deleteDirectory(files[i].getAbsolutePath());
					if (!flag) {
						break;
					}
				}
			}
		}
		if (!flag) {
			return false;
		}
		// 删除当前目录
		return dirFile.delete();
	}
}
