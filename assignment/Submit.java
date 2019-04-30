import java.util.zip.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.io.*;

public class Submit {
    public static void main(String[] args) throws Exception {
        // copy all java and tm files in the CWD into zip file named tm-submission-TIMESTAMP.zip
        LocalDateTime time = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String ts = time.format(formatter);
        FileOutputStream fos = new FileOutputStream("tm-submission-"+ts+".zip");
        ZipOutputStream zos = new ZipOutputStream(fos);
        File cwd = new File(".");
        File[] files = cwd.listFiles(new FilenameFilter() {
            @Override
            public boolean accept(File dir, String name) {
                return name.endsWith(".java") || name.endsWith(".tm");
            }
        });
        for(File f : files) {
            FileInputStream fis = new FileInputStream(f);
            ZipEntry ze = new ZipEntry(f.getName());
            zos.putNextEntry(ze);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zos.write(bytes, 0, length);
            }
            zos.closeEntry();
            fis.close();
        }
        zos.close();
        fos.close();
    }
}