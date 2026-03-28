package unienroll;

import unienroll.infrastructure.file.FileCourseRepository;
import unienroll.infrastructure.file.FileMemberRepository;

public class Main {
    public static void main(String[] args) {
        try {
            FileMemberRepository repository = FileMemberRepository.getInstance();
            FileCourseRepository courseRepository = FileCourseRepository.getInstance();
//            System.out.println(courseRepository.toString());
//            repository.save(new Student("Tahsin", "tp@gmail.com", "SupermanFlies"));
//            courseRepository.save(new Course("NSU-100", "This a very good course", "Batman", 67));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}