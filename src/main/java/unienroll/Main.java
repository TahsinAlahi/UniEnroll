package unienroll;

import unienroll.infrastructure.file.FileCourseRepository;
import unienroll.infrastructure.file.FileMemberRepository;

public class Main {
    public static void main(String[] args) {
        try {
            FileMemberRepository repository = FileMemberRepository.getInstance();
            FileCourseRepository courseRepository = FileCourseRepository.getInstance();
//            System.out.println(courseRepository.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}