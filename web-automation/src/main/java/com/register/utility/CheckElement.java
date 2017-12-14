/**
 * @author Kaushal Chandwani
 *
 */
package com.register.utility;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

public class CheckElement 
{
	public static boolean existsElement(String xpath, WebDriver driverElement) 
	{
	    try 
	    {
	    	driverElement.findElement(By.xpath(xpath));
	    } 
	    catch (NoSuchElementException e) 
	    {
	        return false;
	    }
	    return true;
	}
}
