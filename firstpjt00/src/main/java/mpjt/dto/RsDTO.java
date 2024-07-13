package mpjt.dto;

public class RsDTO {
	private int rs_idx;
	private String rs_name;
	private String rs_addr;
	private String rs_num;
	private String rs_hour;
	private String rs_menu;
	private String rs_price;
	private String rs_type;
	private String rs_rating;
	private String rs_comment;
	private int rs_views;
	private int rs_like;
	private int rs_reviews;
	private int rs_rank;
	private String rs_upd;
	private String rs_regd;
	private String rs_img;
	
	
	public RsDTO() {
	}
	
	
	public RsDTO(int rs_idx, String rs_name, String rs_addr, String rs_num, String rs_hour, String rs_menu,
			String rs_price, String rs_type, String rs_rating, String rs_comment, int rs_views, int rs_like,
			int rs_reviews, int rs_rank, String rs_upd, String rs_regd, String rs_img) {
		super();
		this.rs_idx = rs_idx;
		this.rs_name = rs_name;
		this.rs_addr = rs_addr;
		this.rs_num = rs_num;
		this.rs_hour = rs_hour;
		this.rs_menu = rs_menu;
		this.rs_price = rs_price;
		this.rs_type = rs_type;
		this.rs_rating = rs_rating;
		this.rs_comment = rs_comment;
		this.rs_views = rs_views;
		this.rs_like = rs_like;
		this.rs_reviews = rs_reviews;
		this.rs_rank = rs_rank;
		this.rs_upd = rs_upd;
		this.rs_regd = rs_regd;
		this.rs_img = rs_img;
	}

	public RsDTO(int rs_idx, String rs_name, String rs_addr, String rs_num, String rs_hour, String rs_menu, String rs_price, String rs_type, String rs_rating, String rs_comment, String rs_img) {
		this.rs_idx = rs_idx;
		this.rs_name = rs_name;
		this.rs_addr = rs_addr;
		this.rs_num = rs_num;
		this.rs_hour = rs_hour;
		this.rs_menu = rs_menu;
		this.rs_price = rs_price;
		this.rs_type = rs_type;
		this.rs_rating = rs_rating;
		this.rs_comment = rs_comment;
		this.rs_img = rs_img;
	}


	public int getRs_idx() {
		return rs_idx;
	}
	public void setRs_idx(int rs_idx) {
		this.rs_idx = rs_idx;
	}
	public String getRs_name() {
		return rs_name;
	}
	public void setRs_name(String rs_name) {
		this.rs_name = rs_name;
	}
	public String getRs_addr() {
		return rs_addr;
	}
	public void setRs_addr(String rs_addr) {
		this.rs_addr = rs_addr;
	}
	public String getRs_num() {
		return rs_num;
	}
	public void setRs_num(String rs_num) {
		this.rs_num = rs_num;
	}
	public String getRs_hour() {
		return rs_hour;
	}

	public void setRs_hour(String rs_hour) {
		this.rs_hour = rs_hour;
	}

	public String getRs_price() {
		return rs_price;
	}
	public void setRs_price(String rs_price) {
		this.rs_price = rs_price;
	}
	public String getRs_type() {
		return rs_type;
	}
	public void setRs_type(String rs_type) {
		this.rs_type = rs_type;
	}
	public String getRs_rating() {
		return rs_rating;
	}
	public void setRs_rating(String rs_rating) {
		this.rs_rating = rs_rating;
	}
	public int getRs_views() {
		return rs_views;
	}
	public void setRs_views(int rs_views) {
		this.rs_views = rs_views;
	}
	public int getRs_like() {
		return rs_like;
	}
	public void setRs_like(int rs_like) {
		this.rs_like = rs_like;
	}
	public int getRs_reviews() {
		return rs_reviews;
	}
	public void setRs_reviews(int rs_reviews) {
		this.rs_reviews = rs_reviews;
	}
	public int getRs_rank() {
		return rs_rank;
	}
	public void setRs_rank(int rs_rank) {
		this.rs_rank = rs_rank;
	}
	public String getRs_upd() {
		return rs_upd;
	}
	public void setRs_upd(String rs_upd) {
		this.rs_upd = rs_upd;
	}
	public String getRs_regd() {
		return rs_regd;
	}
	public void setRs_regd(String rs_regd) {
		this.rs_regd = rs_regd;
	}
	public String getRs_menu() {
		return rs_menu;
	}
	public void setRs_menu(String rs_menu) {
		this.rs_menu = rs_menu;
	}
	public String getRs_comment() {
		return rs_comment;
	}
	public void setRs_comment(String rs_comment) {
		this.rs_comment = rs_comment;
	}
	public String getRs_img() {
		return rs_img;
	}
	public void setRs_img(String rs_img) {
		this.rs_img = rs_img;
	}

	
	
}


