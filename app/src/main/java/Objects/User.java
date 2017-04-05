package Objects;

public class User
{
    private String _fname;
    private String _lname;
    private String _sid;
    private String _pass;
    private String _admin;

    public User()
    {
        this._fname = "";
        this._lname = "";
        this._sid = "";
        this._pass = "";
        this._admin = "";
    }

    public String get_fname() {
        return _fname;
    }

    public void set_fname(String _fname) {
        this._fname = _fname;
    }

    public String get_lname() {
        return _lname;
    }

    public void set_lname(String _lname) {
        this._lname = _lname;
    }

    public String get_sid() {
        return _sid;
    }

    public void set_sid(String _sid) {
        this._sid = _sid;
    }

    public String get_pass() {
        return _pass;
    }

    public void set_pass(String _pass) {
        this._pass = _pass;
    }

    public String get_admin() {
        return _admin;
    }

    public void set_admin(String _pass) {
        this._admin = _admin;
    }

}
