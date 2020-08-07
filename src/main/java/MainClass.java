import com.ppsdevelopment.imagelib.IImageEngine;
import com.ppsdevelopment.imagelib.ImageEngine;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;

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

            //ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("c:/applicationcontext.xml");
            FileSystemXmlApplicationContext context = new FileSystemXmlApplicationContext(contextPath);


//            IImportProcessor importProcessor=context.getBean("importProcessor",ImportProcessor.class);
//            importProcessor.loadTable();
//            Map<Integer, TableCollection> items=importProcessor.getItems();

//            FilesPathReader filesPathReader=context.getBean("filesPathReader",FilesPathReader.class);
//            String[] files=filesPathReader.getFilesCollection();

            IImageEngine imageEngine = context.getBean("imageEngine", ImageEngine.class);
            //imageEngine.setPhotoFilesCollection(files);
//            imageEngine.setInfoTable(items);
            imageEngine.process();
            context.close();
        }
        catch (Exception e){
            System.out.println("Ошибка приложения. Сообщение об ошибке:"+e.toString());
        }
   }

}
