package org.example.chapter7;

import fj.Unit;
import fj.data.List;
import fj.data.Validation;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class FileUtilTest
{
    private static final Path RESOURCES_DEST = Paths.get("src/test/resources");
    private static final Path FIRST_FILE = Paths.get(RESOURCES_DEST + "/first.txt");
    private static final Path SECOND_FILE = Paths.get(RESOURCES_DEST + "/second.txt");
    private static final String CONTENTS = "Lorem ipblabla";

    @BeforeClass
    public static void setUp() throws IOException
    {
        if (!Files.exists(RESOURCES_DEST))
        {
            Files.createDirectory(RESOURCES_DEST);
        }

        Files.createFile(FIRST_FILE);
        Files.createFile(SECOND_FILE);
    }

    @AfterClass
    public static void cleanUp() throws IOException
    {
        Files.deleteIfExists(FIRST_FILE);
        Files.deleteIfExists(SECOND_FILE);
        Files.deleteIfExists(RESOURCES_DEST);
    }

    @Test
    public void readFile() throws IOException
    {
        Files.write(FIRST_FILE, CONTENTS.getBytes(StandardCharsets.UTF_8), StandardOpenOption.TRUNCATE_EXISTING);

        assertThat(
                FileUtil.readFile(FIRST_FILE).f(),
                is(Validation.success(CONTENTS))
        );

        Files.write(FIRST_FILE, "".getBytes(), StandardOpenOption.TRUNCATE_EXISTING);


        Validation<Exception, String> actual = FileUtil.readFile(RESOURCES_DEST).f();
        assertEquals(IOException.class, actual.fail().getClass());
    }

    @Test
    public void writeFile() throws IOException
    {
        assertThat(
                FileUtil.writeFile(FIRST_FILE, CONTENTS, true).f(),
                is(Validation.success(Unit.unit())));

        String actualResult = new String(Files.readAllBytes(FIRST_FILE), StandardCharsets.UTF_8);
        assertEquals(CONTENTS, actualResult);


        Exception actualException = FileUtil.writeFile(FIRST_FILE, CONTENTS, false).f().fail();
        assertEquals(FileAlreadyExistsException.class, actualException.getClass());
    }

    @Test
    public void delete() throws IOException
    {
        assertThat(
                FileUtil.delete(FIRST_FILE).f(),
                is(Validation.success(Unit.unit()))
        );
        assertFalse(Files.exists(FIRST_FILE));

        Exception actual = FileUtil.delete(FIRST_FILE).f().fail();
        assertEquals(NoSuchFileException.class, actual.getClass());

        Files.createFile(FIRST_FILE);
    }

    @Test
    public void ls()
    {
        List<String> actual = FileUtil.ls(RESOURCES_DEST).f().success();

        assertEquals(2, actual.length());
        assertThat(
                actual,
                hasItems(FIRST_FILE.toString(), SECOND_FILE.toString())
        );
    }
}