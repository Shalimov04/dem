package kval;

public class Request {
	private int id_request;
    private String add_date;
    private String comments;
    private String status;
    private Integer idtech;
    private Integer idclient;
    
    
	public Request() {
		super();
	}
    
	public Request(int id_request, String add_date, String comments, String status, Integer idtech, Integer idclient) {
		super();
		this.id_request = id_request;
		this.add_date = add_date;
		this.comments = comments;
		this.status = status;
		this.idtech = idtech;
		this.idclient = idclient;
	}
	
    
    public int getId_request() {
		return id_request;
	}
	public void setId_request(int id_request) {
		this.id_request = id_request;
	}
	public String getAdd_date() {
		return add_date;
	}
	public void setAdd_date(String add_date) {
		this.add_date = add_date;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Integer getIdtech() {
		return idtech;
	}
	public void setIdtech(Integer idtech) {
		this.idtech = idtech;
	}
	public Integer getIdclient() {
		return idclient;
	}
	public void setIdclient(Integer idclient) {
		this.idclient = idclient;
	}
}
