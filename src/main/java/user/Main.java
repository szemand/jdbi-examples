package user;

import ex9.LegoSet;
import ex9.LegoSetDao;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

import java.time.LocalDate;

public class Main {

    public static void main(String[] args) {
        Jdbi jdbi = Jdbi.create("jdbc:h2:mem:test");
        jdbi.installPlugin(new SqlObjectPlugin());
        try (Handle handle = jdbi.open()) {
            UserDAO dao = handle.attach(UserDAO.class);
            dao.createTable();

            User user1 = User.builder()
                    .name("James Bond")
                    .username("007")
                    .password("ezegyjelszo1")
                    .email("ezegyemail@cim1.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1920-11-11"))
                    .enabled(true)
                    .build();

            User user2 = User.builder()
                    .name("Jóska Pista")
                    .username("pisti")
                    .password("ezegyjelszo2")
                    .email("ezisemail@cim2.com")
                    .gender(User.Gender.MALE)
                    .dob(LocalDate.parse("1999-01-21"))
                    .enabled(true)
                    .build();

            dao.insert(user1);
            dao.insert(user2);

            dao.findById(1).ifPresent(System.out::println);

            dao.findByUsername("pisti").ifPresent(System.out::println);

            dao.delete(user1);

            dao.list().stream().forEach(System.out::println);
        }
    }

}
