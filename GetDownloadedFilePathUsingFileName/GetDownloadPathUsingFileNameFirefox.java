package org.example.enhancementseleniumscripts.GetDownloadedFilePathUsingFileName;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class GetDownloadPathUsingFileNameFirefox {

    public static void main(String[] args) {
        // Initialize the WebDriver
        WebDriver driver = new FirefoxDriver();

        // Define the file name to search for
        String fileNameToSearch = "simple(13).txt";

        try {
            // Navigate to the sample files page
            driver.navigate().to("https://sample-files.com/documents/txt/");
            // Click the download button for the desired file
            WebElement element = driver.findElement(By.xpath("//*[@id=\"post-178\"]/div/div/p[3]/a"));
            element.click();
            // Navigate to the Firefox downloads page
            Thread.sleep(3000);

            element.click();

            driver.get("about:downloads");

            // Wait for the downloads to appear
            Thread.sleep(Duration.ofSeconds(10).toMillis());

            // Execute the JavaScript function to get the file path
            String filePath = (String) ((JavascriptExecutor) driver).executeScript(
                    "function getDownloadedFilePath(fileName) {" +
                            "    const richListBox = document.querySelector('richlistbox#downloadsListBox');" +
                            "    if (!richListBox) {" +
                            "        return 'richlistbox element not found';" +
                            "    }" +
                            "    const downloadItems = richListBox.querySelectorAll('richlistitem');" +
                            "    for (let i = 0; i < downloadItems.length; i++) {" +
                            "        const downloadItem = downloadItems[i];" +
                            "        const description = downloadItem.querySelector('.downloadTarget');" +
                            "        if (description && description.getAttribute('value') === fileName) {" +
                            "            const downloadTypeIcon = downloadItem.querySelector('image.downloadTypeIcon');" +
                            "            if (downloadTypeIcon) {" +
                            "                const filePath = downloadTypeIcon.getAttribute('src');" +
                            "                if (filePath) {" +
                            "                    return filePath.split('?')[0].replace('moz-icon://', '');" +
                            "                }" +
                            "            }" +
                            "        }" +
                            "    }" +
                            "    return 'file name not found';" +
                            "}" +
                            "return getDownloadedFilePath(arguments[0]);", fileNameToSearch);

            // Print the result
            System.out.println("File path: " + filePath);

            if (filePath != null && !filePath.isEmpty() && !filePath.equals("file name not found")) {
                System.out.println("Latest file path: " + filePath);
            } else {
                System.out.println("File name not found.");
            }
        } catch (Exception e) {
            System.err.println("An error occurred: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Close the WebDriver
            driver.quit();
        }
    }
}