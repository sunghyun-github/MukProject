package mpjt.dto;

public class FavoriteDTO {

    private int favorite_idx;
    private int rs_idx;
    private String user_id; 
    private String rs_name;
    private String rs_type;
    private String rs_addr;
    
    
	public int getFavorite_idx() {
		return favorite_idx;
	}
	public void setFavorite_idx(int favorite_idx) {
		this.favorite_idx = favorite_idx;
	}
	public int getRs_idx() {
		return rs_idx;
	}
	public void setRs_idx(int rs_idx) {
		this.rs_idx = rs_idx;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getRs_name() {
		return rs_name;
	}
	public void setRs_name(String rs_name) {
		this.rs_name = rs_name;
	}
	public String getRs_type() {
		return rs_type;
	}
	public void setRs_type(String rs_type) {
		this.rs_type = rs_type;
	}
	public String getRs_addr() {
		return rs_addr;
	}
	public void setRs_addr(String rs_addr) {
		this.rs_addr = rs_addr;
	}
	public FavoriteDTO(int favorite_idx, int rs_idx, String user_id, String rs_name, String rs_type, String rs_addr) {
		super();
		this.favorite_idx = favorite_idx;
		this.rs_idx = rs_idx;
		this.user_id = user_id;
		this.rs_name = rs_name;
		this.rs_type = rs_type;
		this.rs_addr = rs_addr;
	}
	
    
    

 
}
