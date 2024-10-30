package tn.esprit.spring.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // This ensures mocks are initialized automatically
public class SkierServicesImplTest {

    @Mock
    private ISkierRepository skierRepository;

    @Mock
    private ISubscriptionRepository subscriptionRepository;

    @Mock
    private IPisteRepository pisteRepository;

    @InjectMocks
    private SkierServicesImpl skierServices;

    private Skier skier;
    private Subscription subscription;
    private Piste piste;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Ensure mocks are initialized

        // Initialize entities
        subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());

        piste = new Piste();
        piste.setNamePiste("Blue Trail");

        skier = new Skier();
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setSubscription(subscription);
        skier.setPistes(new HashSet<>(Set.of(piste)));
    }

    @Test
    public void testAddSkier() {
        // Test adding a skier and setting end date based on subscription type
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.addSkier(skier);
        assertNotNull(result);
        assertEquals(LocalDate.now().plusYears(1), result.getSubscription().getEndDate());
    }

    @Test
    public void testAssignSkierToSubscription() {
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(subscription));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.assignSkierToSubscription(1L, 2L);
        assertNotNull(result);
        assertEquals(subscription, result.getSubscription());
    }

    @Test
    public void testAssignSkierToPiste() {
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(2L)).thenReturn(Optional.of(piste));
        when(skierRepository.save(any(Skier.class))).thenReturn(skier);

        Skier result = skierServices.assignSkierToPiste(1L, 2L);
        assertNotNull(result);
        assertTrue(result.getPistes().contains(piste));
    }

    @Test
    public void testRemoveSkier() {
        Long skierId = 1L;
        doNothing().when(skierRepository).deleteById(skierId);

        skierServices.removeSkier(skierId);
        verify(skierRepository, times(1)).deleteById(skierId);
    }
}
