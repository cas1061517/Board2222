package com.example.board.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.board.model.User;
import com.example.board.repository.UserRepository;

@Controller
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	HttpSession session;
	

	@GetMapping("/signup")
	public String signup() {
		return "signup";
	}

	@PostMapping("/signup")
	@ResponseBody
	public String signupPost(@ModelAttribute User user) {

		/* 중복 아이디 가입 불가를 위해서 가입여부 확인 */
		User findUser = userRepository.findByEmail(user.getEmail());
		if (findUser == null) {
			userRepository.save(user);
		} else {
			
			return "1";
		}
		return "2";
	}


	@GetMapping("/signout")
	public String signout() {
		session.removeAttribute("user_info"); // 지정된 세션값만 삭제
		session.invalidate(); // 세션의 모든 정보 삭제
		return "redirect:/";
	}
	
	@GetMapping("/signin")
	public String signin() {
		return "signin";
	}

	@PostMapping("/signin")
	@ResponseBody
	public String signinPost(@ModelAttribute User user) {
		User dbUser = userRepository.findByEmailAndPwd(user.getEmail(), user.getPwd());
		if (dbUser != null) {
			session.setAttribute("user_info", dbUser);
			return "1";
		}else {
			return "2";
		}
	}

}
