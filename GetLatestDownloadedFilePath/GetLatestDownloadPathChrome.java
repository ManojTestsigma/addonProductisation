package org.example.enhancementseleniumscripts.GetLatestDownloadedFilePath;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
public class GetLatestDownloadPathChrome {

    public static void main(String[] args) throws InterruptedException {

        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();

        try {

            driver.navigate().to("https://sample-files.com/documents/txt/");

            WebElement element = driver.findElement(By.xpath("//*[@id=\"post-178\"]/div/div/p[3]/a"));
            element.click();

            Thread.sleep(3000);
            // Navigate to the Chrome downloads page
            driver.get("chrome://downloads/all");

            try {
                String filePath = (String) ((JavascriptExecutor) driver).executeScript("return document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList').items[0].filePath;");
                if (filePath != null && !filePath.isEmpty()) {
                    System.out.println("Latest file path: " + filePath);
                } else {
                    System.out.println("File path not found.");
                }
            } catch (Exception e) {
                System.out.println("Error using JavaScript method: " + e.getMessage());
            }

        } finally {
            // Close the browser
            driver.quit();
        }
    }
}






