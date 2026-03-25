package unienroll;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import unienroll.domain.Member;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        List<Member> members = mapper.readValue(
                Main.class.getResourceAsStream("/data/members.json"),
                new TypeReference<List<Member>>() {}
        );

        for(Member mem : members){
            System.out.println(mem.toString());
        }

    }
}