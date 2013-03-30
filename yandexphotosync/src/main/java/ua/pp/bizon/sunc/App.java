package ua.pp.bizon.sunc;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import ua.pp.bizon.sunc.api.PathFactory;

/**
 * Hello world!
 * 
 */
public class App {
    
    private static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    static {
        context.scan("ua.pp.bizon.sunc.api.impl");
        context.refresh();
    }
    
    public static void main(String[] args) throws Exception {
        try {
            PathFactory factory = context.getBean(PathFactory.class);
            new Sync().sync(factory.create("file:/Users/roman/Pictures/test"),
                    factory.create("yandex:/test"));
        } finally {
            context.close();
        }
        System.out.println("done");
    }
}
