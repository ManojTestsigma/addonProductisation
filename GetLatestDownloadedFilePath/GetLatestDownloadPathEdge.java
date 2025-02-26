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
            driver.get("https://drive.google.com/uc?id=1S33kgW9JZ_Rv1XWJWnxBJ8S9ST_bOuP-&export=download");
            driver.navigate().to("https://sample-files.com/documents/txt/");
            WebElement element = driver.findElement(By.xpath("//*[@id=\"post-178\"]/div/div/p[3]/a"));
            element.click();
            driver.get("edge://downloads");

            Thread.sleep(Duration.ofSeconds(10).toMillis());

            try {
                JavascriptExecutor js = (JavascriptExecutor) driver;

                String script = "return (function() {" +
                        "    let filePath = null;" +
                        "    try {" +
                        "        let downloadItems = Array.from(document.querySelector(\"body > downloads-app\")?.shadowRoot?.querySelectorAll(\"edge-card\") || []);" +
                        "        for (const downloadItem of downloadItems) {" +
                        "            const fileNameElement = downloadItem.querySelector(\".downloads_itemTitle\");" +
                        "            if (fileNameElement) {" +
                        "                const filePathElement = downloadItem.querySelector('.downloads_itemIconContainer > img')?.getAttribute('src');" +
                        "                if (filePathElement) {" +
                        "                    try {" +
                        "                        const decodedPath = decodeURIComponent(filePathElement);" +
                        "                        const match = decodedPath.match(/path=(.*)&scale/);" +
                        "                        if (match && match[1]) {" +
                        "                            filePath = match[1].replace(/\\+/g, ' ');" +
                        "                            return filePath;" +
                        "                        }" +
                        "                    } catch (decodeError) { console.error('Error decoding file path:', decodeError); }" +
                        "                }" +
                        "            }" +
                        "        }" +

                        "        let downloadItemsLegacy = document.querySelectorAll('div[role=\"listitem\"]');" +
                        "        for (const item of downloadItemsLegacy) {" +
                        "            let fileNameElement = item.querySelector('button[aria-label]');" +
                        "            if (fileNameElement) {" +
                        "                let fileName = fileNameElement.getAttribute('aria-label');" +
                        "                if (fileName) {" +
                        "                    let img = item.querySelector('img');" +
                        "                    if (img) {" +
                        "                        let src = img.getAttribute('src');" +
                        "                        if (src) {" +
                        "                            try {" +
                        "                                filePath = decodeURIComponent(src.split('path=')[1].split('&')[0]).replace(/\\+/g, ' ');" +
                        "                                return filePath;" +
                        "                            } catch (e) { console.error('Error extracting or decoding file path:', e); }" +
                        "                        }" +
                        "                    }" +
                        "                }" +
                        "            }" +
                        "        }" +
                        "    } catch (error) {" +
                        "        console.error('Error during file path retrieval:', error);" +
                        "    }" +
                        "    return filePath || null;" +
                        "})();";


                Object result = js.executeScript(script);
                if (result != null) {
                    String resultString = result.toString();
                    System.out.println("Found PDF file path: " + resultString);
                } else {
                    System.out.println("No PDF file found in downloads or an error occurred.");
                }

            } catch (Exception e) {
                System.err.println("An error occurred: " + e.getMessage());
                e.printStackTrace();

            } finally {
                driver.quit();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}



