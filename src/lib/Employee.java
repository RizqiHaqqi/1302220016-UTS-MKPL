package lib;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private static final int SALARY_GRADE_1 = 3000000;
	private static final int SALARY_GRADE_2 = 5000000;
	private static final int SALARY_GRADE_3 = 7000000;
	private static final double FOREIGNER_MULTIPLIER = 1.5;

	private String employeeId;
	private String firstName;
	private String lastName;
	private String idNumber;
	private String address;
	
	private LocalDate joinDate;
	private boolean isForeigner;
	private boolean isMale;
	
	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private Spouse spouse;
	private List<Child> children;

	private List<String> childNames;
	private List<String> childIdNumbers;
	
	public Employee(String employeeId, String firstName, String lastName, String idNumber, String address, int yearJoined, int monthJoined, int dayJoined, boolean isForeigner, boolean isMale) {
		this.employeeId = employeeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.idNumber = idNumber;
		this.address = address;
		this.joinDate = LocalDate.of(yearJoined, monthJoined, dayJoined);
		this.isForeigner = isForeigner;
		this.isMale = isMale;
		
		this.children = new ArrayList<>();
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {	
		switch (grade) {
			case 1 : monthlySalary = SALARY_GRADE_1;
			case 2 : monthlySalary = SALARY_GRADE_2;
			case 3 : monthlySalary = SALARY_GRADE_3;
			default : throw new IllegalArgumentException("invalid grade: " + grade);
		}
	}
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouse(String spouseName, String spouseIdNumber) {
		this.spouse = new Spouse(spouseName, spouseIdNumber);
	}
	
	public void addChild(String childName, String childIdNumber) {
		childNames.add(childName);
		childIdNumbers.add(childIdNumber);
	}
	
	public int getAnnualIncomeTax() {
		
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		int monthWorkingInYear = calculateMonthsWorkedInYear();
		boolean hasSpouse = spouse != null;
		int numberOfChildren = children.size();
		
		return TaxFunction.calculateTax(monthlySalary, otherMonthlyIncome, monthWorkingInYear, annualDeductible, !hasSpouse, numberOfChildren);
	}

	public int calculateMonthsWorkedInYear(){
		LocalDate now = LocalDate.now();
		if(now.getYear() == joinDate.getYear()){
			return now.getMonthValue() - joinDate.getMonthValue();
		} else {
			return 12;	
		}
	}

	private static class Spouse {
        private String name;
        private String idNumber;

        public Spouse(String name, String idNumber) {
            this.name = name;
            this.idNumber = idNumber;
        }
    }

	private static class Child {
        private String name;
        private String idNumber;

        public Child(String name, String idNumber) {
            this.name = name;
            this.idNumber = idNumber;
        }
    }
}
