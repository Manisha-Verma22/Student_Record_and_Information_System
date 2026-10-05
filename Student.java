import java.io.*;
import java.util.*;
/**
 *@author  Joseph deGuzman
 *@version 1.0
 *@since   JDK 1.4
 *@see     <a href="../Person/Person.html">Person</a>
 */
public class Student extends Person implements Serializable
{
  private int         ProbationStatus;
  private int         year;
  private Hashtable   subjects = new Hashtable();
  private static int  count;
  private String      IdNumber;
  private String      course;
  private String      stat = "";
  
  /**
   *Sets the student in Regular Status.
   */
  public static final int REGULAR = 0;
  /**
   *Sets the student in First Probationary Status.
   */
  public static final int FIRST_PROB = 1;
  /**
   *Sets the student in Second Probationary Status.
   */
  public static final int SECOND_PROB = 2;
  /**
   *Sets the student to be advice/forced to shift.
   */
  public static final int SHIFT = 3;
  
  
  /**
   *Creates a student object with no name, a generated ID Number
   *and Regular probationary status.
   */
  public Student(){
    super();
    ProbationStatus = 0;
    addCount();
  }
  /**
   *Creates a student with full name and a generated ID Number
   *@param  thisPerson  a Person object
   */
  public Student(Person thisPerson){
    super(thisPerson);
    addCount();
  }
  /**
   *Creates a student object with first, last and middle names.
   *@param  first  person first name.
   *@param  last   person last name.
   *@param  mid    person middle name.
   */
  public Student(String first,String last,String mid){
    super(first,last,mid);
    addCount();
  }
  /**
   *Creates a student object with first, last and middle names. Also
   *sets student's year and course.
   *@param  first  person first name.
   *@param  last   person last name.
   *@param  mid    person middle name.
   *@param  yr     student year level.
   *@param  crs    student course.
   */
  public Student(String first,String last,String mid,int yr,String crs){
    super(first,last,mid);
    year = yr;
    course = crs;
    addCount();
  }
  
