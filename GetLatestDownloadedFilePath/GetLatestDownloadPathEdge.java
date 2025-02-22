package org.example.enhancementseleniumscripts.GetLatestDownloadedFilePath;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class GetLatestDownloadPathEdge {

    public static void main(String[] args) {

        WebDriver driver = new EdgeDriver();

        try {
            driver.navigate().to("https://sample-files.com/documents/txt/");
            WebElement element = driver.findElement(By.xpath("//*[@id=\"post-178\"]/div/div/p[3]/a"));
            element.click();
            driver.get("edge://downloads/all");

            Thread.sleep(Duration.ofSeconds(10).toMillis());

            try {
                String filePath = (String) ((JavascriptExecutor) driver).executeScript(
                        "function getLatestDownloadedFilePath() {" +
                                "    const shadowRootImgElement = document.querySelector(\"body > downloads-app\")?.shadowRoot.querySelector(\"#downloads-item-1 > div.downloads_itemIconContainer.iDoExist > img\");" +
                                "" +
                                "    if (shadowRootImgElement) {" +
                                "        const filePath = new URL(shadowRootImgElement.src).searchParams.get('path');" +
                                "        if (filePath) {" +
                                "            return decodeURIComponent(filePath);" +
                                "        }" +
                                "    } " +
                                "    const latestFileItem = document.querySelector('.downloads-list [role=\"listitem\"]');" +
                                "" +
                                "    if (latestFileItem) {" +
                                "        const fileIconImg = latestFileItem.querySelector('img');" +
                                "        if (fileIconImg) {" +
                                "            const filePath = new URL(fileIconImg.src).searchParams.get('path');" +
                                "            if (filePath) {" +
                                "                return decodeURIComponent(filePath);" +
                                "            }" +
                                "        }" +
                                "    } " +
                                "    return null;" +
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
