package pages;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RegistrationPage {
    private WebDriver driver;
    private WebDriverWait wait;

    // Định nghĩa các Locator trên trang DemoQA Practice Form[cite: 4, 5]
    private By firstNameField = By.id("firstName");
    private By lastNameField = By.id("lastName");
    private By emailField = By.id("userEmail");
    private By genderMaleRadio = By.xpath("//label[@for='gender-radio-1']");
    private By mobileField = By.id("userNumber");
    private By subjectsInput = By.id("subjectsInput");
    private By hobbiesSportsCheckbox = By.xpath("//label[@for='hobbies-checkbox-1']");
    private By uploadPictureBtn = By.id("uploadPicture");
    private By currentAddressField = By.id("currentAddress");
    private By submitBtn = By.id("submit");
    private By modalContent = By.className("modal-content");
    private By closeLargeModalBtn = By.id("closeLargeModal");

    public RegistrationPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void openPage(String url) {
        driver.get(url);
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // Nhập thông tin cá nhân[cite: 5]
    public void enterPersonalDetails(String firstName, String lastName, String email, String mobile) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameField)).sendKeys(firstName);
        driver.findElement(lastNameField).sendKeys(lastName);
        driver.findElement(emailField).sendKeys(email);
        driver.findElement(mobileField).sendKeys(mobile);
        System.out.println("Đã nhập thông tin: " + firstName + " " + lastName + " | Email: " + email);
    }

    public void selectGenderMale() {
        driver.findElement(genderMaleRadio).click();
    }

    public void enterSubject(String subject) {
        WebElement sub = wait.until(ExpectedConditions.elementToBeClickable(subjectsInput));
        sub.sendKeys(subject);
        sub.sendKeys(Keys.ENTER);
    }

    public void selectHobbies() {
        WebElement hobby = driver.findElement(hobbiesSportsCheckbox);
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", hobby);
        hobby.click();
    }

    public void uploadFile(String filePath) {
        driver.findElement(uploadPictureBtn).sendKeys(filePath);
    }

    public void enterAddress(String address) {
        driver.findElement(currentAddressField).sendKeys(address);
    }

    public void clickSubmit() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(submitBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", btn);
        btn.click();
    }

    // Sử dụng Explicit Wait kiểm tra modal xác nhận xuất hiện[cite: 4, 6]
    public boolean isConfirmationModalDisplayed() {
        try {
            WebElement modal = wait.until(ExpectedConditions.visibilityOfElementLocated(modalContent));
            return modal.isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    public void closeModal() {
        try {
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(closeLargeModalBtn));
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", closeBtn);
        } catch (Exception e) {
            // Bỏ qua nếu không click được
        }
    }

    // Hàm chụp màn hình dựa theo mẫu code chuẩn trong tài liệu PDF
    public void captureScreen(String prefix) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            File dest = new File("screenshots/" + prefix + "_" + time + ".png");
            FileUtils.copyFile(src, dest);
            System.out.println("Đã chụp màn hình thành công lưu tại: " + dest.getPath());
        } catch (IOException e) {
            System.out.println("Lỗi khi chụp màn hình: " + e.getMessage());
        }
    }
}