package mpjt.dto;

public class LikedPostDTO {
    private int fr_idx;
    private String fr_title;
    private String fr_cont;
    private String user_id;
    private String fr_regd;
    private int fr_like;

	// Getters and Setters
    public int getFr_idx() {
        return fr_idx;
    }

    public void setFr_idx(int fr_idx) {
        this.fr_idx = fr_idx;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
    
    public String getFr_regd() {
		return fr_regd;
	}

	public void setFr_regd(String fr_regd) {
		this.fr_regd = fr_regd;
	}

	public int getFr_like() {
		return fr_like;
	}

	public void setFr_like(int fr_like) {
		this.fr_like = fr_like;
	}
}