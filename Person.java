import java.io.*;
/**
 *@author  Jeshurun Ray Flores
 *@since   JDK 1.4
 *@version 2.0
 */
public class Person implements Serializable
{
  private String FirstName;
  private String LastName;
  private String MiddleName;
  private String MiddleInitial;
  private static int count;
  
  /**
   *Creates a person object with blank first, last and middle names.
   */
  public Person(){
    FirstName = "";
    LastName = "";
    MiddleName = "";
    
    setCount();
  }
  
  /**
   *Sets <tt>Person</tt> fields according to specified parameters. Also 
   *automatically generates the person's middle initial.
   *@param	fn   Person first name.
   *@param	ln   Person last name.
   *@param	mn   Person middle name.
   */
  public Person(String fn, String ln, String mn){
    FirstName = fn;
    LastName = ln;
    MiddleName = mn;
    setMiddleInitial();
    setCount();
  }
  
  /**
   *Creates new Person object from another Person
   *@param thisPerson Person object.
   */
  public Person(Person thisPerson){
    FirstName = thisPerson.getFirstName();
    LastName = thisPerson.getLastName();
    MiddleName = thisPerson.getMiddleName();
  }
  /**
   *Generates the person's middle initial using the person's middle name.
   */
  public void setMiddleInitial() {
    try{
      MiddleInitial = MiddleName.toUpperCase().trim().charAt(0)+".";
    }
    catch(StringIndexOutOfBoundsException exc){
      MiddleInitial = "";
    }
  }
  /**
   *Gets First Name
   *@return Person's first name.
   */
  public String getFirstName(){
    return FirstName;
  }
  /**
   *Gets Last Name
   *@return Person's last name.
   */
  public String getLastName(){
    return LastName;
  }
  /**
   *Gets Last Name
   *@return Person's Middle Initial.
   */
  public String getMiddleInitial(){
    return MiddleInitial;
  }
  /**
   *Gets Middle Name
   *@return Person's middle name.
   */
  public String getMiddleName(){
    return MiddleName;
  }
  
  /**
   *Gets Person count
   *@return total Person objects created.
   */
  public static int getCount(){
    return count;
  }
  
  /**
   *Sets new First Name
   *@param str new First Name that is to be replaced over old First Name.
   */
  public void setFirstName(String str){
    FirstName = str;
  }
  /**
   *Sets new Last Name
   *@param str new Last Name that is to be replaced over old Last Name.
   */
  public void setLastName(String str){
    LastName = str;
  }
  /**
   *Sets new Middle Name
   *@param str new Middle Name that is to be replaced over old Middle Name.
   */
  public void setMiddleName(String str){
    MiddleName = str;
    setMiddleInitial();
  }
  /**
   *Increments Person counter.
   */
  public static void setCount(){
    count++;
  }
  /**
   *Returns the person's full name details.
   *@return Complete Person details.
   */
  public String toString(){
    return getLastName()+", "+getFirstName();
  }
  
}