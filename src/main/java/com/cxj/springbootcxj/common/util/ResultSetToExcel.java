package com.cxj.springbootcxj.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 执行sql语句，获取结果集，存入excel
 * 
 * @author Missy
 *
 */
public class ResultSetToExcel {

	/** 数据库配置文件 */
	private static String PROPERTIES_NAME = "mysql.properties";
	/** 生成的excel目录名称 */
	private static String EXCEL_DIR_NAME = "excel";
	/** 计数器 */
	private static int k = 1;

	/**
	 * 程序入口
	 * 
	 * @param args
	 */
//	public static void main(String[] args) {
	public static void resultSetToExcel(String propertiesName, String sqlFileUrl,  String ExcelDirName) {
		if (propertiesName != null) {
			PROPERTIES_NAME = propertiesName;
		}
		if (ExcelDirName != null) {
			EXCEL_DIR_NAME = ExcelDirName;
		}
		// 输入sql文件路径
		System.out.println("sql文件(或文件夹)地址---->" + sqlFileUrl);
		String sqlPath = sqlFileUrl;
		// 创建文件对象
		File sqlFile = new File(sqlPath);
		// 获取数据库配置
		Map<String, String> dbMap = getProperties(PROPERTIES_NAME);
		System.out.println("数据库配置如下...");
		for (Entry<String, String> entrySet : dbMap.entrySet()) {
			System.out.println(entrySet.getKey() + " = " + entrySet.getValue());
		}
		System.out.println();
		// 创建数据库连接属性
		Connection con = getCon(dbMap);
		PreparedStatement preStatement = null;
		ResultSet result = null;
		Map<String, Object> conMap = new HashMap<>();
		conMap.put("con", con);
		conMap.put("preStatement", preStatement);
		conMap.put("result", result);

		if (sqlFile.isDirectory()) {
			// 文件夹处理
			String[] sqlFiles = sqlFile.list();
			// 获取列表，遍历
			for (String fileName : sqlFiles) {
				// 可能存在excel目录，此目录不作处理
				if (EXCEL_DIR_NAME.equals(fileName)) {
					continue;
				}
				// 创建单个文件对象（系统路径分隔符）
				File file = new File(sqlFile.getPath() + System.getProperty("file.separator") + fileName);
				mainProject(file, conMap);
			}
		} else {
			// 文件处理
			mainProject(sqlFile, conMap);
		}
		// 关闭所有资源
		closeAll(conMap);
		System.out.println("工作完成，程序结束!");
	}

	/**
	 * 主程序
	 * 
	 */
	public static void mainProject(File file, Map<String, Object> conMap) {
		try {
			System.out.println("----->第" + k++ + "个文件,正在执行:" + file.getPath());
			// 获取sql语句
			String sqlString = getSqlFromFile(file);
			// 获取结果集
			List<List<String>> table = getResultSet(conMap, sqlString);
			// 创建excel目录
			File excelDir = new File(file.getParentFile(), EXCEL_DIR_NAME);
			excelDir.mkdirs();
			// 获取excel路径
			String path = excelDir.getPath();
			String excelName = file.getName().substring(0, file.getName().lastIndexOf(".")) + ".xlsx";
			File excel = new File(path, excelName);
			// 将结果集写入excel文件中
			resultToExcel(table, excel.getPath());
			System.out.println("执行成功:" + file.getPath() + " -> " + excel.getPath());
			System.out.println();
		} catch (Exception e) {
			System.out.println("[ERROR]--->" + file.getPath());
			e.getStackTrace();
		}
	}

