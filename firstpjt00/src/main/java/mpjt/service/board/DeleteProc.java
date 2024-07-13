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


@WebServlet("/board/DeleteProc")
public class DeleteProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doGet");
		
		// 1. 값 받기
		request.setCharacterEncoding("utf-8");
		String sNum = request.getParameter("num");
		int num = Integer.parseInt(sNum);
		
		// 2. 값 출력
		//System.out.println(num);
		
		// 3. DTO
		BoardDTO dto = new BoardDTO();		

		dto.setFr_idx(num);

		
		// 4. DAO 
		BoardDAO dao = new BoardDAO();
		dao.deleteWrite(dto);
		
		// 5. move
		String path = request.getContextPath() + "/mbboard/bbs.jsp";

		response.sendRedirect(path);
	}

}
