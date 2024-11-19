import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class FileSystemDemo {
    public static void main(String[] args) {
        Path baseDir = Paths.get("Brizhak");
        Path file = baseDir.resolve("Gordey");
        Path dir1 = baseDir.resolve("dir1");
        Path dir2 = dir1.resolve("dir2");
        Path dir3 = dir2.resolve("dir3");

        try {
            // a. Создание директории <Brizhak>
            Files.createDirectories(baseDir);

            // b. Создание файла <Gordey>
            Files.createFile(file);

            // c. Создание вложенных директорий dir1/dir2/dir3 и копирование файла <Gordey>
            Files.createDirectories(dir3);
            Files.copy(file, dir3.resolve("Gordey"));

            // d. Создание файла file1 в dir1
            Files.createFile(dir1.resolve("file1"));

            // e. Создание файла file2 в dir2
            Files.createFile(dir2.resolve("file2"));

            // f. Рекурсивный обход директории <Brizhak>
            System.out.println("Contents of directory Brizhak:");
            Files.walkFileTree(baseDir, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    System.out.println("F: " + file.getFileName());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    System.out.println("D: " + dir.getFileName());
                    return FileVisitResult.CONTINUE;
                }
            });

            // g. Удаление директории dir1 со всем содержимым
            Files.walkFileTree(dir1, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.delete(file);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            });

            System.out.println("Directory dir1 and its contents were deleted.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
