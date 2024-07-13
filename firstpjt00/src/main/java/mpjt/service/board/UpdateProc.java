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


@WebServlet("/board/UpdateProc")
public class UpdateProc extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("doPost");
		
		// 1. 값 받기
		request.setCharacterEncoding("utf-8");
		String sNum = request.getParameter("num");
		int num = Integer.parseInt(sNum);
		String title = request.getParameter("title");
		String content = request.getParameter("content");		
		
		// 2. 값 출력
		//System.out.println(num);
		//System.out.println(title);
		//System.out.println(content);
		
		// 3. DTO
		BoardDTO dto = new BoardDTO();		

		dto.setFr_idx(num);
		dto.setFr_title(title);
		dto.setFr_cont(content);

		
		// 4. DAO 
		BoardDAO dao = new BoardDAO();
		dao.updateWrite(dto);
		
		// 5. move
		String path = request.getContextPath() + "/mbboard/view.jsp?num="+num;

		response.sendRedirect(path);
	}

}
