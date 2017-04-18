package com.project.snake.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SnakeDAO {
	
	Connection connection;
	
	//�����ڿ��� �����ͺ��̽� ����
	public SnakeDAO() {												
		try {
			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:xe";
			String uid = "dd";
			String upw = "1234";
			connection = DriverManager.getConnection(url, uid, upw);
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	//ID�� PASSWORD�� �Է¹޾� ȸ������ Ȯ��
	public SnakeDTO userCheck(String id, String password){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;;
		SnakeDTO data = null;
		String sql = "select * from snake_members where id = ? and password= ? ";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			data = new SnakeDTO();
			
			if(resultSet.next()){
				data.setStatus("loginok");
				data.setId(resultSet.getString("id"));
				data.setPassword(resultSet.getString("password"));
				data.setT_score(resultSet.getInt("t_score"));
				data.setT_food(resultSet.getInt("t_food"));
				data.setT_level(resultSet.getInt("t_level"));				
				data.setT_time(resultSet.getInt("t_time"));
			}else{
				data.setStatus("loginno");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			data.setStatus("loginno");
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return data;
	}
	
	//ID�� �������� ȸ������ ��ȯ
	public SnakeDTO getMember(String id){
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;;
		SnakeDTO data = null;
		String sql = "select * from snake_members where id = ?";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			resultSet = preparedStatement.executeQuery();
			data = new SnakeDTO();
			
			if(resultSet.next()){
				data.setStatus("refreshok");
				data.setId(resultSet.getString("id"));
				data.setPassword(resultSet.getString("password"));
				data.setT_score(resultSet.getInt("t_score"));
				data.setT_food(resultSet.getInt("t_food"));
				data.setT_level(resultSet.getInt("t_level"));				
				data.setT_time(resultSet.getInt("t_time"));
			}else{
				data.setStatus("refreshno");
			}
		} catch (Exception e) {
			//e.printStackTrace();
			data.setStatus("refreshno");
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return data;
	}
	
	//ȸ�������� Database�� ����
	public SnakeDTO insertMember(String id, String password){
		PreparedStatement preparedStatement = null;
		SnakeDTO data = null;
		String sql = "insert into snake_members values (?, ?, default, default, default, default)";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setString(1, id);
			preparedStatement.setString(2, password);
			data = new SnakeDTO();
			
			preparedStatement.executeUpdate();	
			data.setStatus("joinok");
			
		} catch (Exception e) {
			//e.printStackTrace();
			data.setStatus("joinno");
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return data;
	}
	
	//ȸ���� ������ ������Ʈ
	public SnakeDTO updateInfo(String id, int t_score, int t_food, int t_level, int t_time){
		PreparedStatement preparedStatement = null;
		SnakeDTO data = null;
		String sql = "update snake_members set t_score=?, t_food=?, t_level=?, t_time=? where id=?";
		
		try {
			preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setInt(1, t_score);
			preparedStatement.setInt(2, t_food);
			preparedStatement.setInt(3, t_level);
			preparedStatement.setInt(4, t_time);
			preparedStatement.setString(5, id);
			data = new SnakeDTO();
			
			preparedStatement.executeUpdate();
			data.setStatus("updateok");
			
		} catch (Exception e) {
			//e.printStackTrace();
			data.setStatus("updateno");
		} finally {
			try {
				if(preparedStatement!=null) preparedStatement.close();
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return data;
	}

}
