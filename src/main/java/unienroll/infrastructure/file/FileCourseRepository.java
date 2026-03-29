package unienroll.infrastructure.file;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import unienroll.Main;
import unienroll.domain.Course;
import unienroll.repository.CourseRepository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCourseRepository implements CourseRepository {
    ObjectMapper mapper = new ObjectMapper();
    private final List<Course> courses;
    private final Map<String, Course> coursesById;
    File file = new File("src/main/resources/data/courses.json");

    private static FileCourseRepository instance;

    private FileCourseRepository() throws Exception {
        courses = mapper.readValue(Main.class.getResourceAsStream("/data/courses.json"),
                new TypeReference<>() {
                });
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
    public void update(Course entity) {
        // update the list
        for(int i=0; i<courses.size(); i++){
            if(courses.get(i).getCourseId().equals(entity.getCourseId())){
                courses.set(i, entity);
                break;
            }
        }

        // update the map
        coursesById.put(entity.getCourseId(), entity);
        save();
    }

    @Override
    public void save() {
        saveToFile();
    }

    @Override
    public Course add(Course entity) {
        System.out.println(entity.toString());
        courses.add(entity);
        coursesById.put(entity.getCourseId(), entity);
        save();
        return entity;
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
        save();
    }

    @Override
    public boolean existsById(String id) {
        return coursesById.containsKey(id);
    }

    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, courses);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
