package com.example.board.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.model.Board;
import com.example.board.model.User;
import com.example.board.repository.BoardRepository;
import com.example.board.repository.UserRepository;

@Controller
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	UserRepository userRepository;
	@Autowired
	BoardRepository boardRepository;
	@Autowired
	HttpSession session;

	@GetMapping("/write")
	public String boardWrite() {
		return "board/write";
	}

	@PostMapping("/write")
	@ResponseBody
	public String boardWritePost(@ModelAttribute Board board) {
		User user =  (User) session.getAttribute("user_info");
		String userId = user.getEmail();
		board.setUserId(userId);
		boardRepository.save(board);
		return "1";
	}

	@GetMapping("/myinfo")
	public String myInfo(User user, Board board) {
		
		
		return null;
	}
	//th:if="${totalPage} > ${startPage}" 
	//th:if="${totalPage} > ${endpage}"
	@GetMapping("/")
	public String board2(Model model, @RequestParam(name = "page", defaultValue = "1") int page) {
		int startPage = (page - 1) / 10 * 10 + 1;
		int endPage = startPage + 9;
		
		
		PageRequest req = PageRequest.of(page - 1, 10, Sort.by(Direction.DESC, "id"));
		Page<Board> result = boardRepository.findAll(req);
		int totalPage = result.getTotalPages();
		if(endPage > totalPage) {
			endPage = totalPage;
		}
		List<Board> list = result.getContent();
		
		model.addAttribute("list", list);
		model.addAttribute("startPage", startPage);
		model.addAttribute("endPage", endPage);
		model.addAttribute("totalPage", totalPage);
		model.addAttribute("page", page);
		return "board/list";
	}
	
	@GetMapping("/{id}")
	public String detail(@PathVariable("id") long id, Model model){
		
		Optional<Board> opt = boardRepository.findById(id);
		Board board = opt.get();
		System.out.println(board);
		model.addAttribute("board", board);
		
		return "/board/detail";
	}
	@PostMapping("/update/{id}")
	public String update(@PathVariable("id") long id, Model model){
		
		Optional<Board> opt = boardRepository.findById(id);
		Board board = opt.get();
		System.out.println(board);
		model.addAttribute("board", board);
		
		return "/board/update";
	}
	@PostMapping("/update")
	@ResponseBody
	public String boardUpdatePost(@ModelAttribute Board board) {
	System.out.println(board);
		
	boardRepository.save(board);
		return "0";
	}
	
	@GetMapping("/delete/{id}")
	public String delete(@PathVariable("id") long id) {
		boardRepository.deleteById(id);
		return "redirect:/board/";
		}
	
}