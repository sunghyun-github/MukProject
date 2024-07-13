package mpjt.dto;

public class CommentDTO {
    private int frc_idx;
    private int fr_idx;
    private String user_id;
    private String frc_cont;
    private int frc_like;
    private String frc_regd;
    private boolean liked; // 좋아요 상태를 나타내는 필드

    // 기본 생성자
    public CommentDTO() {}

    // 생성자 오버로드
    public CommentDTO(int fr_idx) {
        this.fr_idx = fr_idx;
    }

    public CommentDTO(int frc_idx, int fr_idx, String user_id, String frc_cont, int frc_like, String frc_regd) {
        this.frc_idx = frc_idx;
        this.fr_idx = fr_idx;
        this.user_id = user_id;
        this.frc_cont = frc_cont;
        this.frc_like = frc_like;
        this.frc_regd = frc_regd;
    }

    // Getter 및 Setter 메서드
    public int getFrc_idx() {
        return frc_idx;
    }

    public void setFrc_idx(int frc_idx) {
        this.frc_idx = frc_idx;
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

    public String getFrc_cont() {
        return frc_cont;
    }

    public void setFrc_cont(String frc_cont) {
        this.frc_cont = frc_cont;
    }

    public int getFrc_like() {
        return frc_like;
    }

    public void setFrc_like(int frc_like) {
        this.frc_like = frc_like;
    }

    public String getFrc_regd() {
        return frc_regd;
    }

    public void setFrc_regd(String frc_regd) {
        this.frc_regd = frc_regd;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }
}