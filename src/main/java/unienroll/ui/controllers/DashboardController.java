package unienroll.ui.controllers;

import unienroll.application.CourseService;
import unienroll.application.MemberService;
import unienroll.domain.Course;
import unienroll.domain.Member;
import unienroll.domain.Student;
import unienroll.exception.NotFoundException;
import unienroll.exception.UnauthorizedException;
import unienroll.exception.ValidationException;
import unienroll.ui.SessionState;

import java.util.List;
import java.util.stream.Collectors;

public class DashboardController {
    private final MemberService memberService;
    private final CourseService courseService;
    private final SessionState sessionState;

    public DashboardController(MemberService memberService, CourseService courseService) {
        this.memberService = memberService;
        this.courseService = courseService;
        this.sessionState = SessionState.getInstance();
    }

    // Admin operations
    public List<Member> getPendingMembers() {
        return memberService.pendingMembers();
    }

    public void approveMember(String memberId) throws Exception {
        try {
            memberService.approveMember(memberId);
        } catch (NotFoundException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void createCourse(String title, String description, int capacity) throws Exception {
        Member currentUser = sessionState.getLoggedInMember();
        if (currentUser == null || !currentUser.getIsVerified()) {
            throw new Exception("You must be logged in and verified to create a course.");
        }
        try {
            courseService.createCourse(title, description, currentUser.getId(), capacity);
        } catch (NotFoundException | ValidationException e) {
            throw new Exception(e.getMessage());
        }
    }

    // Student operations
    public List<Course> getAvailableCourses() {
        return courseService.listAllCourses();
    }

    public List<Course> getEnrolledCourses() {
        Member currentUser = sessionState.getLoggedInMember();
        if (currentUser instanceof Student) {
            Student student = (Student) currentUser;
            return courseService.listAllCourses().stream()
                    .filter(c -> student.getEnrolledCoursesId().contains(c.getCourseId()))
                    .collect(Collectors.toList());
        }
        return List.of();
    }

    public void enrollCourse(String courseId) throws Exception {
        Member currentUser = sessionState.getLoggedInMember();
        if (currentUser == null) {
            throw new Exception("You must be logged in.");
        }
        try {
            courseService.enrollStudent(currentUser.getId(), courseId);
        } catch (NotFoundException | UnauthorizedException | IllegalStateException e) {
            throw new Exception(e.getMessage());
        }
    }
}
