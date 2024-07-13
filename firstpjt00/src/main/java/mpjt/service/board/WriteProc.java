package mpjt.service.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mpjt.dao.BoardDAO;
import mpjt.dto.BoardDTO;

@WebServlet("/WriteProc")
public class WriteProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		
		// 1. 값 받기
		request.setCharacterEncoding("utf-8");
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		HttpSession session = request.getSession();		
		String id = (String)session.getAttribute("user_id");
		
		// 2. 값 출력
		//System.out.println(title);
		//System.out.println(content);
		
		// 3. DTO
		BoardDTO dto = new BoardDTO(title, content, id);
		
		// 4. DAO 
		BoardDAO dao = new BoardDAO();
		dao.insertWrite(dto);
		
		// 5. move
		String path = request.getContextPath() + "/mbboard/bbs.jsp";
		response.sendRedirect(path);
	}

}
