package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.jpa.JpaMemberRepository;
import com.example.demo.vo.Member;

import ch.qos.logback.core.model.Model;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class MemberController {

	@Autowired
	JpaMemberRepository jpaMember;

	@RequestMapping(value = "/deletememberControl")
	public ModelAndView deletemember(Member mem, HttpSession session, HttpServletRequest request) {
		
		boolean pwMatch = false;
		Member dbMember = null;
		ModelAndView mav = new ModelAndView();
		String id = request.getParameter("id");
		System.out.println("id:" + id);
		try {
			dbMember = jpaMember.getById(id);
			System.out.println("id:" + id);
			System.out.println("dbmember:" + dbMember);
			String dbPw = dbMember.getPw();

			String inputPW = request.getParameter("pw");
			System.out.println("inputPW:" + inputPW);

			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

			pwMatch = encoder.matches(inputPW, dbPw);

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("아이디또틀림");
		}

		if (pwMatch == true) {
			
			mav.addObject("user", mem.getId());
			mav.addObject("pwMatch", pwMatch);
			jpaMember.deleteById(id);
			session.removeAttribute("login_number");
			mav.setViewName("index");

		} else {
			
			mav.addObject("pwMatch", pwMatch);
			System.out.println(pwMatch);
			mav.setViewName("member/deletemember");
		}
		return mav;

	}

	@RequestMapping(value = "/deletemember")
	public ModelAndView deletemember() {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("member/deletemember");
		return mav;
	}

	@RequestMapping(value = "/memberchangeControl")
	public ModelAndView memberchange(@RequestParam(value = "id") String id) {
		Member member = jpaMember.getById(id);

		jpaMember.save(member);
		System.out.println(member);

		ModelAndView mav = new ModelAndView();
		mav.addObject(member);
		mav.setViewName("member/memberchange");
		return mav;
	}
		
	@RequestMapping(value = "memberchange")
	public ModelAndView memberchange(Member member) {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		String encode_pw = encoder.encode(member.getPw());
		member.setPw(encode_pw);
		System.out.println("zz:" + member);
		jpaMember.save(member);
		ModelAndView mav = new ModelAndView();

		mav.setViewName("index");
		return mav;
	}

	@RequestMapping(value = "signinControl")
	public ModelAndView signin(Member member, HttpServletRequest request) {

		String input_pw = member.getPw();
		System.out.println("사용자 입력 비번=" + input_pw);

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String encode_pw = encoder.encode(input_pw);
		System.out.println("암호화 비번=" + encode_pw);
		member.setPw(encode_pw);

		String sex = request.getParameter("gender");
		member.setSex(sex);
		System.out.println("성별: " + sex);
		// 세이브 하기 전에 중복 여부 검사

		jpaMember.save(member);

		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping(value = "/signin")
	public ModelAndView signin() {
		ModelAndView mav = new ModelAndView();
		List<String> memberIdArr = new ArrayList<String>();
		memberIdArr = jpaMember.selectMemberId();
		System.out.println(memberIdArr);
		mav.addObject("memberIdArr", memberIdArr);
		mav.setViewName("member/signin");
		return mav;
	}

	@RequestMapping(value = "/login")
	public ModelAndView login() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/login");
		return mav;
	}
   @RequestMapping(value="loginControl")
   public ModelAndView login(Member mem, HttpSession session) {
      String inputId = mem.getId();
      System.out.println("inputId"+inputId);
      boolean pwMatch = false;
      boolean indexCk = false;
      Member dbMember = null;
      System.out.println("plz : "+mem);
       
      try {
         dbMember = jpaMember.getById(inputId);
        
         String dbPw = dbMember.getPw();
         
         String inputPW = mem.getPw();
        
         
         BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
         
         pwMatch = encoder.matches(inputPW, dbPw);
         
      } catch (Exception e) {
         e.printStackTrace();
            }
         ModelAndView mav = new ModelAndView();
         System.out.println("dbmember : " + dbMember);
         
         // 세션에 저장된 아이디가 관리자 아이디인지 확인
         if (inputId.equals("manager")) {
        	 // 관리자 페이지로 이동
        	 mav.setViewName("admin/admin");
        	 // 필요한 경우 모델에 데이터를 추가
        	 mav.addObject("id", inputId);
        	 
        	 
        	 return mav;
         }
         if(pwMatch == true) {
        	 indexCk = true;
        	 String login_id = dbMember.getId();
        	 
        	 String login_boyandgirl = dbMember.getSex();
        	 System.out.println(login_boyandgirl);
            session.setAttribute("login_number", dbMember);   
            mav.addObject("pwMatch", pwMatch);
            mav.addObject("login_boyandgirl", login_boyandgirl);
            session.setAttribute("indexCk", indexCk);
            session.setAttribute("loginId", login_id);
            mav.setViewName("index");
          
            
         }else {
            System.out.println("아이디틀림");

         }
		return mav;
   }
	@RequestMapping(value = "/logoutControl")
	public ModelAndView logoutControl(HttpSession session) {
		session.removeAttribute("login_number");
		session.removeAttribute("indexCk");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}

	@RequestMapping(value = "/memberFind")
	public ModelAndView memberFind() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/memberFind");
		return mav;

	}

	@RequestMapping(value = "/findname")
	public ModelAndView findnameControl() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/findname");
		return mav;
	}

	@RequestMapping(value = "/findnameControl")
	public ModelAndView findnameControl(HttpServletRequest request) {
		String inputname = request.getParameter("name");
		System.out.println(inputname);
		String id = jpaMember.selectMemberId2(inputname);
		System.out.println(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.setViewName("member/nameresult");

		return mav;
	}

	@RequestMapping(value = "/findemail")
	public ModelAndView findmailControl() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/findemail");
		return mav;
	}

	@RequestMapping(value = "/findemailControl")
	public ModelAndView findmailControl(HttpServletRequest request) {
		String inputemail = request.getParameter("email");
		System.out.println(inputemail);
		String id = jpaMember.selectMemberId3(inputemail);
		System.out.println(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.setViewName("member/emailresult");

		return mav;
	}

	@RequestMapping(value = "/findphone")
	public ModelAndView findPhone() {

		ModelAndView mav = new ModelAndView();

		mav.setViewName("member/findphone");

		return mav;
	}

	@RequestMapping(value = "/findphoneControl")
	public ModelAndView findPhoneControl(HttpServletRequest request) {
		String inputph = request.getParameter("phonenum");
		System.out.println(inputph);
		String id = jpaMember.selectMemberId1(inputph);
		System.out.println(id);
		ModelAndView mav = new ModelAndView();
		mav.addObject("id", id);
		mav.setViewName("member/phoneresult");

		return mav;
	}

	@RequestMapping(value = "/payment")
	public ModelAndView payment() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("member/payment");

		return mav;
	}
	@RequestMapping(value = "/paymentControl")
	public ModelAndView paymentControl(HttpServletRequest request) {
		ModelAndView mav = new ModelAndView();
		String comple = request.getParameter("comple");
		
		String payment = request.getParameter("payment");
		Member member = jpaMember.getById(payment);
		
		if(comple.equals("gen_user")) {
			member.setUsers("일반 유저 승인 대기 중");
			
		}else if (comple.equals("pre_user")) {
			member.setUsers("프리미엄 유저 승인 대기 중");
		}
		
		jpaMember.save(member);
		
		mav.setViewName("index");
		return mav;
	}
	

}
