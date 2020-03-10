package model1;
import java.util.ArrayList;

import model1.Board;

//제이슨 데이터가 2개가 있는 거. 하나는 정수값, 하나는 보드형 객체의 배열. 
public class ResBoard {
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public ArrayList<Board> getBoards() {
		return boards;
	}
	public void setBoards(ArrayList<Board> boards) {
		this.boards = boards;
	}
	int code;
	ArrayList<Board> boards; 
}
