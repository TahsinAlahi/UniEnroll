package unienroll.infrastructure.file;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import unienroll.Main;
import unienroll.domain.Member;
import unienroll.repository.MemberRepository;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class FileMemberRepository implements MemberRepository {
    ObjectMapper mapper = new ObjectMapper();
    private final List<Member> members;
    //    Using map for faster lookup O(1)
    private final Map<String, Member> membersByEmail;
    private final Map<String, Member> membersById;
    //    I could've also used getClass().getResource("/data/members.json").getFile()
    private final File file = new File("src/main/resources/data/members.json");

    //    I will be using singleton pattern,
    //    to maintain a single instance throughout the entire Project
    private static FileMemberRepository instance;

    private FileMemberRepository() throws Exception {
        membersByEmail = new HashMap<>();
        membersById = new HashMap<>();
        System.out.println(file.getAbsolutePath());
        //    It's official I hate java more than my life 😭
        members = mapper.readValue(
                // TODO: dig more into getResourceAsStream and typeReference later
                Main.class.getResourceAsStream("/data/members.json"),
                new TypeReference<>() {
                }
        );

        indexMembers();
    }

    public static FileMemberRepository getInstance() {
        if (instance == null) {
            try {
                instance = new FileMemberRepository();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public String toString() {
        //   TODO: Stream looks interesting check doc when you have time
        return "FileMemberRepository{\n" +
                members.stream()
                        .map(m -> "  " + m + ", ")
                        .reduce("", (a, b) -> a + b + "\n") +
                "}";
    }

    @Override
    public Member findByEmail(String email) {
        return membersByEmail.get(email);
    }

    @Override
    public void update(Member member) {
        // Replace in list
        for (int i = 0; i < members.size(); i++) {
            if (members.get(i).getId().equals(member.getId())) {
                members.set(i, member);
                break;
            }
        }

        // Update maps
        membersById.put(member.getId(), member);
        membersByEmail.put(member.getEmail().toLowerCase(), member);

        save();
    }

    @Override
    public boolean existsByEmail(String email) {
        return membersByEmail.containsKey(email);
    }

    @Override
    public void save() {
        saveToFile();
    }

    @Override
    public Member add(Member entity) {
        members.add(entity);
        membersByEmail.put(entity.getEmail(), entity);
        membersById.put(entity.getId(), entity);
        save();
        return entity;
    }

    @Override
    public Member findById(String id) {
        return membersById.get(id);
    }

    @Override
    public List<Member> findAll() {
        return members;
    }

    @Override
    public void deleteById(String id) {
        //   removeIf could be easier but less declarative
        Member removedMember = membersById.get(id);
        members.remove(removedMember);
        membersByEmail.remove(removedMember.getEmail());
        membersById.remove(id);
        save();
    }

    @Override
    public boolean existsById(String id) {
        return membersById.containsKey(id);
    }

    private void indexMembers() {
        for (Member m : members) {
            membersByEmail.put(m.getEmail().toLowerCase(), m);
            membersById.put(m.getId(), m);
        }
    }

    private void saveToFile() {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, members);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
