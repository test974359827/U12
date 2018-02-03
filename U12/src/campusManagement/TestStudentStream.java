package campusManagement;

import static org.junit.Assert.assertEquals;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.*;
import org.junit.BeforeClass;
/**
* @author      		 Shayan     Davari fard
* @author     Mohammadrahim     Masoumi
* @author       	  Arian     Tashakkornojehdehi
* 
* @version 1
*/
public class TestStudentStream {
	static double epsilon = 0.0001;

	private static CampusManagement management;

	private static Examination vc;
	private static Examination fop;
	private static Examination cs;
	private static Examination math1;
	private static Examination se;
	private static Examination math2;

	private static Student johnDoe;
	private static Student peterClark;
	private static Student taylorSmith;
	private static Student annaWilliams;

	@BeforeClass
	public static void initDataBeforeClass() {
		management = new CampusManagement("Test System", Semester.WiSe_17_18);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

		// example data
		vc = management.addExamination("Visual Computing", 5, LocalDateTime.parse("01.03.2018 09:00", formatter),
				LocalDateTime.parse("01.03.2018 11:30", formatter));
		fop = management.addExamination("Functional and Object-oriented Programming Concepts ", 10,
				LocalDateTime.parse("02.04.2018 13:30", formatter), LocalDateTime.parse("02.04.2018 15:00", formatter));
		cs = management.addExamination("Computer Security", 5, LocalDateTime.parse("12.02.2018 08:00", formatter),
				LocalDateTime.parse("12.02.2018 09:30", formatter));

		math1 = management.addPastExamination("Mathematics I", 9, Semester.SoSe_17,
				LocalDateTime.parse("05.10.2017 09:00", formatter), LocalDateTime.parse("05.10.2017 11:30", formatter));
		se = management.addPastExamination("Software Engineering", 5, Semester.SoSe_17,
				LocalDateTime.parse("25.09.2017 13:30", formatter), LocalDateTime.parse("25.09.2017 15:00", formatter));
		math2 = management.addPastExamination("Mathematics II", 9, Semester.WiSe_16_17,
				LocalDateTime.parse("03.04.2017 08:00", formatter), LocalDateTime.parse("03.04.2017 09:30", formatter));

		johnDoe = management.addStudent("John", "Doe", 11111, "Computer Science B.Sc.");
		peterClark = management.addStudent("Peter", "Clark", 22222, "Computer Science B.Ed.");
		taylorSmith = management.addStudent("Taylor", "Smith", 33333, "Computer Science B.Sc.");
		annaWilliams = management.addStudent("Anna", "Williams", 44444, "Computer Science B.Sc.");

		management.registerStudentForExamination(johnDoe, math1);
		management.registerStudentForExamination(johnDoe, se);
		management.registerStudentForExamination(johnDoe, math2);
		management.addExaminationGradeForStudent(2.0, johnDoe, math1);
		management.addExaminationGradeForStudent(2.0, johnDoe, se);
		management.addExaminationGradeForStudent(2.0, johnDoe, math2);

		management.registerStudentForExamination(peterClark, math1);
		management.registerStudentForExamination(peterClark, se);
		management.registerStudentForExamination(peterClark, math2);
		management.addExaminationGradeForStudent(4.0, peterClark, math1);
		management.addExaminationGradeForStudent(1.7, peterClark, se);
		management.addExaminationGradeForStudent(3.7, peterClark, math2);

		management.registerStudentForExamination(taylorSmith, math1);
		management.registerStudentForExamination(taylorSmith, se);
		management.registerStudentForExamination(taylorSmith, math2);
		management.addExaminationGradeForStudent(1.3, taylorSmith, math1);
		management.addExaminationGradeForStudent(2.0, taylorSmith, se);
		management.addExaminationGradeForStudent(1.7, taylorSmith, math2);

		management.registerStudentForExamination(annaWilliams, math1);
		management.addExaminationGradeForStudent(1.0, annaWilliams, math1);
	}
	
	
	@Test
	public void Test_getFilteredStudents_filterStudentsByName(){
		List<Student> stdList = management.getFilteredStudents(management.filterStudentsByName("John","Doe"));
		assertEquals(1,stdList.size());
		assertEquals("John",stdList.get(0).getFirstName());
		assertEquals("Doe",stdList.get(0).getLastName());
		assertEquals(11111,stdList.get(0).getMatriculationNumber());
		assertEquals("Computer Science B.Sc.",stdList.get(0).getCourseOfStudies());
		
		stdList =  management.getFilteredStudents(management.filterStudentsByName("TAYLOR","smith"));
		assertEquals(1,stdList.size());
		assertEquals("Taylor",stdList.get(0).getFirstName());
		assertEquals("Smith",stdList.get(0).getLastName());		
		assertEquals(33333,stdList.get(0).getMatriculationNumber());
		assertEquals("Computer Science B.Sc.",stdList.get(0).getCourseOfStudies());
		assertEquals("Student: 33333 - Taylor Smith",stdList.get(0).toString());
		
		stdList =  management.getFilteredStudents(management.filterStudentsByName("Shayan","smith"));
		assertEquals(0,stdList.size());
	}
	
