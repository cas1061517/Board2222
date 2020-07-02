package com.example.board;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;

@SpringBootTest
class BoardRepositoryTest {
	@Autowired
	BoardRepository br;
	
	@Test
	void 게시글100개() {
		for (int i = 0; i < 100; i++) {
		Board board = new Board();
		board.setTitle(i + "번 제목");
		board.setContent(i + "번 제목");
		board.setUserId("raveve@naver.com");
		br.save(board);
		}
	}
	
	@Test
	void contextLoads() {
	}

}
