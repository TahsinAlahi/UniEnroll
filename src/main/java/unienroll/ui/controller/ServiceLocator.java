
package unienroll.ui.controller;

import unienroll.application.CourseService;
import unienroll.application.MemberService;
import unienroll.application.RegistrationWindowService;

public class ServiceLocator {
    private static ServiceLocator instance;
    
    private MemberService memberService;
    private CourseService courseService;
    private RegistrationWindowService windowService;

    private ServiceLocator() {}

    public static ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }

    public void registerMemberService(MemberService service) {
        this.memberService = service;
    }

    public void registerCourseService(CourseService service) {
        this.courseService = service;
    }

    public void registerWindowService(RegistrationWindowService service) {
        this.windowService = service;
    }

    public MemberService getMemberService() {
        return memberService;
    }

    public CourseService getCourseService() {
        return courseService;
    }

    public RegistrationWindowService getWindowService() {
        return windowService;
    }
}

