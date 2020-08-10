import com.ppsdevelopment.imagelib.IImageEngine;
import com.ppsdevelopment.imagelib.ImageEngine;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class MainClass {
    public static void main(String[] args) {
        String contextPath="applicationcontext.xml";

        if (args.length>0) contextPath=args[0];
        File file=new File(contextPath);
        if (!file.exists()) {
            System.out.println("Ошибка загрузки файла контекста!");
            System.exit(1);
        }

        System.out.println("Starting application...");
        try {
            FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(contextPath);
            IImageEngine imageEngine = context.getBean("imageEngine", ImageEngine.class);

            if (imageEngine.getDestinationPath().equals(imageEngine.getFilesPathReader().getPath()))
               throw new Exception("Путь к папке изображений совпадает с путем к папке вывода. ");

            checkPath(imageEngine.getDestinationPath(),"Неправильно задан путь к папке с обработанными изображениями");
            checkPath(imageEngine.getFilesPathReader().getPath(),"Неправильно задан путь к папке с изображениями");

            imageEngine.process();
            context.close();
        }
        catch (Exception e){
            System.out.println("Ошибка приложения. Сообщение об ошибке:"+e.toString());
        }
   }

    private static void checkPath(String pathString,String message) throws Exception {
        Path path = Paths.get(pathString);
        if (!Files.exists(path)) {
            throw new Exception(message);
        }
    }

}