	@Test
	public void Test_getFilteredStudents_filterStudentsByMatriculationNumber(){
		List<Student> stdList = management.getFilteredStudents(management.filterStudentsByMatriculationNumber(22222));
		assertEquals("Student: 22222 - Peter Clark",stdList.get(0).toString());
		assertEquals(1,stdList.size());
		assertEquals("Peter",stdList.get(0).getFirstName());
		assertEquals("Clark",stdList.get(0).getLastName());
		assertEquals(22222,stdList.get(0).getMatriculationNumber());
		assertEquals("Computer Science B.Ed.",stdList.get(0).getCourseOfStudies());
		
		stdList =  management.getFilteredStudents(management.filterStudentsByMatriculationNumber(12446));
		assertEquals(0,stdList.size());
		
		stdList =  management.getFilteredStudents(management.filterStudentsByMatriculationNumber(33333));
		assertEquals(1,stdList.size());
		assertEquals("Taylor",stdList.get(0).getFirstName());
		assertEquals("Smith",stdList.get(0).getLastName());
		assertEquals(33333,stdList.get(0).getMatriculationNumber());
		assertEquals("Computer Science B.Sc.",stdList.get(0).getCourseOfStudies());
		assertEquals("Student: 33333 - Taylor Smith",stdList.get(0).toString());
	}
	
	@Test
	public void Test_getFilteredStudents_filterStudentsByCourseOfStudies(){
		List<Student> stdList = management.getFilteredStudents(management.filterStudentsByCourseOfStudies("COMPUTER science B.ED."));
		assertEquals("Student: 22222 - Peter Clark",stdList.get(0).toString());
		assertEquals(1,stdList.size());
		assertEquals("Peter",stdList.get(0).getFirstName());
		assertEquals("Clark",stdList.get(0).getLastName());
		assertEquals(22222,stdList.get(0).getMatriculationNumber());
		assertEquals("Computer Science B.Ed.",stdList.get(0).getCourseOfStudies());
		
		stdList =  management.getFilteredStudents(management.filterStudentsByCourseOfStudies(" Informatik :) "));
		assertEquals(0,stdList.size());
		
		stdList =  management.getFilteredStudents(management.filterStudentsByCourseOfStudies("ComputeR Science B.Sc."));
		assertEquals(3,stdList.size());
		assertEquals("Student: 11111 - John Doe",stdList.get(0).toString());
		assertEquals("Student: 33333 - Taylor Smith",stdList.get(1).toString());
		assertEquals("Student: 44444 - Anna Williams",stdList.get(2).toString());
	}
	
	@Test
	public void Test_getFilteredExaminations_filterExaminationsByName(){
		List<Examination> exmList = 
				management.getFilteredExaminations(management.filterExaminationsByName
						("Functional and Object-oriented Programming Concepts "));
		assertEquals(1,exmList.size());
		assertEquals("Functional and Object-oriented Programming Concepts ",exmList.get(0).getName());
		assertEquals(10,exmList.get(0).getCreditPoints());
		assertEquals(0,exmList.get(0).getStudentsRegistered().size());
		assertEquals("WiSe_17_18",exmList.get(0).getSemester().toString());
		assertEquals("2018-04-02T13:30",exmList.get(0).getDateBegin().toString());
		assertEquals("2018-04-02T15:00",exmList.get(0).getDateEnd().toString());
	
		exmList =  management.getFilteredExaminations(management.filterExaminationsByName("Digital Technik"));
		assertEquals(0,exmList.size());
		
		exmList =  management.getFilteredExaminations(management.filterExaminationsByName("VISual Computing"));
		assertEquals(1,exmList.size());
		assertEquals("Visual Computing",exmList.get(0).getName());
		assertEquals(5,exmList.get(0).getCreditPoints());
		assertEquals(0,exmList.get(0).getStudentsRegistered().size());
		assertEquals("WiSe_17_18",exmList.get(0).getSemester().toString());
		assertEquals("2018-03-01T09:00",exmList.get(0).getDateBegin().toString());
		assertEquals("2018-03-01T11:30",exmList.get(0).getDateEnd().toString());
		}
	
