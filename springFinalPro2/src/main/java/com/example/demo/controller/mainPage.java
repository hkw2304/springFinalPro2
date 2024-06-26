package com.example.demo.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import com.example.demo.jpa.BoardRepo;
import com.example.demo.jpa.CommRepo;
import com.example.demo.jpa.CourseRepo;
import com.example.demo.jpa.EngMiddleRepo;
import com.example.demo.jpa.EngRepo;
import com.example.demo.jpa.EngUpperRepo;
import com.example.demo.jpa.GradeBankRepo;
import com.example.demo.jpa.JpaMemberRepository;
import com.example.demo.jpa.KorMiddleRepo;
import com.example.demo.jpa.KorRepo;
import com.example.demo.jpa.KorUpperRepo;
import com.example.demo.jpa.MathMiddleRepo;
import com.example.demo.jpa.MathRepo;
import com.example.demo.jpa.MathUpperRepo;
import com.example.demo.jpa.MypageInfoRepo;

import com.example.demo.jpa.NoticeRepo;

import com.example.demo.vo.Board;
import com.example.demo.vo.CommVo;
import com.example.demo.jpa.freeTestRepo;

import com.example.demo.vo.CourseVo;
import com.example.demo.vo.EngBookVo;
import com.example.demo.vo.EngMiddleBookVo;
import com.example.demo.vo.EngUpperBookVo;
import com.example.demo.vo.FreeBookVo;
import com.example.demo.vo.GradeBankVo;
import com.example.demo.vo.KorBookVo;
import com.example.demo.vo.KorMiddleBookVo;
import com.example.demo.vo.KorUpperBookVo;
import com.example.demo.vo.MathBookVo;
import com.example.demo.vo.MathMiddleBookVo;
import com.example.demo.vo.MathUpperBookVo;
import com.example.demo.vo.Member;
import com.example.demo.vo.MypageInfo;
import com.example.demo.vo.Notice;

import jakarta.security.auth.message.callback.PrivateKeyCallback.Request;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

//蹂�寃쏀뀒�뒪�듃!!!
@Controller
public class mainPage {
	@Autowired
	JpaMemberRepository jpaMember;
	@Autowired
	KorRepo jpaKor;
	@Autowired
	MathRepo jpaMath;
	@Autowired
	EngRepo jpaEng;
	@Autowired
	CourseRepo jpaCourse;
	@Autowired
	MypageInfoRepo jpaMypage;
	@Autowired
	BoardRepo jpaBoard;
	@Autowired
	NoticeRepo jpaNotice;
	@Autowired
	freeTestRepo jpaFree;
	@Autowired
	EngUpperRepo jpaEngUpper;
	@Autowired
	KorUpperRepo jpaKorUpper;
	@Autowired
	MathUpperRepo jpaMathUpper;
	@Autowired
	KorMiddleRepo jpaKorMiddle;
	@Autowired
	MathMiddleRepo jpaMathMiddle;
	@Autowired
	EngMiddleRepo jpaEngMiddle;
	@Autowired 
	CommRepo commrepo;


	@Autowired
	GradeBankRepo jpaGradeBank;

	
	@RequestMapping(value="/index")
	public ModelAndView indexPage(HttpSession session) {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("index");
		return mav;
	}
	
	@RequestMapping(value="/analyze")
	public ModelAndView analyzePage() {
		ModelAndView mav = new ModelAndView();
	
		mav.setViewName("analyze/analyze");
		return mav;
	}
	
	@RequestMapping(value="/freeTest")
	public ModelAndView freeTest() {
		ModelAndView mav = new ModelAndView();
		List<FreeBookVo> freeList = jpaFree.findAll();
		System.out.println(freeList);
		mav.addObject("freeList", freeList);
		mav.setViewName("book/freeTest");
		return mav;
	}
	
