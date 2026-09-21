import com.first.framework.ConfigResource;
import com.first.framework.XpathResources;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class FlipkartTests extends ConfigResource implements XpathResources {
    private static Logger logger;
    private static WebDriver driver;
    private static ConfigResource ref;
    private WebDriverWait wait;

    public FlipkartTests() {
        logger = Logger.getLogger(FlipkartTests.class.getName());
        ref = new ConfigResource();
        if(getBrowserName().equals("chrome")) {
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();
        }else if(getBrowserName().equals("firefox")){
            WebDriverManager.firefoxdriver().setup();
            driver=new FirefoxDriver();
        }
        driver.manage().window().maximize();
    }


    @BeforeTest
    public void createDriver() throws IOException {
        FlipkartTests obj = new FlipkartTests();
        driver.get(obj.getUrlValue());
        logger.fine("Driver instantiated Successfully");
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        // Login popup appears intermittently; do not fail setup if absent
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(popUpUserID)));
            logger.info("Login popup visible on homepage.");
        } catch (Exception e) {
            logger.info("Login popup not visible on homepage; continuing setup.");
        }
    }

    @BeforeClass
    public void loginFunctionality() {
        try {
            WebElement loginID = driver.findElement(By.xpath(popUpUserID));
            loginID.sendKeys(ref.getUserId());
            WebElement pwd = driver.findElement(By.xpath(popUpPassword));
            pwd.sendKeys(ref.getPasswordValue());
            WebElement submit = driver.findElement(By.xpath(popUpLoginButton));
            submit.click();
            wait.until(ExpectedConditions.invisibilityOf(submit));
        } catch (ElementNotInteractableException e) {
            logger.info("Different Element is present");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.info("Login popup not found or login skipped: " + e.getClass().getSimpleName());
        } finally {
            Assert.assertTrue(driver.findElement(By.xpath(search)).isDisplayed());
        }
    }


    @Test(priority = 1)
    public void searchItemFunctionality() {
        SoftAssert softAssert = new SoftAssert();
        WebElement searchField = driver.findElement(By.xpath(search));
        searchField.sendKeys(ref.getProductName());
        WebElement product = driver.findElement(productXpath);
        softAssert.assertTrue(product.isDisplayed());
        searchField.submit();
        softAssert.assertAll();
    }


    @Test(priority = 2)
    public void itemSelectionFunctionality() {
        WebElement shirt = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(oneTshirt)));
        shirt.click();
        String Parent = driver.getWindowHandle();
        Set<String> winHandle = driver.getWindowHandles();
        for (String s : winHandle) {
            if (!s.equals(Parent))
                driver.switchTo().window(s);
            logger.info(s);
        }
        wait.until(ExpectedConditions.invisibilityOf(shirt));
    }


    // @Test(priority = 3)
    public void purchaseFunctionality() {
        logger.info("Will write later");
    }

    @Override
    @Test(priority = 3)
    public void addToCartMultipleOptions_TC03() {
        SoftAssert softAssert = new SoftAssert();
        String productUrl = ref.getProductUrlTC03();

        // Step 1: Navigate to product page with size/color options
        driver.get(productUrl);
        logger.info("TC-03: Navigated to product page");

        // Step 2: Select size M
        clickWithFallback(sizeM_primary, sizeM_fallback, "Size M");

        // Step 3: Select color Red
        clickWithFallback(colorRed_primary, colorRed_fallback, "Color Red");

        // Step 4: Click + qty button once (qty becomes 2)
        clickWithFallback(qtyPlus_primary, qtyPlus_fallback, "Quantity +");
        logger.info("TC-03: Selected Size M, Color Red, Qty 2");

        // Step 5: Click Add to Cart
        clickWithFallback(addToCart_primary, addToCart_fallback, "Add to Cart");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.xpath(cartCount_primary)),
                ExpectedConditions.presenceOfElementLocated(By.xpath(cartCount_fallback))
        ));
        logger.info("TC-03: First item added to cart (M/Red/Qty 2)");

        // Step 6: Navigate back to same product page for second variant
        driver.get(productUrl);

        // Step 7: Select size L
        clickWithFallback(sizeL_primary, sizeL_fallback, "Size L");

        // Step 8: Select color Blue (qty defaults to 1)
        clickWithFallback(colorBlue_primary, colorBlue_fallback, "Color Blue");
        logger.info("TC-03: Selected Size L, Color Blue, Qty 1");

        // Step 9: Click Add to Cart
        clickWithFallback(addToCart_primary, addToCart_fallback, "Add to Cart");
        wait.until(ExpectedConditions.or(
                ExpectedConditions.presenceOfElementLocated(By.xpath(cartCount_primary)),
                ExpectedConditions.presenceOfElementLocated(By.xpath(cartCount_fallback))
        ));
        logger.info("TC-03: Second item added to cart (L/Blue/Qty 1)");

        // Step 10: Navigate to cart
        driver.get("https://www.flipkart.com/viewcart");

        // Step 11: Assert cart count shows 3 total items
        WebElement cartBadge = findElementWithFallback(cartCount_primary, cartCount_fallback);
        if (cartBadge != null) {
            String countText = cartBadge.getText().trim();
            Assert.assertEquals(countText, "3", "Cart count should be 3 total items");
            logger.info("TC-03: Cart count verified = " + countText);
        } else {
            Assert.fail("TC-03: Cart count badge not found");
        }

        // Step 12: SoftAssert two separate line items in cart
        List<WebElement> lineItems = driver.findElements(By.xpath(cartLineItem_primary));
        if (lineItems.isEmpty()) {
            lineItems = driver.findElements(By.xpath(cartLineItem_fallback));
        }
        softAssert.assertEquals(lineItems.size(), 2,
                "Cart should display 2 separate line items (M/Red and L/Blue)");
        logger.info("TC-03: Found " + lineItems.size() + " line items in cart");

        softAssert.assertAll();
        logger.info("TC-03: addToCartMultipleOptions completed successfully");
    }

    /** Tries primary xpath, falls back to fallback xpath. Returns the element or null. */
    private WebElement findElementWithFallback(String primaryXpath, String fallbackXpath) {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(primaryXpath)));
        } catch (Exception e) {
            try {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(fallbackXpath)));
            } catch (Exception ex) {
                logger.warning("Element not found with primary or fallback xpath");
                return null;
            }
        }
    }

    /** Clicks an element using primary xpath; if not found, tries fallback. */
    private void clickWithFallback(String primaryXpath, String fallbackXpath, String description) {
        try {
            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(primaryXpath)));
            el.click();
        } catch (Exception e) {
            logger.info(description + ": primary selector failed, trying fallback");
            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(fallbackXpath)));
            el.click();
        }
    }

    @Override
    @Test(priority = 1)
    public void enterPin() {
        logger.info("PIN is 824512");
    }

    @AfterClass
    public static void tearDown() {

        driver.quit();
    }

}
