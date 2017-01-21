package org.bremersee.utils.test;

import junit.framework.TestCase;
import org.bremersee.utils.ResourceBundleUtils;
import org.junit.Test;

import java.util.Locale;

/**
 * @author Christian Bremer
 */
public class ResourceBundleUtilsTests {

    @Test
    public void getLocalizedMessage() {

        System.out.println("Testing resource bundle utils ...");

        String result = ResourceBundleUtils.getLocalizedMessage(
                "i18n",
                Locale.GERMAN,
                "say.hello",
                new Object[]{"Christian"},
                "Hallo");
        System.out.println("Translation = " + result);
        TestCase.assertEquals("Hallo Christian", result);

        result = ResourceBundleUtils.getLocalizedMessage(
                "i18n",
                Locale.FRENCH,
                "say.hello",
                new Object[]{"Christian"},
                "Salut");
        System.out.println("Translation = " + result);
        TestCase.assertEquals("Salut Christian", result);

        System.out.println("Testing resource bundle utils ... DONE!");
    }

}