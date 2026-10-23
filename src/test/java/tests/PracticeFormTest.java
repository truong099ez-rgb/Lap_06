package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.RegistrationPage;

import java.io.File;
import java.time.Duration;

public class PracticeFormTest {
    private WebDriver driver;
    private RegistrationPage registrationPage;
    private final String URL = "https://demoqa.com/automation-practice-form";

    @BeforeMethod
    public void setUp() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        registrationPage = new RegistrationPage(driver);
    }

    @Test(dataProvider = "studentDataProvider", dataProviderClass = TestData.class)
    public void testStudentRegistrationForm(String firstName, String lastName, String email, String mobile, String subject, String address) {
        // 1. Mở trang và kiểm tra tiêu đề, URL
        registrationPage.openPage(URL);
        Assert.assertEquals(registrationPage.getCurrentUrl(), URL, "URL không khớp!");
        Assert.assertFalse(registrationPage.getPageTitle().isEmpty(), "Tiêu đề trang bị rỗng!");
        System.out.println("=== Bắt đầu test case với sinh viên: " + firstName + " " + lastName + " ===");

        // 2 & 3. Điền thông tin cá nhân
        registrationPage.enterPersonalDetails(firstName, lastName, email, mobile);
        registrationPage.selectGenderMale();
        registrationPage.enterSubject(subject);
        registrationPage.selectHobbies();

        // 4. Tải tệp lên form (Tạo file text mẫu tự động để upload)
        try {
            File file = new File("src/test/resources/sample.txt");
            file.getParentFile().mkdirs();
            if (!file.exists()) {
                file.createNewFile();
            }
            registrationPage.uploadFile(file.getAbsolutePath());
        } catch (Exception e) {
            System.out.println("Không tạo được file mẫu upload: " + e.getMessage());
        }

        registrationPage.enterAddress(address);

        // Chụp màn hình TRƯỚC khi submit
        registrationPage.captureScreen("BeforeSubmit_" + firstName);

        // 5 & 8. Nhấn Submit và kiểm tra thông báo xác nhận
        registrationPage.clickSubmit();

        // Chụp màn hình SAU khi submit
        registrationPage.captureScreen("AfterSubmit_" + firstName);

        // Xác thực modal thông báo thành công xuất hiện
        boolean isModalDisplayed = registrationPage.isConfirmationModalDisplayed();
        Assert.assertTrue(isModalDisplayed, "Modal xác nhận không hiển thị sau khi submit!");
        System.out.println("Kết quả: Kiểm thử thành công cho " + firstName + "\n");

        registrationPage.closeModal();
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}