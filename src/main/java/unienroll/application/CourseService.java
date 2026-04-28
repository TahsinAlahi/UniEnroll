package unienroll.application;

import unienroll.domain.Course;
import unienroll.domain.Member;
import unienroll.domain.Roles;
import unienroll.domain.Student;
import unienroll.exception.NotFoundException;
import unienroll.exception.UnauthorizedException;
import unienroll.repository.CourseRepository;
import unienroll.repository.MemberRepository;

import java.util.List;

public class CourseService {
    private final CourseRepository courseRepository;
    private final MemberRepository memberRepository;

    public CourseService(CourseRepository courseRepository, MemberRepository memberRepository) {
        this.courseRepository = courseRepository;
        this.memberRepository = memberRepository;
    }

    public Course createCourse(String title, String description, String instructorId, int capacity) {
        Member instructor = memberRepository.findById(instructorId);
        if (instructor == null) {
            throw new NotFoundException("Instructor not found with ID: " + instructorId);
        }
        if (instructor.getRole() != Roles.ADMIN) {
            // Alternatively, create an Instructor role. For now, assuming Admins are instructors or anyone verified.
            // Let's just ensure they exist. The user prompt mentioned only Student and Admin roles.
        }

        Course course = new Course(title, description, instructorId, capacity);
        courseRepository.add(course);
        return course;
    }

    public void enrollStudent(String studentId, String courseId) {
        Member member = memberRepository.findById(studentId);
        if (member == null) {
            throw new NotFoundException("Student not found with ID: " + studentId);
        }
        if (member.getRole() != Roles.STUDENT) {
            throw new UnauthorizedException("Only students can enroll in courses.");
        }
        if (!member.getIsVerified()) {
            throw new UnauthorizedException("Student is not verified by an admin yet.");
        }

        Course course = courseRepository.findById(courseId);
        if (course == null) {
            throw new NotFoundException("Course not found with ID: " + courseId);
        }

        Student student = (Student) member;
        
        if (course.getEnrolledStudentsId().contains(studentId)) {
            throw new IllegalStateException("Student is already enrolled in this course.");
        }

        if (!course.hasAvailableSeat()) {
            throw new IllegalStateException("Course is full.");
        }

        course.enrollStudent(studentId);
        student.enrollCourse(courseId);

        courseRepository.update(course);
        memberRepository.update(student);
    }

    public void dropCourse(String studentId, String courseId) {
        Member member = memberRepository.findById(studentId);
        if (member == null || member.getRole() != Roles.STUDENT) {
            throw new NotFoundException("Student not found.");
        }
        Course course = courseRepository.findById(courseId);
        if (course == null) {
            throw new NotFoundException("Course not found.");
        }

        Student student = (Student) member;
        
        course.removeStudent(studentId);
        student.dropCourse(courseId);

        courseRepository.update(course);
        memberRepository.update(student);
    }

    public List<Course> listAllCourses() {
        return courseRepository.findAll();
    }
}
