/**
 * @author ChuXianJie
 */
package com.cxj.springbootcxj.cxjUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.common.usermodel.HyperlinkType;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFHyperlink;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * Excel工具类
 * 
 * @author ChuXianJie
 * @date 2019年1月2日
 */
public class ExcelUtil {

	public static void main(String[] args) {
		try {
			Map<String, Object> paraMap = new HashMap<>();
			paraMap.put("sqlFilePath", "E:\\aaa\\customer.sql");
			paraMap.put("inFilePath", "E:\\aaa\\ww.xlsx");
			paraMap.put("outFilePath", "E:\\aaa\\qq.xlsx");
			sqlFileToExcel(paraMap);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用于从sql文件中获取内容生成excel表格（主要数据库设计文档）
	 * 
	 * @author ChuXianJie
	 * @param paraMap
	 * @return
	 * @throws IOException
	 */
	public static Map<String, Object> sqlFileToExcel(Map<String, Object> paraMap) throws IOException {
		Map<String, Object> resultMap = new HashMap<>();
		String sqlFilePath = (String) paraMap.get("sqlFilePath");
		String inFilePath = (String) paraMap.get("inFilePath");
		String outFilePath = (String) paraMap.get("outFilePath");
//		if (outFilePath != null && !"".equals(outFilePath)) {
//			FileUtils.createPath(outFilePath);
//		}
		File sqlFile = new File(sqlFilePath);
		FileInputStream fis = null;
		if (inFilePath != null) {
			fis = new FileInputStream(inFilePath);
		}
		XSSFWorkbook workbook = null;
		if (fis != null) {
			workbook = new XSSFWorkbook(fis);
		} else {
			workbook = new XSSFWorkbook();
		}
		File[] sqlFiles = null;
		if (sqlFile.isDirectory()) {
			sqlFiles = sqlFile.listFiles();
		} else if (sqlFile.isFile()) {
			sqlFiles = new File[] { sqlFile };
		} else {
			System.err.println("[ERROR.sqlFileToExcel]-> sqlFilePath文件路径错误!");
		}
		for (File file : sqlFiles) {
			List<Map<String, Object>> tableList = getTableList(file);
			createContent(workbook, tableList);
			for (Map<String, Object> table : tableList) {
				createExcelTable(table, workbook);
			}
		}
		try {
			OutputStream os = new FileOutputStream(outFilePath);
			workbook.write(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (workbook != null) {
				workbook.close();
			}
		}
		return resultMap;
	}

	/**
	 * 解析sql文件sql ddl语句
	 * 
	 * @author ChuXianJie
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static List<Map<String, Object>> getTableList(File file) {
//		sql文件主要内容如下
//		CREATE TABLE `user` (
//				  `RECORD_NO` int(10) NOT NULL AUTO_INCREMENT COMMENT '记录号',
//				  `ACCOUNT_NUM` varchar(10) NOT NULL COMMENT '账号',
//				  `PASSWORD` varchar(16) NOT NULL COMMENT '密码',
//				  `USER_PHOTO` varchar(48) DEFAULT NULL COMMENT '用户头像',
//				  `USER_NAME` varchar(32) DEFAULT NULL COMMENT '用户名称',
//				  `USER_TYPE` char(1) DEFAULT NULL COMMENT '用户类型',
//				  `USER_STATUS` char(1) DEFAULT NULL COMMENT '用户状态',
//				  `CREATE_TIME` timestamp NULL DEFAULT NULL COMMENT '创建时间',
//				  `LAST_LOGIN_TIME` timestamp NULL DEFAULT NULL COMMENT '最后一次登录时间',
//				  `UPDATE_TIME` timestamp NULL DEFAULT NULL COMMENT '更新时间',
//				  PRIMARY KEY (`RECORD_NO`)
//				) ENGINE=InnoDB DEFAULT CHARSET=utf8;
		List<Map<String, Object>> resultList = new ArrayList<>();
		int total = 0;
		if (file != null && file.isFile()) {
			FileReader fr = null;
			BufferedReader br = null;
			try {
				fr = new FileReader(file);
				br = new BufferedReader(fr);
				String line;
				while ((line = br.readLine()) != null) {
					if (line.contains("CREATE TABLE")) {
						line = line.toUpperCase();
						total++;
						Map<String, Object> tableMap = new LinkedHashMap<>();
						resultList.add(tableMap);
						String[] tempStrings;
						tempStrings = line.split(" ");
						tableMap.put("tableName", tempStrings[2].substring(1, tempStrings[2].length() - 1));// 表名
						while ((line = br.readLine()) != null) {
							line = line.toUpperCase();
							if (line.length() > 0 && ")".equals(String.valueOf(line.charAt(0)))) {
								System.out.println("完成一个表");
								break;
							}
							line = line.trim();
							String[] detail = line.split(" ");
							if ("timestamp".equalsIgnoreCase(detail[1]) || detail.length > 6) {
								for (int i = 2; i < detail.length - 1; i++) {
									detail[i] = detail[i + 1];
								}
							}
							tableMap.put(detail[0], detail);
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (br != null) {
					try {
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				if (fr != null) {
					try {
						fr.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

		} else {
			System.err.println("[ERROR.getTableList]-> file格式不正确!");
		}
		System.out.println("应读取表数:" + total);
		System.out.println("实际读取表数:" + resultList.size());
		return resultList;
	}

	/**
	 * @author ChuXianJie
	 * @param workbook
	 * @param tableList
	 */
	private static void createContent(XSSFWorkbook workbook, List<Map<String, Object>> tableList) {
		System.out.println("开始生成excel目录...");
		int index;
		if ((index = workbook.getSheetIndex("目录")) > 0) {
			workbook.removeSheetAt(index);
		}
		XSSFSheet sheet = workbook.createSheet("目录");
		XSSFFont f = workbook.createFont();
		f.setFontHeightInPoints((short) 14);
		f.setBold(true);// 加粗
		XSSFCellStyle cell_contentStyle = getCellStyle(workbook);
		XSSFCellStyle cellStyle = getCellStyle(workbook);
		cell_contentStyle.setBorderLeft(BorderStyle.NONE);
		cell_contentStyle.setBorderTop(BorderStyle.NONE);
		cell_contentStyle.setBorderRight(BorderStyle.NONE);
		cell_contentStyle.setBorderBottom(BorderStyle.NONE);
		cell_contentStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell_contentStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		cell_contentStyle.setFont(f);
		Row row_content = sheet.createRow(1);
		row_content.setHeight((short) 700);
		Cell cell_content = row_content.createCell(1);
		cell_content.setCellValue("目录");
		cell_content.setCellStyle(cell_contentStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));// 起始行, 终止行, 起始列, 终止列
		sheet.setColumnWidth(1, 2000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 6000);

		Row row_2 = sheet.createRow(2);
		row_2.setHeight((short) 600);
		Cell cell_2_1 = row_2.createCell(1);
		Cell cell_2_2 = row_2.createCell(2);
		Cell cell_2_3 = row_2.createCell(3);
		Cell cell_2_4 = row_2.createCell(4);
		cell_2_1.setCellValue("序号");
		cell_2_2.setCellValue("表中文名");
		cell_2_3.setCellValue("表英文名");
		cell_2_4.setCellValue("备注");
		cell_2_1.setCellStyle(cellStyle);
		cell_2_2.setCellStyle(cellStyle);
		cell_2_3.setCellStyle(cellStyle);
		cell_2_4.setCellStyle(cellStyle);
		// 从第四行开始
		for (int i = 0; i < tableList.size(); i++) {
			// 使用creationHelpper来创建XSSFHyperlink对象
			CreationHelper createHelper = workbook.getCreationHelper();
			XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);
			String tableName = (String) tableList.get(i).get("tableName");
			Row row = sheet.createRow(3 + i);
			row.setHeight((short) 600);
			Cell cell_1 = row.createCell(1);
			Cell cell_2 = row.createCell(2);
			Cell cell_3 = row.createCell(3);
			Cell cell_4 = row.createCell(4);
			cell_1.setCellValue(i + 1);
			cell_2.setCellValue("");
			link.setAddress("#" + tableName + "!A1");
			cell_3.setCellValue(tableName);
			cell_3.setHyperlink(link);// 设置超链接
			cell_4.setCellValue("");
			cell_1.setCellStyle(cellStyle);
			cell_2.setCellStyle(cellStyle);
			cell_3.setCellStyle(cellStyle);
			cell_4.setCellStyle(cellStyle);
		}
	}

	/**
	 * @author ChuXianJie
	 * @param table
	 */
	private static void createExcelTable(Map<String, Object> table, XSSFWorkbook workbook) {
		System.out.println("开始生成excel表...");
		if (table.get("tableName") == null) {
			return;
		}
		int index;
		if ((index = workbook.getSheetIndex((String) table.get("tableName"))) > 0) {
			workbook.removeSheetAt(index);
		}
		XSSFSheet sheet = workbook.createSheet((String) table.get("tableName"));
		// 使用creationHelpper来创建XSSFHyperlink对象
		CreationHelper createHelper = workbook.getCreationHelper();
		XSSFHyperlink link = (XSSFHyperlink) createHelper.createHyperlink(HyperlinkType.URL);

		Row row_0 = sheet.createRow(0);
		Cell cell_0_1 = row_0.createCell(1);
		cell_0_1.setCellValue("返回目录");
		link.setAddress("#" + "目录" + "!A1");
		cell_0_1.setHyperlink(link);// 设置超链接

		XSSFFont f = workbook.createFont();
		f.setFontHeightInPoints((short) 12);
		f.setBold(true);// 加粗
		XSSFCellStyle cell_titleStyle = getCellStyle(workbook);
		XSSFCellStyle cellStyle = getCellStyle(workbook);
		cell_titleStyle.setBorderLeft(BorderStyle.NONE);
		cell_titleStyle.setBorderTop(BorderStyle.NONE);
		cell_titleStyle.setBorderRight(BorderStyle.NONE);
		cell_titleStyle.setBorderBottom(BorderStyle.NONE);
		cell_titleStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		cell_titleStyle.setFillForegroundColor(IndexedColors.PALE_BLUE.getIndex());
		cell_titleStyle.setFont(f);
		Row row_1 = sheet.createRow(1);
		row_1.setHeight((short) 600);
		Cell cell_1_1 = row_1.createCell(1);
		Cell cell_1_2 = row_1.createCell(2);
		cell_1_1.setCellValue("表中文名");
		cell_1_2.setCellValue("");
		cell_1_1.setCellStyle(cell_titleStyle);
		cell_1_2.setCellStyle(cell_titleStyle);
		Row row_2 = sheet.createRow(2);
		row_2.setHeight((short) 600);
		Cell cell_2_1 = row_2.createCell(1);
		Cell cell_2_2 = row_2.createCell(2);
		cell_2_1.setCellValue("表英文名");
		cell_2_2.setCellValue((String) table.get("tableName"));
		cell_2_1.setCellStyle(cell_titleStyle);
		cell_2_2.setCellStyle(cell_titleStyle);
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 7));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 2, 7));
		sheet.setColumnWidth(1, 2000);
		sheet.setColumnWidth(2, 6000);
		sheet.setColumnWidth(3, 6000);
		sheet.setColumnWidth(4, 4000);
		sheet.setColumnWidth(5, 4000);
		sheet.setColumnWidth(6, 6000);
		sheet.setColumnWidth(7, 4000);
		Row row_3 = sheet.createRow(3);
		row_3.setHeight((short) 500);
		Cell cell_3_1 = row_3.createCell(1);
		Cell cell_3_2 = row_3.createCell(2);
		Cell cell_3_3 = row_3.createCell(3);
		Cell cell_3_4 = row_3.createCell(4);
		Cell cell_3_5 = row_3.createCell(5);
		Cell cell_3_6 = row_3.createCell(6);
		Cell cell_3_7 = row_3.createCell(7);
		cell_3_1.setCellValue("序号");
		cell_3_2.setCellValue("字段中文名");
		cell_3_3.setCellValue("字段英文名");
		cell_3_4.setCellValue("类型");
		cell_3_5.setCellValue("主键");
		cell_3_6.setCellValue("枚举");
		cell_3_7.setCellValue("备注");
		cell_3_1.setCellStyle(cellStyle);
		cell_3_2.setCellStyle(cellStyle);
		cell_3_3.setCellStyle(cellStyle);
		cell_3_4.setCellStyle(cellStyle);
		cell_3_5.setCellStyle(cellStyle);
		cell_3_6.setCellStyle(cellStyle);
		cell_3_7.setCellStyle(cellStyle);

		int k = 4;
		for (String key : table.keySet()) {
			if (!"tableName".equals(key) && !"PRIMARY".equals(key)) {
				Row tempRow = sheet.createRow(k++);
				tempRow.setHeight((short) 500);
				Cell tempCell_1 = tempRow.createCell(1);// 序号
				Cell tempCell_2 = tempRow.createCell(2);// 字段中文名
				Cell tempCell_3 = tempRow.createCell(3);// 字段英文名
				Cell tempCell_4 = tempRow.createCell(4);// 类型
				Cell tempCell_5 = tempRow.createCell(5);// 主键
				tempCell_1.setCellValue(k - 4);
				tempCell_2.setCellValue(
						((String) ((Object[]) table.get(key))[5]).replaceAll("'", "").replaceAll(",", ""));
				tempCell_3.setCellValue(key.replaceAll("`", ""));
				tempCell_4.setCellValue((String) ((Object[]) table.get(key))[1]);
				for (int i = 1; i <= 7; i++) {
					Cell tempCell = tempRow.getCell(i);
					if (tempCell == null) {
						tempCell = tempRow.createCell(i);
					}
					tempCell.setCellStyle(cellStyle);
				}
			}
		}

	}

	private static XSSFCellStyle getCellStyle(XSSFWorkbook workbook) {
		XSSFCellStyle cellStyle = workbook.createCellStyle();
//		cellStyle.setFillForegroundColor((short) 13);// 设置背景色  
//		cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);  
		cellStyle.setBorderBottom(BorderStyle.THIN); // 下边框
		cellStyle.setBorderLeft(BorderStyle.THIN);// 左边框
		cellStyle.setBorderTop(BorderStyle.THIN);// 上边框
		cellStyle.setBorderRight(BorderStyle.THIN);// 右边框
		cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
		return cellStyle;
	}
}