	@Test
	public void Test_getFilteredExaminations_filterExaminationsByCreditPoints(){
		List<Examination> exmList = 
				management.getFilteredExaminations(management.filterExaminationsByCreditPoints(5));
		assertEquals(3,exmList.size());
		assertEquals("Visual Computing",exmList.get(0).getName());
		assertEquals("Computer Security",exmList.get(1).getName());
		assertEquals("Software Engineering",exmList.get(2).getName());
		assertEquals(5,exmList.get(1).getCreditPoints());
		assertEquals(3,exmList.get(2).getStudentsRegistered().size());
		assertEquals("WiSe_17_18",exmList.get(1).getSemester().toString());
		assertEquals("SoSe_17",exmList.get(2).getSemester().toString());

		exmList =  management.getFilteredExaminations(management.filterExaminationsByCreditPoints(8));
		assertEquals(0,exmList.size());
		
		exmList =  management.getFilteredExaminations(management.filterExaminationsByCreditPoints(9));
		assertEquals(2,exmList.size());
		assertEquals("Mathematics I",exmList.get(0).getName());
		assertEquals("Mathematics II",exmList.get(1).getName());
		assertEquals(9,exmList.get(0).getCreditPoints());
		assertEquals(4,exmList.get(0).getStudentsRegistered().size());
		assertEquals(3,exmList.get(1).getStudentsRegistered().size());
		assertEquals("WiSe_16_17",exmList.get(1).getSemester().toString());
		assertEquals("SoSe_17",exmList.get(0).getSemester().toString());

	}
	
	@Test
	public void Test_getFilteredExaminations_filterExaminationsBySemester(){
		List<Examination> exmList = 
				management.getFilteredExaminations(management.filterExaminationsBySemester(Semester.WiSe_17_18));

		assertEquals(3,exmList.size());
		assertEquals("Visual Computing",exmList.get(0).getName());
		assertEquals(5,exmList.get(0).getCreditPoints());
		assertEquals(0,exmList.get(0).getStudentsRegistered().size());
		assertEquals("WiSe_17_18",exmList.get(0).getSemester().toString());
		assertEquals("2018-03-01T09:00",exmList.get(0).getDateBegin().toString());
		assertEquals("2018-03-01T11:30",exmList.get(0).getDateEnd().toString());
		
		assertEquals("Functional and Object-oriented Programming Concepts ",exmList.get(1).getName());
		assertEquals(10,exmList.get(1).getCreditPoints());
		assertEquals(0,exmList.get(1).getStudentsRegistered().size());
		assertEquals("WiSe_17_18",exmList.get(1).getSemester().toString());
		assertEquals("2018-04-02T13:30",exmList.get(1).getDateBegin().toString());
		assertEquals("2018-04-02T15:00",exmList.get(1).getDateEnd().toString());		
		
		assertEquals("Computer Security",exmList.get(2).getName());
		assertEquals(5,exmList.get(2).getCreditPoints());
		assertEquals(0,exmList.get(2).getStudentsRegistered().size());
		assertEquals("WiSe_17_18",exmList.get(2).getSemester().toString());

		exmList =  management.getFilteredExaminations(management.filterExaminationsBySemester(Semester.SoSe_16));
		assertEquals(0,exmList.size());
		
		exmList =  management.getFilteredExaminations(management.filterExaminationsBySemester(Semester.WiSe_16_17));
		assertEquals(1,exmList.size());
		assertEquals("Mathematics II",exmList.get(0).getName());
		assertEquals(3,exmList.get(0).getStudentsRegistered().size());
		assertEquals("WiSe_16_17",exmList.get(0).getSemester().toString());
	}
	
