package tn.esprit.spring.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;
public class SkierTest {
    @Test
    public void testSkierCreation() {
        LocalDate birthDate = LocalDate.of(1990, 1, 1);
        Skier skier = new Skier(1L, "John", "Doe", birthDate, "New York", null, null, null);

        assertNotNull(skier);
        assertEquals("John", skier.getFirstName());
        assertEquals("Doe", skier.getLastName());
        assertEquals(birthDate, skier.getDateOfBirth());
        assertEquals("New York", skier.getCity());
    }
}


