package unienroll.ui.controllers;

import unienroll.application.MemberService;
import unienroll.domain.Admin;
import unienroll.domain.Member;
import unienroll.domain.Student;
import unienroll.exception.DuplicateResourceException;
import unienroll.exception.NotFoundException;
import unienroll.exception.UnauthorizedException;
import unienroll.exception.ValidationException;
import unienroll.ui.SessionState;

public class AuthController {
    private final MemberService memberService;
    private final SessionState sessionState;

    public AuthController(MemberService memberService) {
        this.memberService = memberService;
        this.sessionState = SessionState.getInstance();
    }

    public void login(String email, String password) throws Exception {
        try {
            Member member = memberService.login(email, password);
            sessionState.setLoggedInMember(member);
        } catch (NotFoundException | UnauthorizedException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void registerStudent(String name, String email, String password) throws Exception {
        try {
            Student student = new Student(name, email, password);
            memberService.registerMember(student);
        } catch (DuplicateResourceException | ValidationException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void registerAdmin(String name, String email, String password) throws Exception {
        try {
            Admin admin = new Admin(name, email, password);
            memberService.registerMember(admin);
        } catch (DuplicateResourceException | ValidationException e) {
            throw new Exception(e.getMessage());
        }
    }

    public void logout() {
        sessionState.clear();
    }
}
