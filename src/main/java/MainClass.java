import com.ppsdevelopment.*;
import com.ppsdevelopment.imagelib.IImageEngine;
import com.ppsdevelopment.imagelib.ImageEngine;
import com.ppsdevelopment.imagelib.ImageTexter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.Map;

public class MainClass {
    public static void main(String[] args) {

//            ImportProcessor processor=new ImportProcessor("c://files/1.xlsx");
//            processor.loadTable();

            ClassPathXmlApplicationContext context=new ClassPathXmlApplicationContext("applicationcontext.xml");

            IImportProcessor importProcessor=context.getBean("importProcessor",ImportProcessor.class);
            importProcessor.loadTable();
            Map<Integer, TableCollection> items=importProcessor.getItems();

            FilesPathReader filesPathReader=context.getBean("filesPathReader",FilesPathReader.class);
            String[] files=filesPathReader.getFilesCollection();


            IImageEngine imageEngine=context.getBean("imageEngine", ImageEngine.class);
            imageEngine.setPhotoFilesCollection(files);
            imageEngine.setInfoTable(items);
            imageEngine.process();

//            IFilesPathReader filesPathReader=context.getBean("filesPathReader", FilesPathReader.class);
//            String[] files=filesPathReader.getFilesCollection();

//            System.out.println(imageEngine.getFileName());
            context.close();
  //          String caption="Чтобы узнать, есть в массиве какой-либо элемент, можно воспользоваться методом contains(), который вернёт логическое значение true или false в зависимости от присутствия элемента в наборе";
            //Соотношение высоты шрифта к высоте экрана
            int fontLineHeightRatio=5;
////            ImageTexter texter=new ImageTexter("c://files/1.jpg",caption, 2/3f,1/20.0,10,0, fontLineHeightRatio, this.destinationPath);
//            texter.draw();
   }

}