	@RequestMapping(value="/searchPage")
	public ModelAndView searchPage(@RequestParam (name="search")String title) {
		ModelAndView mav = new ModelAndView();
		List<CourseVo> courseList = null;
		if(title.length() == 0) {
			mav.addObject("list", courseList);
		}
		else {
			courseList = jpaCourse.selectCourseTitle(title);
			if(courseList.size() == 0) {
				courseList = null;
				mav.addObject("list", courseList);
			}
			else {
			mav.addObject("list", courseList);
			}
		}
		mav.setViewName("search/searchPage");			
		return mav;
	}
	@RequestMapping(value="/korBook")
	public ModelAndView korBook() {
		ModelAndView mav = new ModelAndView();
		List<KorBookVo> korList = jpaKor.findAll();
		mav.addObject("korList", korList);
		mav.setViewName("book/korBook");
		return mav;
	}
	@RequestMapping(value="/engBook")
	public ModelAndView engBook() {
		ModelAndView mav = new ModelAndView();
		List<EngBookVo> engList = jpaEng.findAll();
		mav.addObject("engList", engList);
		mav.setViewName("book/engBook");
		return mav;
	}
	@RequestMapping(value="/korUpperBook")
	public ModelAndView korUpperBook() {
		ModelAndView mav = new ModelAndView();
		List<KorUpperBookVo> korList = jpaKorUpper.findAll();
		mav.addObject("korList", korList);
		mav.setViewName("book/korUpperBook");
		return mav;
	}
	@RequestMapping(value="/mathUpperBook")
	public ModelAndView mathUpperBook() {
		ModelAndView mav = new ModelAndView();
		List<MathUpperBookVo> mathList = jpaMathUpper.findAll();
		mav.addObject("mathList", mathList);
		mav.setViewName("book/mathUpperBook");
		return mav;
	}
	@RequestMapping(value="/engUpperBook")
	public ModelAndView engUpperBook() {
		ModelAndView mav = new ModelAndView();
		List<EngUpperBookVo> engList = jpaEngUpper.findAll();
		mav.addObject("engList", engList);
		mav.setViewName("book/engUpperBook");
		return mav;
	}
	@RequestMapping(value="/mathBook")
	public ModelAndView mathBook() {
		ModelAndView mav = new ModelAndView();
		List<MathBookVo> mathList = jpaMath.findAll();
		mav.addObject("mathList", mathList);
		mav.setViewName("book/mathBook");
		return mav;
	}
	
	@RequestMapping(value="/korMiddleBook")
	public ModelAndView korMiddleBook() {
		ModelAndView mav = new ModelAndView();
		List<KorMiddleBookVo> korList = jpaKorMiddle.findAll();
		mav.addObject("korList", korList);
		mav.setViewName("book/korMiddleBook");
		return mav;
	}
	@RequestMapping(value="/mathMiddleBook")
	public ModelAndView mathMiddleBook() {
		ModelAndView mav = new ModelAndView();
		List<MathMiddleBookVo> mathList = jpaMathMiddle.findAll();
		mav.addObject("mathList", mathList);
		mav.setViewName("book/mathMiddleBook");
		return mav;
	}
	@RequestMapping(value="/engMiddleBook")
	public ModelAndView engMiddleBook() {
		ModelAndView mav = new ModelAndView();
		List<EngMiddleBookVo> engList = jpaEngMiddle.findAll();
		mav.addObject("engList", engList);
		mav.setViewName("book/engMiddleBook");
		return mav;
	}
	
	
	
	
	@RequestMapping(value="/myPage")
	public ModelAndView myPage(@RequestParam("id") String id, MypageInfo mypageInfo) {
		ModelAndView mav = new ModelAndView();
		System.out.println("mypage >>" + id);
		List<MypageInfo> mypageArr= null;
		mypageArr = jpaMypage.selectById(id);
		
		System.out.println("mypageInfo >> " + mypageArr.size());
		if(mypageArr.size() == 0) {

			System.out.println("�뫜 臾몄젣 �뾾�떎.");
			mav.addObject("mypage", "문제 푸세요!!!");

			
			mav.addObject("mypage", "임마 문제풀어!!!");

		}
		mav.addObject("mypageArr", mypageArr);
		
		mav.setViewName("member/myPage");
		return mav;
	}
	
	@RequestMapping(value = "/detailcourse")
	public ModelAndView detail(HttpServletRequest request) {
		String title = request.getParameter("title");
		ModelAndView mav = new ModelAndView();
		CourseVo course = jpaCourse.getById(title);
		mav.addObject("item", course);
		mav.setViewName("search/detailCourse");
		return mav;
	}

