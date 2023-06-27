import java.awt.image.BufferedImage

import javax.imageio.ImageIO

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebDriverException;

import com.assertthat.selenium_shutterbug.core.Capture;
import com.assertthat.selenium_shutterbug.core.Shutterbug;
import com.kms.katalon.core.driver.DriverType;
import com.kms.katalon.core.webui.driver.DriverFactory
import com.kms.katalon.core.webui.driver.WebUIDriverType;
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.webui.util.FileUtil

String url = 'https://www.adm.com/en-us/insights-and-innovation/formulation-challenges/sugar-reduction/'
String fileName = "sugarReduction.png"
File png = new File("./" + fileName)

WebUI.openBrowser('')
WebUI.setViewPortSize(1200, 800)
WebUI.navigateToUrl(url)

//WebUI.takeFullPageScreenshot(png.toString())

WebDriver driver = DriverFactory.getWebDriver()
BufferedImage image = null
DriverType browser = DriverFactory.getExecutedBrowser()

int betweenScrollTimeout = 2000

boolean useDevicePixelRatio = true
long baseX = 0;
if (browser == WebUIDriverType.CHROME_DRIVER || browser == WebUIDriverType.EDGE_CHROMIUM_DRIVER) {
	image = Shutterbug.shootPage(driver, Capture.FULL, betweenScrollTimeout, useDevicePixelRatio).getImage()
	long scrollbarSize = getScrollBarSize(driver)
	baseX -= scrollbarSize
} else {
	image = Shutterbug.shootPage(driver, Capture.FULL_SCROLL, betweenScrollTimeout, useDevicePixelRatio).getImage()
}
FileUtil.saveImage(image, fileName)

WebUI.closeBrowser()

BufferedImage bi = ImageIO.read(png)
println "png width=" + bi.getWidth()
println "png height=" + bi.getHeight()


private static long getScrollBarSize(WebDriver driver) {
	if (driver == null) {
		return 0;
	}
	try {
		return (Long) ((JavascriptExecutor) driver)
				.executeScript("return (window.innerWidth - document.body.clientWidth)");
	} catch (NullPointerException | WebDriverException e) {
		return 0;
	}
}

