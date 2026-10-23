package tests;

import org.testng.annotations.DataProvider;

public class TestData {
    @DataProvider(name = "studentDataProvider")
    public Object[][] getStudentData() {
        return new Object[][] {
            { "Nguyen", "An", "an.nguyen@gmail.com", "0901234567", "Maths", "123 Le Loi, TP.HCM" },
            { "Tran", "Binh", "binh.tran@gmail.com", "0912345678", "English", "456 Nguyen Hue, TP.HCM" },
            { "Le", "Chi", "chi.le@gmail.com", "0923456789", "Computer Science", "789 Le Duan, TP.HCM" }
        };
    }
}