	/**
	 * 读取配置文件，获取配置参数
	 * 
	 * @param propertiesName
	 * @return
	 */
	public static Map<String, String> getProperties(String propertiesName) {
		Properties properties = new Properties();
		// 使用ClassLoader加载properties配置文件生成对应的输入流
		InputStream input = ResultSetToExcel.class.getClassLoader().getResourceAsStream(propertiesName);
		try {
			// 使用properties对象加载输入流
			properties.load(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		Map<String, String> dbMap = new HashMap<>();
		dbMap.put("driver", properties.getProperty("driver"));
		dbMap.put("url", properties.getProperty("url"));
		dbMap.put("username", properties.getProperty("username"));
		dbMap.put("password", properties.getProperty("password"));

		return dbMap;
	}

	/**
	 * 从文件中获取io流，读取sql语句
	 * 
	 * @param file
	 * @return
	 */
	public static String getSqlFromFile(File file) {
		BufferedReader bReader;
		StringBuffer sBuffer = new StringBuffer();
		try {
			bReader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
			String line = new String();
			while ((line = bReader.readLine()) != null) {
				// 拟定一个文件只有一条查询语句
				sBuffer.append(line.trim()).append(" ");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sBuffer.toString();
	}

	/**
	 * 获取数据库连接
	 * 
	 * @param dbMap
	 * @return
	 */
	public static Connection getCon(Map<String, String> dbMap) {
		Connection con = null;// 创建一个数据库连接
		try {
			Class.forName(dbMap.get("driver"));// 加载Oracle驱动程序
			System.out.println("开始尝试连接数据库...");
			String url = dbMap.get("url");
			String username = dbMap.get("username");
			String password = dbMap.get("password");
			con = DriverManager.getConnection(url, username, password);// 获取连接
			System.out.println("连接成功！");
		} catch (Exception e) {
			System.err.println("[ERROR]--->数据库连接失败,请查看网络连接，或者数据库配置文件!");
			e.getStackTrace();
		}
		return con;
	}

	/**
	 * 关闭资源
	 * 
	 * @param result
	 * @param preStatement
	 * @param con
	 */
	public static void closeAll(Map<String, Object> conMap) {
		ResultSet result = (ResultSet) conMap.get("result");
		PreparedStatement preStatement = (PreparedStatement) conMap.get("preStatement");
		Connection con = (Connection) conMap.get("con");
		try {
			if (result != null)
				result.close();
			if (preStatement != null)
				preStatement.close();
			if (con != null)
				con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("数据库已关闭!");
	}

	/**
	 * 通过sql语句获取结果集
	 * 
	 * @return
	 */
	public static List<List<String>> getResultSet(Map<String, Object> conMap, String sqlString) {
		// 结果表
		List<List<String>> table = new ArrayList<>();
		PreparedStatement preStatement = (PreparedStatement) conMap.get("preStatement");// 预编译语句对象
		ResultSet result = (ResultSet) conMap.get("result");// 结果集对象
		Connection con = (Connection) conMap.get("con");
		try {
			System.out.println("sql:" + sqlString);
			preStatement = con.prepareStatement(sqlString);// 实例化预编译语句
			result = preStatement.executeQuery();
			// 获取列名
			List<String> colNames = new ArrayList<>();
			int colCount = result.getMetaData().getColumnCount();
			for (int i = 1; i <= colCount; i++) {
				colNames.add(result.getMetaData().getColumnName(i));
			}
			// 添加表头
			table.add(colNames);
			// 添加记录
			while (result.next()) {
				List<String> row = new ArrayList<>(); // 一行
				for (int i = 1; i <= colCount; i++) {
					row.add(result.getString(i));
				}
				table.add(row);
			}
			System.out.println("共有记录:" + (table.size() - 1));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return table;
	}

	/**
	 * 把结果集导入新生成的excel中
	 * 
	 * @param rs
	 * @param bookPath
	 */
	public static void resultToExcel(List<List<String>> table, String bookPath) {
		try {
			XSSFWorkbook workbook = new XSSFWorkbook();
			XSSFSheet sheet = workbook.createSheet("报表");

			// 表头及风格
			XSSFFont f = workbook.createFont();
			f.setFontHeightInPoints((short) 14);
			f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// 加粗
			XSSFCellStyle style = workbook.createCellStyle();
			;
			style.setFont(f);
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			style.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
			Row thead = sheet.createRow(0);
			for (int i = 0; i < table.get(0).size(); i++) {
				Cell cell = thead.createCell(i);
				cell.setCellValue(table.get(0).get(i));
				cell.setCellStyle(style);
			}
			// 表体
			for (int i = 1; i < table.size(); i++) {
				Row row = sheet.createRow(i);
				for (int j = 0; j < table.get(i).size(); j++) {
					Cell cell = row.createCell(j);
					cell.setCellValue(table.get(i).get(j));
				}
			}
			// 写入输出流
			OutputStream os = new FileOutputStream(new File(bookPath));
			workbook.write(os);
			workbook.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