  /**
   *Increments automatically everytime a student object has been created and
   *generates an ID Number for that student.
   */
  private void        addCount(){
    count++;
    setIdNumber();
  }
  /**
   *Adds/Enrolls the student to a subject.
   *@param  thisSubj  The Subject that the student is wished to be enrolled to.
   */
  public  void        addSubject(Subject thisSubj){
    subjects.put(thisSubj.getCode(),thisSubj);
  }
  /**
   *Drops/Withdraws the student to a subject.
   *@param  thisCode  The Subject code that the student is wished to be dropped.
   */
  public  void        dropSubject(String thisCode){
    subjects.remove(thisCode);
  }
  /**
   *Gets the total number of Student objects created.
   *@return count Objects created.
   */
  public  static int  getCount(){
    return count;
  }
  /**
   *Gets the course that this object is currently enrolled to.
   *@return course The Student's course.
   */
  public  String      getCourse(){
    return course;
  }
  /**
   *Gets the Student's ID Number.
   *@return IdNumber Student ID Number.
   */
  public  String      getIdNumber(){
    return IdNumber;
  }
  /**
   *Returns the Student's probation status code.
   *@return ProbationStatus Probation Status Code.
   */
  public  int         getProbationStatus(){
    return ProbationStatus;
  }
  /**
   *Returns the Student's probation status in words
   */
  public  String      getStat(){
    return stat;
  }
  /**
   *Used by <tt>getStat()</tt> method to get Probation Status code equivalent.
   *<p>Returns the following values</p>
   *<br /><tt>REGULAR</tt> if <tt>ProbationStatus</tt> code is <tt>REGULAR</tt>;
   *<br /><tt>UNDER FIRST PROBATION</tt> if <tt>ProbationStatus</tt> code is <tt>FIRST_PROB</tt>;
   *<br /><tt>UNDER SECOND PROBATION</tt> if <tt>ProbationStatus</tt> code is <tt>SECOND_PROB</tt>;
   *<br /><tt>RECOMMENDED TO SHIFT</tt> if <tt>ProbationStatus</tt> code is <tt>SHIFT</tt>;
   *<br /><tt>UNKNOWN</tt> if <tt>ProbationStatus</tt> code is set other than that of the Status Codes.
   */
  private String      getStatus(){
  	int stat = ProbationStatus;
  	String prob="";
    if(stat==0)
      prob = "REGULAR";
    else if(stat==1)
      prob = "UNDER FIRST PROBATION";
    else if(stat==2)
      prob = "UNDER SECOND PROBATION";
    else if(stat==3)
      prob = "RECOMMENDED TO SHIFT";
    else
      prob = "UNKNOWN";
    return prob;
  }
  /**
   *Returns the Hashtable of subjects that the Student has enrolled.
   *@return Hashtable/Collection of enrolled subjects.
   */
  public  Hashtable   getSubjects(){
    return subjects;
  }
  /**
   *Computes and returns the Student's WPA.
   *@return Student's Weighted Percentile Average (WPA).
   */
  public  double      getWPA(){
  	double WPA;
    double tempUnits=0;
    double tempGrades=0;
    Set sKeys = subjects.keySet();
    Vector keys = new Vector(sKeys);
    for(Iterator i=keys.iterator();i.hasNext();){
      String thisKey = ""+i.next();
      Subject temp = (Subject)subjects.get(thisKey);
      tempUnits+=temp.getUnits();
      tempGrades+=temp.getUnits()*temp.getGrade();
    }
    WPA=tempGrades/tempUnits;
    
    return WPA;
  }
  /**
   *Computes and returns the Student's MSCA.
   *@return Student's Major Subjects Calculated Average (MSCA).
   */
  public  double      getMSCA(){
  	double MSCA;
    double tempUnits=0;
    double tempGrades=0;
    Set sKeys = subjects.keySet();
    Vector keys = new Vector(sKeys);
    
    for(Iterator i=keys.iterator();i.hasNext();){
      String thisKey = ""+i.next();
      Subject temp = (Subject)subjects.get(thisKey);
      if(temp.isMajor()==true){
        tempUnits+=temp.getUnits();
        tempGrades+=temp.getUnits()*temp.getGrade();
      }
    }
    
    MSCA=tempGrades/tempUnits;
    
    return MSCA;
  }
  /**
   *Returns the Student's year level.
   *@return Student Year Level
   */
  public  int         getYear(){
    return year;
  }
  /**
   *Sets a new course for the Student.
   *@param course the Student Course.
   */
  public  void        setCourse(String course){
    this.course = course;
  }
  /**
   *Method used bu <tt>addCount()</tt> in generating and ID Number for the Student.
   */
  private void        setIdNumber(){
    if(count<10)
      IdNumber="0000"+count;
    else if(count>9&&count<100)
      IdNumber="000"+count;
    else if(count>99&&count<1000)
      IdNumber="00"+count;
    else if(count>999&&count<10000)
      IdNumber="0"+count;
    else if(count>9999&&count<100000)
      IdNumber=""+count;
  }
  /**
   *Sets and initial value of how many Students has been previously created.
   *@param newCount New initial count.
   */
  public  static void setInitialCount(int newCount){
    count = newCount;
  }
  /**
   *Sets a new ID Number for the student created.
   *@param thisNew The new ID Number to be initialized.
   */
  public  void        setNewId(String thisNew){
    IdNumber = thisNew;
  }
  /**
   *Sets the student's probationary status code and code equivalent (<tt>stat</tt>).
   *@param stat Probationary Status (<tt>REGULAR</tt>,<tt>FIRST_PROB</tt>,
   *            <tt>SECOND_PROB</tt>, or <tt>SHIFT</tt>)
   */
  public  void        setProbationStatus(int stat){
    ProbationStatus=stat;
    this.stat = getStatus();
  }
  /**
   *Sets a user-defined Probationary status without using Status codes.
   *@param thisNewStat New Probationary Status
   */
  public  void        setStat(String thisNewStat){
    stat = thisNewStat;
  }
  /**
   *Sets the Student's year level
   *@param year New year level
   */
  public  void        setYear(int year){
    this.year = year;
  }
  /**
   *Returns Student Informations.
   *@return Student ID Number and full name.
   */
  public  String      toString(){
    return getIdNumber()+" "+super.toString();
  }
}//class