import java.io.*;
/**
 *@author  Jeshurun Ray Flores
 *@version 1.0
 *@since   JDK 1.4
 */
public class Subject implements Serializable{
  private String code;
  private String description;
  private double units;
  private double grade;
  private boolean isMajor;
  
  /**
   *Creates a subject with blank code and description, units of 1, grade of 65
   *and sets subject as a minor subject
   */
  Subject(){
    code="";
    description="";
    units=1;
    grade=65;
    isMajor=false;
  }
  /**
   *Creates a subject with a subject code but no description. Also sets number
   *of units to 1, grade of 65 and as a minor subject.
   *@param code Subject Code.
   */
  Subject(String code){
    this.code=code.toUpperCase();
    description="";
    units=1;
    grade=65;
    isMajor=false;
  }
  Subject(Subject newSbj){
  	code = newSbj.getCode();
  	description = newSbj.getDescription();
  	units = newSbj.getUnits();
  	grade = newSbj.getGrade();
  	isMajor = newSbj.isMajor();
  }
  /**
   *Creates a subject with a subject code and description, units of 1, grade of 65
   *and sets as minor subject.
   *@param code Subject Code.
   *@param SD Subject Description
   */
  Subject(String code, String SD){
    this.code=code.toUpperCase();
    description=SD.toUpperCase();
    units=1;
    grade=65;
    isMajor=false;
  }
  /**
   *Creates a subject with a subject code and description and sets as major subject
   *according to the passed method arguments.
   *@param code Subject Code.
   *@param SD Subject Description
   *@param maj true makes the subject major and minor if otherwise.
   */
  Subject(String code, String SD, boolean maj){
    this.code = code.toUpperCase();
    description = SD.toUpperCase();
    isMajor = maj;
    units=1;
    grade = 65;
  }
  /**
   *Creates a subject with code, description, grade, number of units and if to be
   *set as major or minor.
   *@param code   Subject Code.
   *@param SD     Subject Description
   *@param grade  Subject grade.
   *@param units  Number of Units
   *@param maj    true makes the subject major and minor if otherwise.
   */
  Subject(String code, String SD, double grade, double units, boolean maj){
    this.code = code.toUpperCase();
    description = SD.toUpperCase();
    this.grade = grade;
    this.units = units;
    isMajor = maj;
  }
  /**
   *Identifies if subject is major or minor.
   *@return <tt>true</tt> if major <tt>false</tt> if otherwise.
   */
  public boolean isMajor(){
    return isMajor;
  }
  /**
   *Returns the subject's grade.
   *@return The grade.
   */
  public double getGrade(){
    return grade;
  }
  /**
   *Returns the subject code
   *@return Subject Code
   */
  public String getCode(){
    return code;
  }
  /**
   *Returns the subject description
   *@return Subject description
   */
  public String getDescription(){
    return description;
  }
  /**
   *Returns the number of units.
   *@return Subject unit count.
   */
  public double getUnits(){
    return units;
  }
  /**
   *Sets the subject to be major or minor.
   *@param maj true if major and false if otherwise.
   */
  public void setAsMajor(boolean maj){
    isMajor = maj;
  }
  /**
   *Sets the grade for this subject.
   *@param grade New grade for the subject.
   */
  public void setGrade(double grade){
    this.grade = grade;
  }
  /**
   *Sets a new subject code for this subject in upper case format.
   *@param thisCode New subject code.
   */
  public void setCode(String thisCode){
    code = thisCode.toUpperCase();
  }
  /**
   *Sets a new description for the subject in upper case format.
   *@param SD New subject description
   */
  public void setDescription(String SD){
    description= SD.toUpperCase();
  }
  /**
   *Sets the number of units of the subject.
   *@param units New Units to be set.
   */
  public void setUnits(double units){
    this.units = units;
  }
  /**
   *Returns the Subject information
   *@return In format <tt>code</tt> - <tt>description</tt>
   */
  public String toString(){
    return code+" - "+description;
  }
}//class