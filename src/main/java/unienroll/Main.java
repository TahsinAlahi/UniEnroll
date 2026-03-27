package unienroll;

import unienroll.infrastructure.file.FileMemberRepository;

public class Main {
    public static void main(String[] args) {
        try {
            FileMemberRepository repository = FileMemberRepository.getInstance();
            repository.deleteById("USR-103");
            System.out.println(repository.findByEmail("david.kim@example.com"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}