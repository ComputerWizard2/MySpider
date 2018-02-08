package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * 数据库工具类
 * 
 * @version 1.0
 * @author zhangcheng 用于打开和关闭数据库操作
 * 
 */
public class DBUtil {

	// 私有化，不能进行实例
	private DBUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 打开数据�?
	 * 
	 * @author Administrator
	 */
	public static Connection getConnection(String dbName) {

		// 获得配置文件
		Properties pro = new Properties();

		// 获得输入�?
		InputStream inputStream = DBUtil.class.getResourceAsStream("JDBC.properties");
		try {
			pro.load(inputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("加载配置文件失败");
			return null;
		}
		// 读取对应的属�?
		String url = pro.getProperty("URL");
		String userName = pro.getProperty("USERNAME");
		String passWord = pro.getProperty("PASSWORD");
		String dataBaseName = pro.getProperty("DBNAME");
		// 该设置必不可少，是作为支持中文的必要支持
		String encoding = "?useUnicode=true&characterEncoding=utf-8&useSSL=false";
		// 加载驱动�?
		try {
			Class.forName(pro.getProperty("JARNAME"));
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("驱动包找不到");
		}
		// 连接数据�?
		Connection con = null;
		try {
			String newURL = null;
			if (dbName == null) {
				dbName=dataBaseName;
			} 
			newURL = url + dbName + encoding;
			con = DriverManager.getConnection(newURL, userName, passWord);
			return con;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			// 数据库不存在
			try {
				Connection connection = DriverManager.getConnection(url + encoding, userName, passWord);
				// 执行语句
				Statement stm = connection.createStatement();
				stm.execute("create database " + dbName);
				// 释放连接
				releaseDB(connection, stm, null);
				// 连接新数据库
				con = DriverManager.getConnection(url + dataBaseName  + encoding, userName, passWord);
				stm= con.createStatement();
				String table = "create table categroyMovieTable(numid int(100) AUTO_INCREMENT,categroy varchar(100),url varchar(100),title varchar(100),source varchar(100),PRIMARY KEY (numid)) charset=utf8";
				stm.executeUpdate(table);
				table = "CREATE TABLE `moviedetailtable` (`id` int(100) DEFAULT NULL,`director` varchar(100) DEFAULT NULL,`keyword` varchar(500) DEFAULT NULL,`categroy` varchar(500) DEFAULT NULL,`des` varchar(3000) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
				stm.executeUpdate(table);
				table = "CREATE TABLE `movieperformertable` (`id` int(100) DEFAULT NULL,`performer` varchar(100) DEFAULT NULL,`role` varchar(255) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
				stm.executeUpdate(table);
				table = "CREATE TABLE `movietable` (`id` int(100) NOT NULL AUTO_INCREMENT COMMENT '电影的标�?',`moviename` varchar(100) DEFAULT NULL COMMENT '电影名称',`time` varchar(100) DEFAULT NULL COMMENT '电影播放长度',`url` varchar(500) DEFAULT NULL COMMENT '电影地址',`imagepath` varchar(2000) DEFAULT NULL COMMENT '图片地址',`saveimagepath` varchar(500) DEFAULT NULL COMMENT '图片本地保存地址',`score` float(10,1) DEFAULT NULL COMMENT '评分',`status` int(10) DEFAULT NULL COMMENT '完成状�??',`source` varchar(10) NOT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=12364 DEFAULT CHARSET=utf8;";
				stm.executeUpdate(table);
				table = "CREATE TABLE `performerdetailtable` (`name` varchar(100) DEFAULT NULL,`e_name` varchar(100) DEFAULT NULL,`alias` varchar(200) DEFAULT NULL COMMENT '别名',`sex` varchar(10) DEFAULT '',`bloodtype` varchar(5) DEFAULT NULL,`height` varchar(10) DEFAULT NULL,`address` varchar(500) DEFAULT NULL,`birthday` varchar(50) DEFAULT NULL,`constellation` varchar(500) DEFAULT NULL,`location` varchar(200) DEFAULT NULL,`ResidentialAddress` varchar(100) DEFAULT NULL,`school` varchar(200) DEFAULT NULL,`BrokerageAgency` varchar(200) DEFAULT NULL,`fameyear` varchar(200) DEFAULT NULL COMMENT '成名年代',`hobby` varchar(1000) DEFAULT NULL,`Occupation` varchar(500) DEFAULT NULL,`weight` varchar(500) DEFAULT NULL,`image` varchar(1000) DEFAULT NULL,`des` varchar(2000) DEFAULT NULL) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
				stm.executeUpdate(table);
				table = "CREATE TABLE `movieurl` (`id` int(11) NOT NULL AUTO_INCREMENT,`movieurl` varchar(255) DEFAULT NULL,PRIMARY KEY (`id`)) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8;";
				stm.executeUpdate(table);
				// 释放连接
				releaseDB(con, stm, null);
				// 重新建立连接
				getConnection(dbName);
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
//				e1.printStackTrace();
				
				
			}

		}
		return con;
	}

	/**
	 * 关闭数据�?
	 */
	public static void releaseDB(Connection con, Statement smt, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (smt != null) {
			try {
				smt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
