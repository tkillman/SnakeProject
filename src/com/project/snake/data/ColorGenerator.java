package com.project.snake.data;

import java.util.LinkedList;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ColorGenerator {
	
	LinkedList<Paint> colorList;
	
	public ColorGenerator() {
		colorList = new LinkedList<>();
	}
	
	public void ColorCreate(){
		colorList.add(Color.web("#f2b233"));	//���
		colorList.add(Color.web("#8342bd"));	//����
		colorList.add(Color.web("#e388cc"));	//��ȫ
		colorList.add(Color.web("#ed6051"));	//��Ȳ
		colorList.add(Color.web("#2eb094"));	//�ʷ�
	}
	
	public Paint getRandomColor(){
		return colorList.get((int)(Math.random()*colorList.size()));
	}
	
	//Ǫ�带 �������� �����Ҷ� Į�� ���̻���
	//����Ҷ��� ���
}

//#3bbdc4 / 60, 187, 194 �ǳ� �ϴû�
//#2cb8d1 / 44, 184, 209 �ϴû�
//#f2b233 / 240, 174, 53 ��Ȳ��
//#8342bd / 129, 66, 189 �����
//#e388cc / 227, 136, 203 ��ȫ��
//#ed6051 / 235, 95, 82 ������
//#2eb094 / 48, 176, 148 �ʷϻ