	@RequestMapping(value = "/adminlogin")
	public ModelAndView adminlogin() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/adminlogin");

		return mav;
	}
	@RequestMapping(value = "/admin")
	public ModelAndView admin() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/admin");

		return mav;
	}
	@RequestMapping(value = "/board")
	public ModelAndView board() {
		
		ModelAndView mav = new ModelAndView();
		List<Board> boardlist = jpaBoard.findAll();
		
		mav.addObject("boardlist",boardlist);
		mav.setViewName("admin/board/boardlist");

		return mav;
	}
	@RequestMapping(value = "/boardedit")
	public ModelAndView boardedit(HttpServletRequest request) {
		String numbers = request.getParameter("numbers");
	      int num = Integer.parseInt(numbers);
	      System.out.println("num" + num);
		ModelAndView mav = new ModelAndView();
		Board course = jpaBoard.getById(num);
		System.out.println("course: 11111"+course);
		
		mav.setViewName("admin/board/boardedit");
		mav.addObject("list", course);
		
		return mav;
	}
	
	@RequestMapping(value = "/boardeditregister")
	public ModelAndView boardeditregister() {
		
		ModelAndView mav = new ModelAndView();
		List<Board> boardlist = jpaBoard.findAll();
		
		mav.addObject("boardlist",boardlist);
		mav.setViewName("admin/board/boardedit");

		return mav;
	}
	
	@RequestMapping(value = "/adminnotice")
	public ModelAndView notice() {
		
		ModelAndView mav = new ModelAndView();
		List<Notice> noticelist = jpaNotice.findAll();
		System.out.println(noticelist);
		mav.addObject("noticelist",noticelist);
		mav.setViewName("admin/notice/noticelist");

		return mav;
	}
	@RequestMapping(value = "/boardregister")
	public ModelAndView boardregister(Board boardvo) {
		
		ModelAndView mav = new ModelAndView();
		
		
		
		jpaBoard.save(boardvo);
		List<Board> boardlist = jpaBoard.findAll();
		mav.addObject("boardlist",boardlist);
		mav.setViewName("admin/board/boardlist");
		System.out.println(boardvo);
		return mav;
	}
	@RequestMapping(value = "/noticeregister")
	public ModelAndView boardregister(Notice noticevo) {
		
		ModelAndView mav = new ModelAndView();
		jpaNotice.save(noticevo);
		List<Notice> noticelist = jpaNotice.findAll();
		mav.addObject("noticelist", noticelist);
		mav.setViewName("admin/notice/noticelist");
		System.out.println(noticevo);
		return mav;
	}
	
	@RequestMapping(value = "/boardwrite")
	public ModelAndView boardview() {
		
		ModelAndView mav = new ModelAndView();	
		mav.setViewName("admin/board/boardwrite");
		return mav;
	}
	@RequestMapping(value = "/noticewrite")
	public ModelAndView noticeview() {
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/notice/noticewrite");

		return mav;
	}

	@RequestMapping(value = "/boardview")
	   public ModelAndView boardview(HttpServletRequest request) {
	      String numbers = request.getParameter("numbers");
	      int num = Integer.parseInt(numbers);
	      List<CommVo> commvo = commrepo.selectbynum(num);
	     
	      ModelAndView mav = new ModelAndView();
	      mav.addObject("commvolist", commvo);
	     
	      Board course = jpaBoard.getById(num);
	    
	      mav.addObject("list", course);
	     
	      mav.setViewName("admin/board/boardview");
	    
	      return mav;
	   }
	@RequestMapping(value = "/noticeview")
	   public ModelAndView noticeview(HttpServletRequest request) { 
	      String numbers = request.getParameter("numbers");
	      int num = Integer.parseInt(numbers);
	      ModelAndView mav = new ModelAndView();
	      Notice course = jpaNotice.getById(num);
	      System.out.println(course);
	      mav.addObject("list", course);
	      mav.setViewName("admin/notice/noticeview");
	      return mav;
	   }
	@RequestMapping(value = "/usernoticeview")
	   public ModelAndView usernoticeview(HttpServletRequest request) {
	      String numbers = request.getParameter("numbers");
	      int num = Integer.parseInt(numbers);
	      System.out.println(numbers);
	      ModelAndView mav = new ModelAndView();
	      Notice course = jpaNotice.getById(num);
	      System.out.println(course);
	      mav.addObject("list", course);
	      mav.setViewName("admin/notice/usernoticeview");
	      return mav;
	   }
	@RequestMapping(value = "/usernotice")
	public ModelAndView usernotice() {
		
		ModelAndView mav = new ModelAndView();
		List<Notice> noticelist = jpaNotice.findAll();
		System.out.println(noticelist);
		mav.addObject("noticelist", noticelist);
		mav.setViewName("admin/notice/usernoticelist");
		
		return mav;
	}
	@RequestMapping(value = "/levelup")

	   public ModelAndView levelup() {
	      ModelAndView mav = new ModelAndView();
	      List<Member> memberlist = jpaMember.findAll();
	      for(int i = 0; i<memberlist.size(); i++) {
	         if(memberlist.get(i).getUsers()==null) {
	            
	         }else if(memberlist.get(i).getUsers().equals("일반 유저 승인 대기 중")) {
	            
	         }else if(memberlist.get(i).getUsers().equals("프리미엄 유저 승인 대기 중")) {
	      }
	      }
	      mav.addObject("memberlist", memberlist);
	      System.out.println("member 는 " + memberlist);
	      mav.setViewName("admin/levelup");



	      return mav;
	   }
	

	@RequestMapping(value = "/levelupControl")
	   public ModelAndView levelupControl(HttpServletRequest request) {
	      ModelAndView mav = new ModelAndView();
	      GradeBankVo gradeBank = new GradeBankVo();
	      List<Member> member = jpaMember.findAll();
	      final int genMoney = 10000;
	      final int preMoney = 15000;
	      double id = 0.0;
	      for(int i = 0; i<member.size(); i++) {
	         if(member.get(i).getUsers()==null) {
	            System.out.println("아무거나");
	         }else if(member.get(i).getUsers().equals("일반 유저 승인 대기 중")) {
	            member.get(i).setUsers("일반유저");
	            id = Math.round(Math.random() * 1000);
	            gradeBank.setId(id);
	            gradeBank.setGenmoney(genMoney);
	            gradeBank.setPremoney(0);
	            jpaGradeBank.save(gradeBank);
	            jpaMember.save(member.get(i));
	            
	         }else if(member.get(i).getUsers().equals("프리미엄 유저 승인 대기 중")) {
	            member.get(i).setUsers("프리미엄");
	            id = Math.round(Math.random() * 1000);
	            gradeBank.setId(id);
	            gradeBank.setGenmoney(0);
	            gradeBank.setPremoney(preMoney);
	            jpaGradeBank.save(gradeBank);
	            jpaMember.save(member.get(i));
	         }
	         
	      }
	      System.out.println("memberrrrrrr:" + member);
	      mav.setViewName("admin/admin");
	      return mav;
	      
	   }

	@RequestMapping(value = "/allmember")
	public ModelAndView allmember() {
		ModelAndView mav = new ModelAndView();
		List<Member> allmemberlist = jpaMember.findAll();
		System.out.println("allmemberlist�뒗" + allmemberlist);
		mav.addObject("allmemberlist", allmemberlist);
		mav.setViewName("admin/allmember");
		return mav;
	}
	@RequestMapping(value = "/membership")
	public ModelAndView membership() {
		ModelAndView mav = new ModelAndView();
		List<Member> membership = jpaMember.findAll();
		System.out.println("membership : " + membership);
		mav.addObject("membership",membership);
		mav.setViewName("admin/membership");
		return mav;
	}
	@RequestMapping(value = "/customerQna")
	public ModelAndView customerQna() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("admin/notice/customerQna");
		return mav;
	}
	@RequestMapping(value = "/baseballgame")
	public ModelAndView baseballgame() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("game/baseballgame");
		return mav;
	}
	@RequestMapping(value = "/wordreplay")
	public ModelAndView wordreplay() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("game/wordreplay");
		return mav;
	}
	@RequestMapping(value = "/englishgame")
	public ModelAndView englishgame() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("game/englishgame");
		return mav;
	}
}

