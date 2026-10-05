import java.io.*;
import java.util.*;
/**
 *@author  Jeshurun Ray Flores
 *@version 1.0
 *@since   JDK 1.4
 */
public class Semester implements Serializable{
  private int schoolYear;
  private int semNum;
  private String semCode;
  private Hashtable studEnrolled = new Hashtable();
  private Hashtable subjOpen = new Hashtable();
  
  /**
   *Creates a new semester with null school year, semester as 1 and blank
   *semester code.
   */
  public Semester(){
  	schoolYear = 0;
  	semNum = 1;
  	semCode = "";
  }
  /**
   *Creates a new semester with specified school year, semester number and a generated
   *semester code.
   *@param sy  School Year.
   *@param sem Semester.
   */
  public Semester(int sy, int sem){
    schoolYear = sy;
    semNum = sem;
    setSemCode();
  }
  /**
   *Enrolls/adds a student into the semester.
   *@param thisStudent The student to be enrolled.
   */
  public void       enrollStudent(Student thisStudent){
    studEnrolled.put(thisStudent.getIdNumber(),thisStudent);
  }
  /**
   *Returns the school year to which the semester is set.
   *@return School Year.
   */
  public int        getSchoolYear(){
    return schoolYear;
  }
  /**
   *Returns the generated semester code.
   *@return Semester Code.
   */
  public String     getSemCode(){
    return semCode;
  }
  /**
   *Returns the Semester Number
   *@return Semester Number (first, second, or third semester).
   */
  public int        getSem(){
    return semNum;
  }
  /**
   *Returns the collection of students currently enrolled in semester.
   *@return Hashtable/Collection of students enrolled.
   */
  public Hashtable  getStudentsEnrolled(){
    return studEnrolled;
  }
  /**
   *Returns the collection of subjects currently opened for the semester.
   *@return Hashtable/Collection of subjects opened.
   */
  public Hashtable  getSubjectsOpen(){
    return subjOpen;
  }
  /**
   *Opens a new subject in the semester
   *@param thisSubject The subject to be opened.
   */
  public void       openSubject(Subject thisSubject){
    subjOpen.put(thisSubject.getCode(),thisSubject);
  }
  /**
   *Closes/deletes a subject from the semester.
   *@param code The Subject code of the subject to be closed/deleted.
   */
  public void       closeSubject(String code){
    subjOpen.remove(code);
  }
  /**
   *Sets a new school year for which the semester is to be classified and automatically
   *sets a new semester code for the modified semester.
   *@param newSy The new school year for the semester.
   */
  public void       setSchoolYear(int newSy){
    schoolYear=newSy;
    setSemCode();
  }
  /**
   *Sets a new semester number for the semester and automatically
   *sets a new semester code for the modified semester.
   *@param newSem The new semester number to be set.
   */
  public void       setSem(int newSem){
   semNum = newSem;
   setSemCode();
  }
  /**
   *Sets/Generates a new Semester code for the semester based on the school
   *year to which it bellongs and its semester number.
   */
  public void       setSemCode(){
    String sy = ""+schoolYear;
    String sem = ""+semNum;
    String temp = sy.trim()+"-"+sem.trim();
    semCode = temp.trim();
  }
  /**
   *Returns the objects details.
   *@return Semester Code, number of students currently enrolled and total
   *        number of subjects openned.
   */
  public String     toString(){
    return semCode+"\n students enrolled: "+studEnrolled.size()
                  +"\n subjects open: "+subjOpen.size();
  }
  
}//class