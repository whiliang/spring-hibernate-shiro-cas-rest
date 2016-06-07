package com.yiwei.paxAdmin.unitTest;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class JDBCTest {
	private final String url = "jdbc:mysql://192.168.6.122:3306/arch?useUnicode=true&amp;characterEncoding=UTF-8";
	private final String driverClass = "com.mysql.jdbc.Driver";
	private final String username = "yiwei";
	private final String password = "yiwei";

	{
		try {
			Class.forName(driverClass);
		} catch (ClassNotFoundException e) {
			System.out.println("Can't load mysql driver class");
			e.printStackTrace();
		}
	}

	public Connection openConnection() {
		try {
			return DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			System.out.println("Can't create connection");
			e.printStackTrace();
			return null;
		}
	}

	class SendFiler implements Callable<Long> {
		private int id;
		private File file;

		public SendFiler(int id, File file) {
			this.id = id;
			this.file = file;
		}

		@Override
		public Long call() throws Exception {
			Long start = System.currentTimeMillis();
			try {
				Connection conn = openConnection();
				PreparedStatement ps = conn
						.prepareStatement("insert into tfiledata(file_id,"
								+ "file_size, file_data) values (?,?,?)");
				ps.setInt(1, id);
				ps.setLong(2, file.length());
				ps.setBlob(3, new FileInputStream(file));
				int result = ps.executeUpdate();
				System.out
						.println(result > 0 ? "SendFiler" + id
								+ " save success" : "SendFiler" + id
								+ " save failture");
				ps.close();
				conn.close();
			} catch (Exception e) {
				System.out.println("SendFiler" + id + "has exceptions");
				e.printStackTrace();
			}
			Long end = System.currentTimeMillis();
			return start - end;
		}

	}

	public static void main(String[] args) {
		ExecutorService executor = Executors.newFixedThreadPool(500);
		for (int i = 1; i < 501; i++) {
			File file = new File("G:\\packages\\PXRetailer-0.55.0-001.zip");
			SendFiler sendFiler = new JDBCTest().new SendFiler(i, file);
			try {
				Future<Long> result = executor.submit(sendFiler);
				Long time = result.get();
				System.out.println("SendFiler" + i + "use " + time);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.exit(0);
	}

}
