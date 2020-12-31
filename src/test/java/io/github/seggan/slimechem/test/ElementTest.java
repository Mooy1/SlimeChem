package io.github.seggan.slimechem.test;

import io.github.mooy1.slimechem.implementation.atomic.Element;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.Isotope;
import io.github.mooy1.slimechem.implementation.atomic.isotopes.IsotopeLoader;
import io.github.mooy1.slimechem.lists.Constants;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ElementTest {

    @BeforeAll
    public static void setUp() {
        Constants.isTestingEnvironment = true;
        Isotope.getIsotopes().clear();
        IsotopeLoader isotopeLoader = new IsotopeLoader();
        isotopeLoader.load();
        isotopeLoader.loadDecayProducts();
    }

    @Test
    public void testIsotopeGetting() {
        for (Element element : Element.values()) {
            if (element.getSeries() != Element.Series.CUSTOM) {
                System.out.println(element.getCorrespondingIsotope());
            }
        }
    }
}
