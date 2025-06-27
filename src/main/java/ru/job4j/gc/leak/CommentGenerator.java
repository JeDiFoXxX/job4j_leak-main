package ru.job4j.gc.leak;

import ru.job4j.gc.leak.models.Comment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CommentGenerator implements Generate {

    public static final String PATH_PHRASES = "files/phrases.txt";

    public static final String SEPARATOR = System.lineSeparator();
    public static final Integer COUNT = 50;

    private final List<Comment> comments = new ArrayList<>();
    private final UserGenerator userGenerator;
    private final Random random;

    private List<String> phrases;

    public CommentGenerator(Random random, UserGenerator userGenerator) {
        this.userGenerator = userGenerator;
        this.random = random;
        read();
    }

    private void read() {
        try (Stream<String> pathPhrases = Files.lines(Path.of(PATH_PHRASES))) {
            phrases = pathPhrases.collect(Collectors.toList());
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public List<Comment> getComments() {
        return comments;
    }

    @Override
    public void generate() {
        comments.clear();
        for (int i = 0; i < COUNT; i++) {
            String text = String.format("%s%s%s%s%s",
                    phrases.get(random.nextInt(phrases.size())), SEPARATOR,
                    phrases.get(random.nextInt(phrases.size())), SEPARATOR,
                    phrases.get(random.nextInt(phrases.size())));
            var comment = new Comment();
            comment.setText(text);
            comment.setUser(userGenerator.randomUser());
            comments.add(comment);
        }
    }
}