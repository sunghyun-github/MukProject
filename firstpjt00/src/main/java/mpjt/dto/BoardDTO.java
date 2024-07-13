package mpjt.dto;

public class BoardDTO {
   private int fr_idx;
   private String user_id;
   private String fr_title;
   private String fr_cont;
   private int fr_visitnum;
   private int fr_like;
   private String fr_ofile;
   private String fr_sfile;
   private String fr_upd;
   private String fr_regd;

   public BoardDTO() {
   }

   public BoardDTO(int fr_idx) {
      this.fr_idx = fr_idx;
   }
   
   public BoardDTO(int fr_idx, String user_id, String fr_title, String fr_cont, int fr_visitnum, int fr_like, String fr_regd) {
      this.fr_idx = fr_idx;
      this.user_id = user_id;
      this.fr_title = fr_title;
      this.fr_cont = fr_cont;
      this.fr_visitnum = fr_visitnum;
      this.fr_like = fr_like;
      this.fr_regd = fr_regd;
   }

   // 게시글 추가
   public BoardDTO(String fr_title, String fr_cont, String user_id, String fr_ofile, String fr_sfile) {
      this.user_id = user_id;
      this.fr_title = fr_title;
      this.fr_cont = fr_cont;
      this.fr_ofile = fr_ofile;
      this.fr_sfile = fr_sfile;
   }
   
   public BoardDTO(String fr_title, String fr_cont, String user_id) {
      this.user_id = user_id;
      this.fr_title = fr_title;
      this.fr_cont = fr_cont;
   }
   
   // 게시글 보기
   public BoardDTO(int fr_idx, String fr_title, String fr_cont, String user_id, String fr_regd, int fr_visitnum, int fr_like, String fr_ofile, String fr_sfile) {
      this.fr_idx = fr_idx;
      this.fr_title = fr_title;
      this.fr_cont = fr_cont;
      this.user_id = user_id;
      this.fr_visitnum = fr_visitnum;
      this.fr_like = fr_like;
      this.fr_regd = fr_regd;
      this.fr_ofile = fr_ofile;
      this.fr_sfile = fr_sfile;
   }
   
   public BoardDTO(int fr_idx, String fr_title, String fr_cont, String user_id, String fr_regd, int fr_visitnum, int fr_like) {
      this.fr_idx = fr_idx;
      this.fr_title = fr_title;
      this.fr_cont = fr_cont;
      this.user_id = user_id;
      this.fr_regd = fr_regd;
      this.fr_visitnum = fr_visitnum;
      this.fr_like = fr_like;
   }
   
   public BoardDTO(int fr_idx, String user_id, String fr_title, String fr_cont, int fr_visitnum, int fr_like,
         String fr_ofile, String fr_sfile, String fr_regd) {
      super();
      this.fr_idx = fr_idx;
      this.user_id = user_id;
      this.fr_title = fr_title;
      this.fr_cont = fr_cont;
      this.fr_visitnum = fr_visitnum;
      this.fr_like = fr_like;
      this.fr_ofile = fr_ofile;
      this.fr_sfile = fr_sfile;
      this.fr_regd = fr_regd;
   }

   
   public BoardDTO(String user_id, String fr_title, String fr_cont, int fr_visitnum, int fr_like) {
      super();
      this.user_id = user_id;
      this.fr_title = fr_title;
      this.fr_cont = fr_cont;
      this.fr_visitnum = fr_visitnum;
      this.fr_like = fr_like;
   }

   public int getFr_idx() {
      return fr_idx;
   }
   public void setFr_idx(int fr_idx) {
      this.fr_idx = fr_idx;
   }
   public String getUser_id() {
      return user_id;
   }
   public void setUser_id(String user_id) {
      this.user_id = user_id;
   }
   public String getFr_title() {
      return fr_title;
   }
   public void setFr_title(String fr_title) {
      this.fr_title = fr_title;
   }
   public String getFr_cont() {
      return fr_cont;
   }
   public void setFr_cont(String fr_cont) {
      this.fr_cont = fr_cont;
   }
   public int getFr_visitnum() {
      return fr_visitnum;
   }
   public void setFr_visitnum(int fr_visitnum) {
      this.fr_visitnum = fr_visitnum;
   }
   public int getFr_like() {
      return fr_like;
   }
   public void setFr_like(int fr_like) {
      this.fr_like = fr_like;
   }
   
   public String getFr_ofile() {
      return fr_ofile;
   }

   public void setFr_ofile(String fr_ofile) {
      this.fr_ofile = fr_ofile;
   }

   public String getFr_sfile() {
      return fr_sfile;
   }

   public void setFr_sfile(String fr_sfile) {
      this.fr_sfile = fr_sfile;
   }

   public String getFr_upd() {
      return fr_upd;
   }
   public void setFr_upd(String fr_upd) {
      this.fr_upd = fr_upd;
   }
   public String getFr_regd() {
      return fr_regd;
   }
   public void setFr_regd(String fr_regd) {
      this.fr_regd = fr_regd;
   }
}