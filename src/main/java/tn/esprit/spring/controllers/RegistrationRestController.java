package tn.esprit.spring.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.*;
import tn.esprit.spring.entities.Registration;
import tn.esprit.spring.entities.Support;
import tn.esprit.spring.services.IRegistrationServices;

import java.util.List;

@Tag(name = "\uD83D\uDDD3️ Registration Management")
@RestController
@RequestMapping("/registration")
@RequiredArgsConstructor
public class RegistrationRestController {
    // Initialisation du logger
    private static final Logger logger = LogManager.getLogger(RegistrationRestController.class);

    private final IRegistrationServices registrationServices;

    @Operation(description = "Add Registration and Assign to Skier")
    @PutMapping("/addAndAssignToSkier/{numSkieur}")
    public Registration addAndAssignToSkier(@RequestBody Registration registration,
                                            @PathVariable("numSkieur") Long numSkieur) {
        logger.info("Starting to add and assign registration to skier with ID: {}", numSkieur);
        try {
            Registration result = registrationServices.addRegistrationAndAssignToSkier(registration, numSkieur);
            logger.info("Successfully added and assigned registration to skier ID: {}", numSkieur);
            return result;
        } catch (Exception e) {
            logger.error("Error while assigning registration to skier ID: {}", numSkieur, e);
            throw e;
        }
    }

    @Operation(description = "Assign Registration to Course")
    @PutMapping("/assignToCourse/{numRegis}/{numCourse}")
    public Registration assignToCourse(@PathVariable("numRegis") Long numRegistration,
                                       @PathVariable("numCourse") Long numCourse) {
        logger.debug("Assigning registration ID: {} to course ID: {}", numRegistration, numCourse);
        try {
            Registration result = registrationServices.assignRegistrationToCourse(numRegistration, numCourse);
            logger.info("Successfully assigned registration ID: {} to course ID: {}", numRegistration, numCourse);
            return result;
        } catch (Exception e) {
            logger.error("Failed to assign registration ID: {} to course ID: {}", numRegistration, numCourse, e);
            throw e;
        }
    }

    @Operation(description = "Add Registration and Assign to Skier and Course")
    @PutMapping("/addAndAssignToSkierAndCourse/{numSkieur}/{numCourse}")
    public Registration addAndAssignToSkierAndCourse(@RequestBody Registration registration,
                                                     @PathVariable("numSkieur") Long numSkieur,
                                                     @PathVariable("numCourse") Long numCourse) {
        logger.info("Adding registration and assigning to skier ID: {} and course ID: {}", numSkieur, numCourse);
        try {
            Registration result = registrationServices.addRegistrationAndAssignToSkierAndCourse(registration, numSkieur, numCourse);
            logger.info("Successfully added registration and assigned to skier ID: {} and course ID: {}", numSkieur, numCourse);
            return result;
        } catch (Exception e) {
            logger.error("Error while adding and assigning registration to skier ID: {} and course ID: {}", numSkieur, numCourse, e);
            throw e;
        }
    }

    @Operation(description = "Numbers of the weeks when an instructor has given lessons in a given support")
    @GetMapping("/numWeeks/{numInstructor}/{support}")
    public List<Integer> numWeeksCourseOfInstructorBySupport(@PathVariable("numInstructor") Long numInstructor,
                                                             @PathVariable("support") Support support) {
        logger.info("Fetching weeks for instructor ID: {} with support: {}", numInstructor, support);
        try {
            List<Integer> weeks = registrationServices.numWeeksCourseOfInstructorBySupport(numInstructor, support);
            logger.debug("Weeks found for instructor ID: {}: {}", numInstructor, weeks);
            return weeks;
        } catch (Exception e) {
            logger.error("Failed to fetch weeks for instructor ID: {} with support: {}", numInstructor, support, e);
            throw e;
        }
    }
}
