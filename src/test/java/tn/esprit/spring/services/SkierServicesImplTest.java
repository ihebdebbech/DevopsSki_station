package tn.esprit.spring.services;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.Skier;
import tn.esprit.spring.entities.Subscription;
import tn.esprit.spring.repositories.*;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkierServicesImplTest {

    @Mock
    private ISkierRepository skierRepository;
    @Mock
    private ISubscriptionRepository subscriptionRepository;
    @Mock
    private IPisteRepository pisteRepository;
    @Mock
    private ICourseRepository courseRepository;
    @Mock
    private IRegistrationRepository registrationRepository;

    @InjectMocks
    private SkierServicesImpl skierServices;



    @Test
    public void testAssignSkierToSubscription() {
        Skier skier = new Skier();
        Subscription sub = new Subscription();

        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(2L)).thenReturn(Optional.of(sub));

        skierServices.assignSkierToSubscription(1L, 2L);

        assertEquals(sub, skier.getSubscription());
        verify(skierRepository).save(skier);
    }

    @Test
    public void testRetrieveAllSkiers() {
        when(skierRepository.findAll()).thenReturn(Arrays.asList(new Skier(), new Skier()));

        List<Skier> result = skierServices.retrieveAllSkiers();

        assertEquals(2, result.size());
        verify(skierRepository).findAll();
    }

    @Test
    public void testRemoveSkier() {
        skierServices.removeSkier(1L);

        verify(skierRepository).deleteById(1L);
    }

    @Test
    public void testRetrieveSkier() {
        Skier skier = new Skier();
        when(skierRepository.findById(1L)).thenReturn(Optional.of(skier));

        Skier result = skierServices.retrieveSkier(1L);

        assertNotNull(result);
        verify(skierRepository).findById(1L);
    }
}
