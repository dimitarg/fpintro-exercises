package org.example.chapter7;

import fj.P1;
import fj.Try;
import fj.Unit;
import fj.data.List;
import fj.data.Validation;

import java.nio.charset.StandardCharsets;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class FileUtil
{

    /**
     * Read a file
     */
    public static P1<Validation<Exception, String>> readFile(Path absolutePath)
    {
        return Try.f(() -> new String(Files.readAllBytes(absolutePath)));
    }

    /**
     * Write a file. If file already exists and is folder, fail. If file aready exists and is file and force=false, fail
     */
    public static P1<Validation<Exception, Unit>> writeFile(Path absolutePath, String contents, boolean force)
    {
        return Try.f(() ->
        {
            if (!force && Files.exists(absolutePath))
            {
                throw new FileAlreadyExistsException(absolutePath.toString());
            }

            Files.write(absolutePath, contents.getBytes(StandardCharsets.UTF_8));
            return Unit.unit();
        });
    }

    /**
     * Delete a file. If file does not exist, fail
     */
    public static P1<Validation<Exception, Unit>> delete(Path absolutePath)
    {
        return Try.f(() ->
        {
            Files.delete(absolutePath);
            return Unit.unit();
        });
    }

    /**
     * List the files and folders which are direct descendants of this path
     * If path does not exist, fail.
     */
    public static P1<Validation<Exception, List<String>>> ls(Path absolutePath)
    {
        return Try.f(() ->
        {
            java.util.List<String> files = Files.list(absolutePath).map(f -> f.toString()).collect(Collectors.toList());
            return fromJavaList(files);
        });
    }

    private static <A> List<A> fromJavaList(java.util.List<A> list)
    {
        List<A> res = List.nil();
        for (int curr = list.size() - 1; curr >= 0; curr--)
        {
            res = List.cons(list.get(curr), res);
        }

        return res;
    }
}