	@Test
	public void Test_getFilteredGrades_filterGradesByGrade(){
		
		List<ExaminationGrade> ExmGrList = math2.getFilteredGrades(math2.filterGradesByGrade(2.0));

		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(0).getGrade()));
		assertEquals("John",ExmGrList.get(0).getStudent().getFirstName());

		ExmGrList = math1.getFilteredGrades(math1.filterGradesByGrade(2.0));
		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(0).getGrade()));
		assertEquals("John",ExmGrList.get(0).getStudent().getFirstName());

		ExmGrList = math1.getFilteredGrades(math1.filterGradesByGrade(1.3));
		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(1.3),new Double(ExmGrList.get(0).getGrade()));

		
		ExmGrList = se.getFilteredGrades(se.filterGradesByGrade(2.0));
		assertEquals(2,ExmGrList.size());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(0).getGrade()));
		assertEquals("John",ExmGrList.get(0).getStudent().getFirstName());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(1).getGrade()));
		assertEquals("Taylor",ExmGrList.get(1).getStudent().getFirstName());

		ExmGrList = se.getFilteredGrades(se.filterGradesByGrade(1.7));
		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(1.7),new Double(ExmGrList.get(0).getGrade()));
	}
	
	@Test
	public void Test_getFilteredGrades_filterGradesByStudent(){
		List<ExaminationGrade> ExmGrList = math2.getFilteredGrades(math2.filterGradesByStudent(johnDoe));

		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(0).getGrade()));
		assertEquals("John",ExmGrList.get(0).getStudent().getFirstName());

		ExmGrList = math1.getFilteredGrades(math1.filterGradesByStudent(johnDoe));
		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(0).getGrade()));
		assertEquals("John",ExmGrList.get(0).getStudent().getFirstName());

		ExmGrList = math1.getFilteredGrades(math1.filterGradesByStudent(taylorSmith));
		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(1.3),new Double(ExmGrList.get(0).getGrade()));

		
		ExmGrList = se.getFilteredGrades(se.filterGradesByStudent(johnDoe));
		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(0).getGrade()));
		assertEquals("John",ExmGrList.get(0).getStudent().getFirstName());

		ExmGrList = se.getFilteredGrades(se.filterGradesByStudent(taylorSmith));
		assertEquals(1,ExmGrList.size());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(0).getGrade()));
		
		ExmGrList = fop.getFilteredGrades(fop.filterGradesByStudent(peterClark));
		assertEquals(0,ExmGrList.size());

		ExmGrList = cs.getFilteredGrades(cs.filterGradesByStudent(annaWilliams));
		assertEquals(0,ExmGrList.size());

	}
	
	@Test
	public void getAverageGrade(){
		Examination exm = management.getFilteredExaminations(management.filterExaminationsBySemester(Semester.WiSe_16_17)).get(0);
		
		List<ExaminationGrade> ExmGrList = exm.getGrades();
		
		assertEquals(3,ExmGrList.size());
		assertEquals(new Double(2.0),new Double(ExmGrList.get(0).getGrade()));
		assertEquals(new Double(3.7),new Double(ExmGrList.get(1).getGrade()));
		assertEquals(new Double(1.7),new Double(ExmGrList.get(2).getGrade()));
		
		assertEquals(new Double(7.4/3),new Double(exm.getAverageGrade()));

		ExmGrList = se.getGrades();
		assertEquals(3,ExmGrList.size());
		assertEquals(new Double(2),new Double(ExmGrList.get(0).getGrade()));
		assertEquals(new Double(1.7),new Double(ExmGrList.get(1).getGrade()));
		assertEquals(new Double(2),new Double(ExmGrList.get(2).getGrade()));
		
		assertEquals(new Double(5.7/3),new Double(se.getAverageGrade()));
	}
	
	@Test
	public void getDistributionOfGrades(){
		Map<Double, Integer> NV = new HashMap<Double, Integer>();
		
		NV = se.getDistributionOfGrades();
		assertEquals("{2.0=2, 1.7=1}",NV.toString());
		assertEquals(2,NV.size());
		assertEquals(new Integer(2),NV.get(2.0));
		assertEquals(new Integer(1),NV.get(1.7));

		NV = math1.getDistributionOfGrades();
		assertEquals("{2.0=1, 4.0=1, 1.0=1, 1.3=1}",NV.toString());
		assertEquals(4,NV.size());
		assertEquals(new Integer(1),NV.get(2.0));
		assertEquals(null,NV.get(1.7));
		
		NV = math2.getDistributionOfGrades();
		assertEquals("{2.0=1, 3.7=1, 1.7=1}",NV.toString());
		assertEquals(3,NV.size());
		assertEquals(new Integer(1),NV.get(2.0));
		assertEquals(new Integer(1),NV.get(1.7));
		assertEquals(new Integer(1),NV.get(3.7));
		
		NV = fop.getDistributionOfGrades();
		assertEquals(0,NV.size());
		assertEquals(null,NV.get(1.7));
	}
}
