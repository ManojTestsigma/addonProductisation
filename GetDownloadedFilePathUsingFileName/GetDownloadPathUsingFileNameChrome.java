package org.example.enhancementseleniumscripts.GetDownloadedFilePathUsingFileName;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.Map;

public class GetDownloadPathUsingFileNameChrome {

    public static void main(String[] args) throws InterruptedException {

        // Initialize the ChromeDriver
        WebDriver driver = new ChromeDriver();
        String targetFileName = "simple.txt"; // Replace with your desired file name

        try {
            // Download the file twice to ensure it's present in downloads and to trigger the download twice

            String downloadURL = "https://drive.usercontent.google.com/download?id=1szYMoC4D-I5uZqiovJ5IOSwh8jVOVpmK&export=download&authuser=0&confirm=t&uuid=770e07f0-0783-4b5c-9c09-b36741378e9f&at=AEz70l58Um2in724t0ss8cJtPG7k:1740249548154";

            driver.get(downloadURL); //Initiate the download
            Thread.sleep(3000); //Wait for download to start

            driver.get(downloadURL); //Initiate the download again to see the downloaded history

            Thread.sleep(3000); //Wait for download to start

            // Navigate to the Chrome downloads page
            driver.get("chrome://downloads/all");

            try {
                // Execute JavaScript to get the list of download items
                List<Map<String, Object>> downloadItems = (List<Map<String, Object>>) ((JavascriptExecutor) driver).executeScript(
                        "return Array.from(document.querySelector('downloads-manager').shadowRoot.querySelector('#downloadsList').items).map(item => ({fileName: item.fileName, filePath: item.filePath}));"
                );

                String filePath = null;
                for (Map<String, Object> item : downloadItems) {
                    String fileName = (String) item.get("fileName");
                    if (fileName != null && fileName.equals(targetFileName)) {
                        filePath = (String) item.get("filePath");
                        break; // Found the file, no need to continue searching
                    }
                }

                if (filePath != null && !filePath.isEmpty()) {
                    System.out.println("File path for " + targetFileName + ": " + filePath);
                } else {
                    System.out.println("File " + targetFileName + " not found in downloads.");
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