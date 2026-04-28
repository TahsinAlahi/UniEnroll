package unienroll.application;

import unienroll.domain.Admin;
import unienroll.domain.Member;
import unienroll.domain.Student;
import unienroll.exception.DuplicateResourceException;
import unienroll.exception.NotFoundException;
import unienroll.exception.UnauthorizedException;
import unienroll.repository.MemberRepository;

import java.util.List;

public class MemberService {
    private final MemberRepository repository;

    public MemberService(MemberRepository repository){
        this.repository = repository;
    }

    public void registerMember(Admin admin){
        if(repository.existsByEmail(admin.getEmail())){
            throw new DuplicateResourceException("Email already exists: " + admin.getEmail());
        }
        repository.add(admin);
    }

    public void registerMember(Student student){
        if(repository.existsByEmail(student.getEmail())){
            throw new DuplicateResourceException("Email already exists: " + student.getEmail());
        }
        repository.add(student);
    }

    public Member login(String email, String password){
        Member member = repository.findByEmail(email);
        if(member == null) {
            throw new NotFoundException("Member not found with email: " + email);
        }
        if(!member.isValidPassword(password)) {
            throw new UnauthorizedException("Invalid password");
        }
        return member;
    }

    public void updateMember(Member member, String newName, String newPassword) {
        member.setName(newName);
        if (newPassword != null && !newPassword.isEmpty()) {
            member.setPassword(newPassword);
        }
        repository.update(member);
    }

    public Member findByEmail(String email){
        return repository.findByEmail(email);
    }

    public List<Member> listAllMembers() {
        return repository.findAll();
    }

    public void approveMember(String memberId) {
        Member user = repository.findById(memberId);
        if (user != null) {
            user.setVerified(true);
            repository.update(user);
        } else {
            throw new NotFoundException("Member not found with ID: " + memberId);
        }
    }

    public void deleteMember(String memberId) {
        if (!repository.existsById(memberId)) {
            throw new NotFoundException("Member not found with ID: " + memberId);
        }
        repository.deleteById(memberId);
    }

    public List<Member> pendingMembers(){
        return repository.findAll().stream()
                .filter(m -> !m.getIsVerified())
                .toList();
    }

    public boolean isMemberVerified(String memberId) {
        Member member = repository.findById(memberId);
        return member != null && member.getIsVerified();
    }

}
