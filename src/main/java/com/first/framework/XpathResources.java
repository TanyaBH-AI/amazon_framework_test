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
    // Size option selectors
    String sizeM_primary = "//div[contains(@class,'_1s4dGg')]//span[text()='M']";
    String sizeM_fallback = "//div[@class='_2d4LM1']//span[text()='M']";
    String sizeL_primary = "//div[contains(@class,'_1s4dGg')]//span[text()='L']";
    String sizeL_fallback = "//div[@class='_2d4LM1']//span[text()='L']";

    // Color option selectors
    String colorRed_primary = "//li[contains(@class,'_1uiNfd')]//span[text()='Red']";
    String colorRed_fallback = "//div[contains(@class,'_3V2wfe')]//span[text()='Red']";
    String colorBlue_primary = "//li[contains(@class,'_1uiNfd')]//span[text()='Blue']";
    String colorBlue_fallback = "//div[contains(@class,'_3V2wfe')]//span[text()='Blue']";

    // Quantity + button
    String qtyPlus_primary = "(//button[@class='_23HbyY'])[last()]";
    String qtyPlus_fallback = "//button[contains(@class,'_23HbyY') and text()='+']";

    // Add to Cart button
    String addToCart_primary = "//button[contains(text(),'Add to cart')]";
    String addToCart_fallback = "//div[@class='_3pPSAp']//button[1]";

    // Cart count badge
    String cartCount_primary = "//div[contains(@class,'_3HqJxs')]//span";
    String cartCount_fallback = "//a[@href='/cart']//span";

    // Cart line item rows
    String cartLineItem_primary = "//div[contains(@class,'_1s6Rch')]";
    String cartLineItem_fallback = "//div[contains(@class,'_1AtVbE')]";

    //===================== Test Cases =====================
    void loginFunctionality();
    void searchItemFunctionality();
    void itemSelectionFunctionality();
    void purchaseFunctionality();
    void enterPin();
    void addToCartMultipleOptions_TC03();
}
