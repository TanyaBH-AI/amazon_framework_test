package com.first.framework;

import org.openqa.selenium.By;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

public interface XpathResources {
    ConfigResource obj=new ConfigResource();
    String tshirtName = "'Solid Men Mandarin Collar Blue, Maroon T-Shirt'";
    String productString=obj.getProductName();
    //=====================  FLIPKART SECTION ===================================
    //String popUpUserID = "//form[@autocomplete=\"on\"]//input[@type=\"text\"]";
    String popUpUserID = "//form[@autocomplete='on']//input[@type='text']";
    String popUpPassword = "//input[@type='password']";
    String requestOtp = "//button[contains(text(),'Request OTP')]";
    String otpPos = "//input[@type='text']";
    String popUpLoginButton = "//button[@type='submit']/child::span";
    String popUpWindow = "//div[@class='_3Njdz7']";
    String search = "//input[@name='q']";
    String searchClick = "//button[@type='submit']";
    String oneTshirt = String.format("//a[contains(text(),%s)]", tshirtName);
    By productXpath= By.xpath(String.format("//input[@value='%s']",productString));
    String tshirtAll = "//div[@class='_1HmYoV _35HD7C']//div[contains(@data-id,'TSH')]//a";


    String userIdValue= "userid";
    String pwdValue="password";

    String tShirtName = "Striped Men Round Neck White T-Shirt";


  String msgField="//div[@data-tab='6']";
  String send="//span[@data-icon='send']";
  //===================== TC-03: Add to Cart Multiple Options =====================
    // Size option selectors — class-agnostic text-based (Flipkart hashes CSS class names on each deploy)
    String sizeM_primary = "(//a[normalize-space(text())='M'])[last()]";
    String sizeM_fallback = "(//span[normalize-space(text())='M'])[last()]";
    String sizeL_primary = "(//a[normalize-space(text())='L'])[last()]";
    String sizeL_fallback = "(//span[normalize-space(text())='L'])[last()]";

    // Color option selectors — attribute-based (swatches are img thumbnails or li/a with title)
    String colorRed_primary = "//a[.//img[@alt='Red']] | //a[@title='Red']";
    String colorRed_fallback = "//li[@title='Red']//a | //div[@title='Red']//a";
    String colorBlue_primary = "//a[.//img[@alt='Blue']] | //a[@title='Blue']";
    String colorBlue_fallback = "//li[@title='Blue']//a | //div[@title='Blue']//a";

    // Quantity + button — text-based
    String qtyPlus_primary = "(//button[normalize-space(text())='+'])[last()]";
    String qtyPlus_fallback = "(//button[@aria-label='Increase quantity' or contains(@title,'increase')])[last()]";

    // Add to Cart button — text-based (robust across case variants)
    String addToCart_primary = "//button[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'add to cart')]";
    String addToCart_fallback = "//button[contains(text(),'Add to cart') or contains(text(),'ADD TO CART')]";

    // Cart count badge — href-based (stable)
    String cartCount_primary = "//a[@href='/cart']//span[string-length(normalize-space(text()))>0]";
    String cartCount_fallback = "//a[contains(@href,'viewcart') or contains(@href,'/cart')]//span";

    // Cart line item rows — data-id or article-based (stable structural selectors)
    String cartLineItem_primary = "//div[@data-id]";
    String cartLineItem_fallback = "//div[contains(@class,'item') and .//a[contains(@href,'/p/')]]";

    //===================== Test Cases =====================
    void loginFunctionality();
    void searchItemFunctionality();
    void itemSelectionFunctionality();
    void purchaseFunctionality();
    void enterPin();
    void addToCartMultipleOptions_TC03();
}
