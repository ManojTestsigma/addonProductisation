package org.example.enhancementseleniumscripts.GetDownloadedFilePathUsingFileName;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class GetDownloadPathUsingFileNameEdge {

    public static void main(String[] args) {

        WebDriver driver = new EdgeDriver();
        String targetFileName = "simple (21).txt"; // Replace with your desired file name

        try {
            driver.get("https://drive.google.com/uc?id=1S33kgW9JZ_Rv1XWJWnxBJ8S9ST_bOuP-&export=download");
            driver.navigate().to("https://sample-files.com/documents/txt/");
            WebElement element = driver.findElement(By.xpath("//*[@id=\"post-178\"]/div/div/p[3]/a"));
            element.click();

            driver.get("edge://downloads");

            Thread.sleep(Duration.ofSeconds(10).toMillis());

            try {
                String script = "return (function(targetFileName) {" +
                        "    let pdfPath = null;" +
                        "    try {" +
                        "        let downloadItems = Array.from(document.querySelector(\"body > downloads-app\")?.shadowRoot?.querySelectorAll(\"edge-card\") || []);" +
                        "        for (const downloadItem of downloadItems) {" +
                        "            const fileNameElement = downloadItem.querySelector(\".downloads_itemTitle\");" +
                        "            if (fileNameElement) {" +
                        "                const fileName = fileNameElement.textContent.trim();" +
                        "                if (fileName.toLowerCase() === targetFileName.toLowerCase()) {" +
                        "                    const filePath = downloadItem.querySelector('.downloads_itemIconContainer > img')?.getAttribute('src');" +
                        "                    if (filePath) {" +
                        "                        try {" +
                        "                            const decodedPath = decodeURIComponent(filePath);" +
                        "                            const match = decodedPath.match(/path=(.*)&scale/);" +
                        "                            if (match && match[1]) {" +
                        "                                pdfPath = match[1].replace(/\\+/g, ' ');" +
                        "                                pdfPath = pdfPath.replace(/%20/g, ' ');" +
                        "                                return pdfPath;" +
                        "                            }" +
                        "                        } catch (decodeError) {" +
                        "                            console.error('Error decoding file path:', decodeError);" +
                        "                        }" +
                        "                    }" +
                        "                }" +
                        "            }" +
                        "        }" +
                        "        let downloadItemsLegacy = document.querySelectorAll('div[role=\"listitem\"]');" +
                        "        for (const item of downloadItemsLegacy) {" +
                        "            let fileNameElement = item.querySelector('button[aria-label]');" +
                        "            if (fileNameElement) {" +
                        "                let fileName = fileNameElement.getAttribute('aria-label');" +
                        "                if (fileName && fileName.toLowerCase() === targetFileName.toLowerCase()) {" +
                        "                    let img = item.querySelector('img');" +
                        "                    if (img) {" +
                        "                        let src = img.getAttribute('src');" +
                        "                        if (src) {" +
                        "                            try {" +
                        "                                pdfPath = decodeURIComponent(src.split('path=')[1].split('&')[0]).replace(/\\+/g, ' ');" +
                        "                                pdfPath = pdfPath.replace(/%20/g, ' '); " +
                        "                                return pdfPath;" +
                        "                            } catch (e) {" +
                        "                                console.error('Error extracting or decoding file path:', e);" +
                        "                            }" +
                        "                        }" +
                        "                    }" +
                        "                }" +
                        "            }" +
                        "        }" +
                        "    } catch (error) {" +
                        "        console.error('Error during PDF path retrieval:', error);" +
                        "    }" +
                        "    return pdfPath || null;" +
                        "})('" + targetFileName + "');";

                JavascriptExecutor js = (JavascriptExecutor) driver;
                Object obj = js.executeScript(script);

                if (obj == null) {
                    System.out.println("There is no file with name: " + targetFileName);
                }

                System.out.println("Found PDF file path: " + obj.toString());
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