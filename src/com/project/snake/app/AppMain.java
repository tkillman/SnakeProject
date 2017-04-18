package com.project.snake.app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class AppMain extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("Resource/Layout1.fxml"));	//fxmlloader ����
	    Parent root = fxmlLoader.load();																			//fxmlload
	    
	    Scene scene = new Scene(root);				//Scene ����, fxml�� ���δ�.
		primaryStage.setScene(scene);				//Stage�� Scene ����
		
		primaryStage.setResizable(true);			//âũ�� ���� �Ұ�
		primaryStage.setTitle("Snake Game");		//���� Ÿ��Ʋ
		//primaryStage.setFullScreenExitHint("");
		//primaryStage.setFullScreen(true);
		//primaryStage.setMaximized(true);
		primaryStage.show();						//ȭ�� ���
	}
	
	public static void main(String[] args) {
		
		launch(args);
		
	}
	
}
