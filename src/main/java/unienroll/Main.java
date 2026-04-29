package unienroll;

import unienroll.application.CourseService;
import unienroll.application.MemberService;
import unienroll.domain.Admin;
import unienroll.domain.Course;
import unienroll.domain.Student;
import unienroll.exception.DuplicateResourceException;
import unienroll.exception.NotFoundException;
import unienroll.exception.UnauthorizedException;
import unienroll.exception.ValidationException;
import unienroll.application.RegistrationWindowService;
import unienroll.infrastructure.file.FileCourseRepository;
import unienroll.infrastructure.file.FileMemberRepository;
import unienroll.infrastructure.file.FileRegistrationWindowRepository;
import java.time.LocalDateTime;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== UniEnroll Course Management System ===");

        try {
            // 1. Dependency Injection setup
            FileMemberRepository memberRepo = FileMemberRepository.getInstance();
            FileCourseRepository courseRepo = FileCourseRepository.getInstance();
            FileRegistrationWindowRepository windowRepo = FileRegistrationWindowRepository.getInstance();

            RegistrationWindowService windowService = new RegistrationWindowService(windowRepo);
            MemberService memberService = new MemberService(memberRepo);
            CourseService courseService = new CourseService(courseRepo, memberRepo, windowService);

            // Set an active registration window for demo purposes
            windowService.setRegistrationWindow(LocalDateTime.now().minusMinutes(10), LocalDateTime.now().plusHours(1));

            // 2. Create an Admin
            System.out.println("\n--- Registering Admin ---");
            Admin admin = new Admin("Alice Admin", "admin@uni.edu", "securePass123");
            try {
                memberService.registerMember(admin);
                System.out.println("Admin registered successfully.");
            } catch (DuplicateResourceException e) {
                System.out.println(e.getMessage());
                // Admin already exists, find by email instead
                admin = (Admin) memberService.findByEmail("admin@uni.edu");
            }
            // Admin must be verified to do anything, typically another super-admin does this. 
            // For demo, we verify the first admin manually if needed, or assume they are verified.
            admin.setVerified(true);
            memberRepo.update(admin);

            // 3. Create a Student
            System.out.println("\n--- Registering Student ---");
            Student student = new Student("Tahsin", "tp@gmail.com", "SupermanFlies");
            try {
                memberService.registerMember(student);
                System.out.println("Student registered successfully.");
            } catch (DuplicateResourceException e) {
                System.out.println(e.getMessage());
                student = (Student) memberService.findByEmail("tp@gmail.com");
            }

            // 4. Verify Student
            System.out.println("\n--- Verifying Student ---");
            memberService.approveMember(student.getId());
            System.out.println("Student verified by Admin.");

            // 5. Create a Course
            System.out.println("\n--- Creating Course ---");
            Course course = courseService.createCourse("Java 101", "Intro to Java Programming", admin.getId(), 30, 3.0, 500.0);
            System.out.println("Course created: " + course.getTitle() + " (ID: " + course.getCourseId() + ")");

            // 6. Student Enrolls in Course
            System.out.println("\n--- Enrolling Student ---");
            courseService.enrollStudent(student.getId(), course.getCourseId());
            System.out.println("Student " + student.getName() + " successfully enrolled in " + course.getTitle());

            // 7. Show System State
            System.out.println("\n--- Current System State ---");
            System.out.println("All Members:");
            memberService.listAllMembers().forEach(System.out::println);

            System.out.println("\nAll Courses:");
            courseService.listAllCourses().forEach(System.out::println);

        } catch (ValidationException | NotFoundException | UnauthorizedException | IllegalStateException e) {
            System.err.println("Business Logic Error: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Unexpected Error:");
            e.printStackTrace();
        }
    }
}