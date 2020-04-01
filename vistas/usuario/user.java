public class user{
    private int user_rol=0;
    private int user_id=0;

    public int get_user_rol () {
      return user_rol;
    }

    public void set_user_rol(int newUR) {
        user_rol = newUR;
    }

    public int get_user_id() {
        return user_id;
    }

    public void set_user_id(int id) { user_id=id; }
}
