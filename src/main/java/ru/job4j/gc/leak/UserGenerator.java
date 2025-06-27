package ru.job4j.gc.leak;

import ru.job4j.gc.leak.models.User;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UserGenerator implements Generate {

    public static final String PATH_NAMES = "files/names.txt";
    public static final String PATH_SURNAMES = "files/surnames.txt";
    public static final String PATH_PATRONS = "files/patr.txt";

    public static final String SEPARATOR = " ";
    public static final Integer NEW_USERS = 1000;

    private final List<User> users = new ArrayList<>();
    private final Random random;

    public List<String> names;
    public List<String> surnames;
    public List<String> patrons;

    public UserGenerator(Random random) {
        this.random = random;
        readAll();
    }

    @Override
    public void generate() {
        users.clear();
        for (int i = 0; i < NEW_USERS; i++) {
            var name = surnames.get(random.nextInt(surnames.size())) + SEPARATOR
                    + names.get(random.nextInt(names.size())) + SEPARATOR
                    + patrons.get(random.nextInt(patrons.size()));
            var user = new User();
            user.setName(name);
            users.add(user);
        }
    }

    private void readAll() {
        try (Stream<String> namesStream = Files.lines(Path.of(PATH_NAMES));
             Stream<String> surnamesStream = Files.lines(Path.of(PATH_SURNAMES));
             Stream<String> patronsStream = Files.lines(Path.of(PATH_PATRONS))
        ) {
            names = namesStream.collect(Collectors.toList());
            surnames = surnamesStream.collect(Collectors.toList());
            patrons = patronsStream.collect(Collectors.toList());

        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public User randomUser() {
        return users.get(random.nextInt(users.size()));
    }
}