package unienroll.infrastructure.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import unienroll.Main;
import unienroll.domain.Course;
import unienroll.repository.CourseRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCourseRepository implements CourseRepository {
    ObjectMapper mapper = new ObjectMapper();
    private final List<Course> courses;
    private final Map<String, Course> coursesById;

    private static FileCourseRepository instance;

    private FileCourseRepository() throws Exception {
        courses = mapper.readValue(Main.class.getResourceAsStream("/data/courses.json"),
                new TypeReference<>() {
                });
        for(Course c: courses){
            System.out.println(c);
        }
        coursesById = new HashMap<>();
        for (Course c : courses) {
            coursesById.put(c.getCourseId(), c);
        }
    }

    public static FileCourseRepository getInstance() {
        if (instance == null) {
            try {
                instance = new FileCourseRepository();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public String toString() {
        //   TODO: Stream looks interesting check doc when you have time
        return "FileCourseRepository{\n" +
                courses.stream()
                        .map(m -> "  " + m + ", ")
                        .reduce("", (a, b) -> a + b + "\n") +
                "}";
    }

    @Override
    public Course save(Course entity) {
        return null;
    }

    @Override
    public Course findById(String id) {
        return coursesById.get(id);
    }

    @Override
    public List<Course> findAll() {
        return courses;
    }

    @Override
    public void deleteById(String id) {
        courses.removeIf(c -> c.getCourseId().equals(id));
        coursesById.remove(id);

    }

    @Override
    public boolean existsById(String id) {
        return coursesById.containsKey(id);
    }
}
