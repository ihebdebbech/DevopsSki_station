package tn.esprit.spring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.spring.entities.*;
import tn.esprit.spring.repositories.*;
import tn.esprit.spring.services.SkierServicesImpl;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SkierServicesImplTest {

    @Mock
    ISkierRepository skierRepository;

    @Mock
    IPisteRepository pisteRepository;

    @Mock
    ICourseRepository courseRepository;

    @Mock
    IRegistrationRepository registrationRepository;

    @Mock
    ISubscriptionRepository subscriptionRepository;

    @InjectMocks
    SkierServicesImpl skierService;

    private Skier skier;
    private Subscription subscription;

    @BeforeEach
    void setUp() {
        subscription = new Subscription();
        subscription.setTypeSub(TypeSubscription.ANNUAL);
        subscription.setStartDate(LocalDate.now());

        skier = new Skier();
        skier.setNumSkier(1L);
        skier.setFirstName("John");
        skier.setLastName("Doe");
        skier.setSubscription(subscription);
    }

    @Test
    void retrieveAllSkiersTest() {
        List<Skier> skierList = List.of(skier);
        when(skierRepository.findAll()).thenReturn(skierList);

        List<Skier> retrievedSkiers = skierService.retrieveAllSkiers();

        assertThat(retrievedSkiers).hasSize(1);
        verify(skierRepository).findAll();
    }

    @Test
    void addSkierTest() {
        when(skierRepository.save(Mockito.any(Skier.class))).thenReturn(skier);

        Skier savedSkier = skierService.addSkier(skier);

        assertThat(savedSkier.getSubscription().getEndDate()).isEqualTo(subscription.getStartDate().plusYears(1));
        verify(skierRepository).save(skier);
    }

    @Test
    void assignSkierToSubscriptionTest() {
        Long skierId = 1L;
        Long subscriptionId = 1L;
        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier));
        when(subscriptionRepository.findById(subscriptionId)).thenReturn(Optional.of(subscription));
        when(skierRepository.save(skier)).thenReturn(skier);

        Skier updatedSkier = skierService.assignSkierToSubscription(skierId, subscriptionId);

        assertThat(updatedSkier.getSubscription()).isEqualTo(subscription);
        verify(skierRepository).save(skier);
    }

    @Test
    void addSkierAndAssignToCourseTest() {
        Course course = new Course();
        course.setNumCourse(1L);

        Registration registration = new Registration();
        registration.setSkier(skier);
        skier.setRegistrations(Set.of(registration));

        when(skierRepository.save(skier)).thenReturn(skier);
        when(courseRepository.getById(course.getNumCourse())).thenReturn(course);

        Skier savedSkier = skierService.addSkierAndAssignToCourse(skier, course.getNumCourse());

        assertThat(savedSkier.getRegistrations()).isNotEmpty();
        verify(registrationRepository, times(1)).save(any(Registration.class));
    }

    @Test
    void removeSkierTest() {
        Long skierId = 1L;
        doNothing().when(skierRepository).deleteById(skierId);

        skierService.removeSkier(skierId);

        verify(skierRepository).deleteById(skierId);
    }

    @Test
    void retrieveSkierTest() {
        Long skierId = 1L;
        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier));

        Skier retrievedSkier = skierService.retrieveSkier(skierId);

        assertThat(retrievedSkier).isNotNull();
        verify(skierRepository).findById(skierId);
    }

    @Test
    void assignSkierToPisteTest() {
        Long skierId = 1L;
        Long pisteId = 1L;
        Piste piste = new Piste();
        piste.setNumPiste(pisteId);

        when(skierRepository.findById(skierId)).thenReturn(Optional.of(skier));
        when(pisteRepository.findById(pisteId)).thenReturn(Optional.of(piste));
        when(skierRepository.save(skier)).thenReturn(skier);

        Skier updatedSkier = skierService.assignSkierToPiste(skierId, pisteId);

        assertThat(updatedSkier.getPistes()).contains(piste);
        verify(skierRepository).save(skier);
    }

    @Test
    void retrieveSkiersBySubscriptionTypeTest() {
        List<Skier> skierList = List.of(skier);
        when(skierRepository.findBySubscription_TypeSub(TypeSubscription.ANNUAL)).thenReturn(skierList);

        List<Skier> retrievedSkiers = skierService.retrieveSkiersBySubscriptionType(TypeSubscription.ANNUAL);

        assertThat(retrievedSkiers).hasSize(1);
        verify(skierRepository).findBySubscription_TypeSub(TypeSubscription.ANNUAL);
    }
}
