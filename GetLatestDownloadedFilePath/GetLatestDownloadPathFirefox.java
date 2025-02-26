package org.example.enhancementseleniumscripts.GetLatestDownloadedFilePath;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class GetLatestDownloadPathFirefox {

    public static void main(String[] args) {

        WebDriver driver = new FirefoxDriver();

        try {
            driver.navigate().to("https://sample-files.com/documents/txt/");
            WebElement element = driver.findElement(By.xpath("//*[@id=\"post-178\"]/div/div/p[3]/a"));
            element.click();
            driver.get("about:downloads");

            Thread.sleep(Duration.ofSeconds(10).toMillis());

            try {
                String filePath = (String) ((JavascriptExecutor) driver).executeScript(
                        "function getLatestDownloadedFilePath() {" +
                                "    const richListBox = document.querySelector('richlistbox#downloadsListBox');" +
                                "    if (!richListBox) {" +
                                "        return null;" +
                                "    }" +
                                "    const latestDownloadItem = richListBox.querySelector('richlistitem');" +
                                "    if (!latestDownloadItem) {" +
                                "        return null;" +
                                "    }" +
                                "    const downloadTypeIcon = latestDownloadItem.querySelector('image.downloadTypeIcon');" +
                                "    if (!downloadTypeIcon) {" +
                                "        return null;" +
                                "    }" +
                                "    const filePath = downloadTypeIcon.getAttribute('src');" +
                                "    if (!filePath) {" +
                                "        return null;" +
                                "    }" +
                                "    const filePathFromUrl = filePath.split('?')[0].replace('moz-icon://', '');" +
                                "    return filePathFromUrl;" +
                                "}" +
                                "return getLatestDownloadedFilePath();");

                System.out.println("File path: " + filePath);

                if (filePath != null && !filePath.isEmpty()) {
                    System.out.println("Latest file path: " + filePath);
                } else {
                    System.out.println("File path not found.");
                }
            } catch (Exception e) {
                System.out.println("Error using JavaScript method: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();

        } finally {
            driver.quit();
        }
    }
}