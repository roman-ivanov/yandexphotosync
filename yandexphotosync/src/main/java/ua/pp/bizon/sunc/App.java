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
        if (args.length < 2){
            System.out.println("usage: App from to");
            System.exit(1);
        } 
        try {
            PathFactory factory = context.getBean(PathFactory.class);
            new Sync().sync(
                    factory.create(args[0]), factory.create(args[1]));
        } finally {
            context.close();
        }
        System.out.println("done");
    }
}
