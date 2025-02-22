package org.example.enhancementseleniumscripts.GetDownloadedFilePathUsingFileName;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class GetDownloadPathUsingFileNameEdge {

    public static void main(String[] args) {

        WebDriver driver = new EdgeDriver();
        String targetFileName = "simple (2).txt"; // Replace with your desired file name

        try {
            // Download the file twice to ensure it's present in downloads and to trigger the download twice

            String downloadURL = "https://drive.usercontent.google.com/download?id=1szYMoC4D-I5uZqiovJ5IOSwh8jVOVpmK&export=download&authuser=0&confirm=t&uuid=770e07f0-0783-4b5c-9c09-b36741378e9f&at=AEz70l58Um2in724t0ss8cJtPG7k:1740249548154";

            driver.get(downloadURL); //Initiate the download
            Thread.sleep(3000); //Wait for download to start

            driver.get(downloadURL); //Initiate the download again to see the downloaded history

            Thread.sleep(3000); //Wait for download to start
            driver.get("edge://downloads/all");

            Thread.sleep(Duration.ofSeconds(10).toMillis());

            try {
                String filePath = (String) ((JavascriptExecutor) driver).executeScript(
                        "function getDownloadedFilePath(targetFileName) {" +
                                "  function getLatestDownloadedFilePath() {" +
                                "    const shadowRootImgElement = document.querySelector(\"body > downloads-app\")?.shadowRoot.querySelector(\"#downloads-item-1 > div.downloads_itemIconContainer.iDoExist > img\");" +
                                "" +
                                "    if (shadowRootImgElement) {" +
                                "      const filePath = new URL(shadowRootImgElement.src).searchParams.get('path');" +
                                "      if (filePath) {" +
                                "        return decodeURIComponent(filePath);" +
                                "      }" +
                                "    } " +
                                "    const latestFileItem = document.querySelector('.downloads-list [role=\"listitem\"]');" +
                                "" +
                                "    if (latestFileItem) {" +
                                "      const fileIconImg = latestFileItem.querySelector('img');" +
                                "      if (fileIconImg) {" +
                                "        const filePath = new URL(fileIconImg.src).searchParams.get('path');" +
                                "        if (filePath) {" +
                                "          return decodeURIComponent(filePath);" +
                                "        }" +
                                "      }" +
                                "    } " +
                                "    return null;" +
                                "  }" +
                                "" +
                                "  const latestFilePath = getLatestDownloadedFilePath();" +
                                "  if (latestFilePath && latestFilePath.includes(targetFileName)) {" +
                                "    return latestFilePath;" +
                                "  }" +
                                "  return null;" +
                                "}" +
                                "return getDownloadedFilePath(arguments[0]);", targetFileName);

                if (filePath != null && !filePath.isEmpty()) {
                    System.out.println("File path for " + targetFileName + ": " + filePath);
                } else {
                    System.out.println("File " + targetFileName + " not found in downloads.");
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