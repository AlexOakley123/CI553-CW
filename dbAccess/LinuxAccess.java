package dbAccess;

/**
  * Implements management of an mySQL database on Linux.
  * @author  Mike Smith University of Brighton
  * @version 2.0
  */
class LinuxAccess extends DBAccess
{
  public void loadDriver() throws Exception
  {
    //Class.forName("org.gjt.mm.mysql.Driver").newInstance();
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
  }

  public String urlOfDatabase()
  {
    //return "jdbc:mysql://localhost/cshop?user=root";
    return "jdbc:mysql://ao657_Catshop:Matcet4p@ao657.brighton.domains/ao657_Catshop";
  }
}
