package unienroll.application;

import unienroll.domain.Admin;
import unienroll.domain.Member;
import unienroll.domain.Student;
import unienroll.infrastructure.file.FileMemberRepository;

import java.util.List;

public class MemberService {
    private FileMemberRepository repository = FileMemberRepository.getInstance();

    public MemberService(FileMemberRepository repository){
        this.repository = repository;
    }

    public void registerMember(Admin admin){
        if(repository.existsByEmail(admin.getEmail())){
            System.out.println("Email already exists");
            return;
        }
        System.out.println("Registering new admin");
        repository.add(admin);
    }

    public void registerMember(Student student){
        if(repository.existsByEmail(student.getEmail())){
            System.out.println("Email already exists");
            return;
        }
        System.out.println("Registering Student");
        repository.add(student);
    }

    public Member login(String email, String password){
        Member member = repository.findByEmail(email);
        if(member == null) return null;
        if(!member.isValidPassword(password)) return null;
        System.out.println("Login successful");
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
            System.out.println(user.getName() + " approved!");

        }
    }

    public void deleteMember(String memberId) {
        repository.deleteById(memberId);
        System.out.println("Member deleted: " + memberId);
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
