package alfatecsistemas.tdgov.gestionsede.model;



public class Token{

    private Long access_token;
    private int expires_in;
    private String token_type;
    private String scope;
    private Long refresh_token;

    public Long getAccess_token() {
        return access_token;
    }

    public Long getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(Long refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public void setAccess_token(Long access_token) {
        this.access_token = access_token;
    }


}