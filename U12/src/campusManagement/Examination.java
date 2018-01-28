package campusManagement;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import javax.swing.plaf.synth.SynthSeparatorUI;

/**
 * Class represents a examination in our management system
 * 
 * @author Lukas Roehrig
 * 
 * @author      	 Shayan     Davari fard
 * @author    Mohammadrahim     Masoumi
 * @author       	  Arian     Tashakkornojehdehi
 * 
 * @version 1
 */
public class Examination {

	/**
	 * name of the examinations
	 */
	private String name;

	/**
	 * credit points achieved if this examination is passed
	 */
	private int creditPoints;

	/**
	 * semester this examination takes place
	 */
	private Semester semester;

	/**
	 * date and start time of the examination
	 */
	LocalDateTime dateBegin;

	/**
	 * date and end time of the examination
	 */
	LocalDateTime dateEnd;

	/**
	 * list of students who registered themselves for this examination
	 */
	private LinkedList<Student> studentsRegistered;

	/**
	 * list of grades which where achieved in this examination
	 */
	private LinkedList<ExaminationGrade> grades;

	/**
	 * Constructs a new Examination with the parameters given
	 * 
	 * @param name
	 * @param creditPoints
	 * @param semester
	 * @param dateBegin
	 * @param dateEnd
	 */
	public Examination(String name, int creditPoints, Semester semester, LocalDateTime dateBegin,
			LocalDateTime dateEnd) {
		this.name = name;
		this.creditPoints = creditPoints;
		this.semester = semester;
		setDateBegin(dateBegin);
		setDateEnd(dateEnd);
		studentsRegistered = new LinkedList<Student>();
		grades = new LinkedList<ExaminationGrade>();
	}

	/**
	 * @return the name of the examination
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return credit points achieved if this examination is passed
	 */
	public int getCreditPoints() {
		return creditPoints;
	}

	/**
	 * @return date and start time of the examination
	 */
	public LocalDateTime getDateBegin() {
		return dateBegin;
	}

	/**
	 * @return date and end time of the examination
	 */
	public LocalDateTime getDateEnd() {
		return dateEnd;
	}

	/**
	 * @return list of students who registered themselves for this examination
	 */
	public LinkedList<Student> getStudentsRegistered() {
		return studentsRegistered;
	}

	/**
	 * @return list of grades which where achieved in this examination
	 */
	public LinkedList<ExaminationGrade> getGrades() {
		return grades;
	}

	/**
	 * @return semester this examination takes place
	 */
	public Semester getSemester() {
		return semester;
	}

	/**
	 * overrides the date and start time of this examination with the date and
	 * time given
	 * 
	 * @param dateBegin
	 */
	public void setDateBegin(LocalDateTime dateBegin) {
		this.dateBegin = dateBegin;
	}

	/**
	 * overrides the date and end time of this examination with the date and
	 * time given
	 * 
	 * @param dateEnd
	 */
	public void setDateEnd(LocalDateTime dateEnd) {
		this.dateEnd = dateEnd;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Examination: ");
		sb.append(getName());
		sb.append(" - CP: ");
		sb.append(getCreditPoints());
		sb.append(" - ");
		sb.append("Students registered: ");
		sb.append(getStudentsRegistered().size());
		sb.append(" - ");
		sb.append("Grades given: ");
		sb.append(grades.size());
		return sb.toString();
	}

	/**
	 * liefert ein Filter-Prädikat zurück, das alle Prüfungsnoten
	 * mit der übergebenen Note selektiert.
	 * 
	 * @param grade
	 * @return
	 */
	public Predicate<ExaminationGrade> filterGradesByGrade(double grade){
		return (ExmGrd -> ExmGrd.getGrade() == grade);
	}
	
	/**
	 * liefert ein Filter-Prädikat zurück, das alle Prüfungsnoten 
	 * mit dem übergebenen Student selektiert.
	 * 
	 * @param student
	 * @return
	 */
	public Predicate<ExaminationGrade> filterGradesByStudent(Student student){
		return (ExmGrd -> ExmGrd.getStudent() == student);
	}
	
	/**
	 * 	bekommt ein Filter-Prädikat übergeben und liefert alle
	 *  Prüfungsnoten der Prüfung in einer java.util.List zurück,
	 *  die der Filter nicht aussortiert hat.
	 * 
	 * @param filter
	 * @return
	 */
	public List<ExaminationGrade> getFilteredGrades(Predicate<ExaminationGrade> filter){
		return grades.stream().filter(filter).collect(Collectors.toList());
	}
	
	/**
	 * liefert den Durchschnitt aller Noten der Prüfung zurück
	 * 
	 * @return
	 */
	public double getAverageGrade(){
		return grades.stream().mapToDouble(a -> a.getGrade()).average().getAsDouble();
	}
	
	
	/**
	 * liefert die Notenverteilung in Form einer HashMap zurück.
	 * Die Keys der HashMap entsprechen der Note und die Values
	 * der HashMap, die entsprechende Anzahl der Häufigkeit 
	 * der Note. Gibt es bei  einer Prüfung beispielsweise 
	 * 3x1.0 , 2x4.0 sowie 5x2.3, so sollte die HashMap 
	 * folgendermaßen aussehen, wenn man die toString() 
	 * Methode verwendet um Sie zu visualisieren: {1.0=3, 4.0=2, 2.3=5}.
	 *  
	 * @return
	 */
	public Map<Double, Integer> getDistributionOfGrades(){
		Map<Double, Integer> NV = new HashMap<Double, Integer>();
		grades.stream().forEach(a -> NV.put(a.getGrade(), 0));
		grades.stream().forEach(a -> NV.put(a.getGrade(), NV.get(a.getGrade()) + 1));
		return NV;
	}
}
