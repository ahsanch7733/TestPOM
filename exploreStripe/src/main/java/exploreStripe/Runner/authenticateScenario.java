package exploreStripe.Runner;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.Test;

public class authenticateScenario {

	@Test
	public void openAuthenticateScenario() throws Throwable {

		WebDriver driver;
		String current = System.getProperty("user.dir");

		System.out.println(current + "\\src\\main\\resources\\driver\\chromedriver.exe");
		System.setProperty("webdriver.chrome.driver", current + "\\src\\main\\resources\\driver\\chromedriver.exe");

		driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().deleteAllCookies();

		// Navigate to Checkout.Stripe Website
		driver.get("https://checkout.stripe.dev/preview");
		Thread.sleep(2000);
		// Move to Iframe of Checkout Form
		driver.switchTo().frame("checkout-demo");
		// Enter keys in Email text Field
		driver.findElement(By.id("email")).sendKeys("ates@test.com");
		// Enter keys in card number Field for authenticate
		driver.findElement(By.id("cardNumber")).sendKeys("4000002500003155");
		// Enter keys in Card Expiry Field
		driver.findElement(By.id("cardExpiry")).sendKeys("12/21");
		// Enter keys in Card CVC Field
		driver.findElement(By.id("cardCvc")).sendKeys("7878");
		// Enter keys in Card Name Field
		driver.findElement(By.id("billingName")).sendKeys("test");

		// Selector element to select option of desired country from drop down
		WebElement countrySelection = driver.findElement(By.id("billingCountry"));
		Select country = new Select(countrySelection);
		// Option selection by visible text
		country.selectByVisibleText("United States");

		//// Enter keys in ZIP Code Field
		driver.findElement(By.id("billingPostalCode")).sendKeys("454500");

		// Click on pay now button
		driver.findElement(By.cssSelector(".SubmitButton--complete")).click();
		Thread.sleep(10000);
		// Switch to challenge frame and to acsFrame to click on complete authentication.
		driver.switchTo().defaultContent();
		driver.switchTo().frame("checkout-demo").switchTo().frame(0).switchTo().frame("challengeFrame").switchTo()
				.frame("acsFrame");
		//Click on complete authentication
		driver.findElement(By.id("test-source-authorize-3ds")).click();
		Thread.sleep(4000);
		// Get text of payment success message from application
		String paymentmsg = driver.findElement(By.cssSelector(".CheckoutOverlay h2")).getText();
		// Apply assertion to verify the success message.
		assertEquals(paymentmsg, "Payment success");

	}

}
