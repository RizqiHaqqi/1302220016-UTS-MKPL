package lib;

public class TaxFunction {

	private static final int BASIC_PTKP = 54000000;
    private static final int MARRIED_PTKP = 4500000;
    private static final int CHILD_PTKP = 1500000;
    private static final double TAX_RATE = 0.05;
    private static final int MAX_CHILDREN = 3;
    private static final int MAX_MONTHS = 12;

	/**
	 * Fungsi untuk menghitung jumlah pajak penghasilan pegawai yang harus dibayarkan setahun.
	 * 
	 * Pajak dihitung sebagai 5% dari penghasilan bersih tahunan (gaji dan pemasukan bulanan lainnya dikalikan jumlah bulan bekerja dikurangi pemotongan) dikurangi penghasilan tidak kena pajak.
	 * 
	 * Jika pegawai belum menikah dan belum punya anak maka penghasilan tidak kena pajaknya adalah Rp 54.000.000.
	 * Jika pegawai sudah menikah maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000.
	 * Jika pegawai sudah memiliki anak maka penghasilan tidak kena pajaknya ditambah sebesar Rp 4.500.000 per anak sampai anak ketiga.
	 * 
	 */
	
	
	public static int calculateTax(int monthlySalary, int otherMonthlyIncome, int numberOfMonthWorking, int deductible, boolean isSingle, int numberOfChildren) {
		
		if (numberOfMonthWorking > MAX_MONTHS) {
			throw new IllegalArgumentException("jumlah bulan bekerja tidak boleh lebih dari 12") ;
		}
		
		numberOfChildren = Math.min(numberOfChildren, MAX_CHILDREN);
		int ptkp = BASIC_PTKP;
        if (!isSingle) {
            ptkp += MARRIED_PTKP + (numberOfChildren * CHILD_PTKP);
        }

        int annualIncome = (monthlySalary + otherMonthlyIncome) * numberOfMonthWorking;
        int taxableIncome = annualIncome - deductible - ptkp;

        if (taxableIncome <= 0) {
            return 0;
        }

        return (int) Math.round(TAX_RATE * taxableIncome);
    }
	
}
