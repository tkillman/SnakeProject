package com.project.snake.controller;

import com.project.snake.data.Direction;
import com.project.snake.database.SnakeDTO;
import com.project.snake.network.SnakeGameClient;

import javafx.scene.paint.Color;

public class GameController {
	
	ViewController view_ctr;
	SnakeController snake_ctr;
	SnakeGameClient network;
	boolean isDirection = false;
	
	public GameController(ViewController view_ctr) {
		this.view_ctr = view_ctr;							//ViewController�� ��ü�� ���ڷ� �޴´�.
		snake_ctr = new SnakeController(this);				//SnakeController ��ü ����
		network = new SnakeGameClient(this);				//������ ����
		view_ctr.initGameView();							//�ʱ�ȭ�� ����
		
	}
	
	//���ӽ��� �׼ǿ� ���� ó��
	public void gameStart(){
		snake_ctr.snakeCreate();
		snake_ctr.foodCreate();
		view_ctr.startView(snake_ctr.head, snake_ctr.tail, snake_ctr.food);
		view_ctr.setTimer();
	}
	//���� ����� �׼ǿ� ���� ó��
	public void restart(){
		view_ctr.isKey = true;
		snake_ctr.snake.clear();
		snake_ctr.bodyList.clear();
		snake_ctr.bombList.clear();

		SnakeDTO data = new SnakeDTO("refresh", view_ctr.member.getId(), null, 0, 0, 0, 0);
		//System.out.println(data.getStatus());
		data = network.sendData(data);
		if(data!=null){
			if(data.getStatus().equals("refreshok")){
				view_ctr.member.setT_score(data.getT_score());
				view_ctr.member.setT_food(data.getT_food());
				view_ctr.member.setT_level(data.getT_level());
				view_ctr.member.setT_time(data.getT_time());
			}
		}
		view_ctr.refreshSnakePanel();
		view_ctr.refreshSidePanel();
	}
	//���� ���� �׼ǿ� ���� ó��
	public void quitGame(){
		view_ctr.isKey = false;
		if(view_ctr.isStart){
			view_ctr.runThread.stop();
			view_ctr.timeThread.stop();
		}
				
		snake_ctr.snake.clear();
		snake_ctr.bodyList.clear();
		snake_ctr.bombList.clear();
		view_ctr.member = new SnakeDTO(null, null, null, 0, 0, 0, 0);
		view_ctr.refreshSnakePanel();
		view_ctr.refreshSidePanel();
	}
	//���� ���� �׼ǿ� ���� ó��
	public void gameOver(){
		checkUpdate(view_ctr.member.getId(), view_ctr.scoreCnt, view_ctr.foodCnt, view_ctr.levelCnt, view_ctr.timeCnt);
		view_ctr.gameOverView();
	}
	//������ �Է¿� ���� ĳ������ �������� 
	public void changeDirection(Direction direction){
		if(isDirection) return;
		switch (direction) {
			case UP: if(SnakeController.direction==Direction.DOWN) return; else break;
			case RIGHT: if(SnakeController.direction==Direction.LEFT) return; else break;
			case LEFT: if(SnakeController.direction==Direction.RIGHT) return; else break;
			case DOWN: if(SnakeController.direction==Direction.UP) return; else break;
		}
		SnakeController.direction = direction;
		isDirection = true;
	}
	//�⵿Ȯ���� ĳ���� ���ΰ�ħ
	public void changeSnake(){
		isDirection = false;
		switch(SnakeController.direction){
			case UP: 	checkCrash(-1, 0);	break;
			case RIGHT: checkCrash(0, 1); 	break;
			case LEFT: 	checkCrash(0, -1);	break;
			case DOWN: 	checkCrash(1, 0);	break;
		}
		view_ctr.repaintSnake(snake_ctr.snake, snake_ctr.bodyList, snake_ctr.bombList, snake_ctr.head, snake_ctr.tail, snake_ctr.food);
	}
	//x, y�� ��ǥ�� ĳ���� �⵿Ȯ��
	public void checkCrash(int off_y, int off_x){
		int y = snake_ctr.head.getY() + off_y;
		int x = snake_ctr.head.getX() + off_x;

		if(x<0 || x>ViewController.WIDTH-1 || y<0 || y>ViewController.HEGHT-1){			//���� ���� ���
			gameOver();
			return;
		}		
		else if(snake_ctr.food.getY()==y && snake_ctr.food.getX()==x){					//food�� ���� ���
			snake_ctr.addHead();
			snake_ctr.foodCreate();
			view_ctr.foodCnt++;															//food ī��Ʈ ��
			view_ctr.scoreCnt += (50 * view_ctr.levelCnt) + view_ctr.bonusCnt;			//food ���� 50 + bonus ����
			if(view_ctr.scoreCnt >= 500 * (int)Math.pow(view_ctr.levelCnt+1, 2)){			//level ����
				view_ctr.levelUp();
				view_ctr.showAnimation("levelUp");
			}
			else view_ctr.showAnimation("ScoreUp");
			//����		����		����				���ʽ�
			//0			1		50(50*level)	100(50*level+1)	
			//2000		2		100				150				
			//4500		3		150				200				
			//8000		4		200				250				
			//12500		5		250				300			
			view_ctr.bonusCnt = 50 * (view_ctr.levelCnt+1);								//������ 100���� �ʱ�ȭ	
			
			return;
		}else if(view_ctr.snakeRects[y][x].getFill()!=Color.TRANSPARENT){	//���� ��� ��쵵 �ƴϰ� ���� �ƴ϶�� body�浹
			gameOver();
			return;
		}
		snake_ctr.move(y, x);
	}
	//�α��� �׼ǿ� ���� ó��
	public void checkLogin(String id, String password){
		
		SnakeDTO data = new SnakeDTO("login", id, password, 0, 0, 0, 0);
		data = network.sendData(data);
		
		
		if(data!=null){
			
			if(data.getStatus().equals("loginok")){				//�α��� �����ÿ��� ������ ����
				view_ctr.member.setId(data.getId());
				view_ctr.member.setPassword(data.getPassword());
				view_ctr.member.setT_food(data.getT_food());
				view_ctr.member.setT_score(data.getT_score());
				view_ctr.member.setT_level(data.getT_level());
				view_ctr.member.setT_time(data.getT_time());
				view_ctr.loginView(data.getStatus());
				return;
			}
		}			
		view_ctr.loginView("loginno");
	}
	//ȸ������ �׼ǿ� ���� ó��
	public void checkJoin(String id, String password, String checkPassword){
		if(password.equals(checkPassword)){
			SnakeDTO data = new SnakeDTO("join", id, password, 0, 0, 0, 0);
			data = network.sendData(data);
			//System.out.println(data.getStatus());
			if(data!=null){
				view_ctr.joinView(data.getStatus());
				return;
			}			
		}
		view_ctr.joinView("joinno");
	}
	//��������� ���� ������Ʈ ó��
	public void checkUpdate(String id, int t_score, int t_food, int t_level, int t_time){
		if(view_ctr.member.getT_score()>t_score) t_score = view_ctr.member.getT_score();
		if(view_ctr.member.getT_food()>t_food) t_food = view_ctr.member.getT_food();
		if(view_ctr.member.getT_level()>t_level) t_level = view_ctr.member.getT_level();
		if(view_ctr.member.getT_time()>t_time) t_time = view_ctr.member.getT_time();
		
		SnakeDTO data = new SnakeDTO("update", id, null, t_score, t_food, t_level, t_time);
		data = network.sendData(data);
		//System.out.println(data.getStatus());
		if(data!=null){
			System.out.println("update success");
			return;
		}		
		System.out.println("update fail");
	}
	//��ź ������Ʈ
	public void updateBomb(int check, int bombCnt){
		switch(check){
		case 1: snake_ctr.bombCreate(bombCnt);
		case 0: snake_ctr.bombList.clear(); ViewController.isBomb=false;
		}
		
	}
	
}
