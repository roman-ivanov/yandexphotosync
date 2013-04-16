package ua.pp.bizon.sunc.ui;

import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;



public class AppImpl {

    private static final AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();

    static {
        context.scan("ua.pp.bizon.sunc.ui");
        context.refresh();
    }

    public static void main(String[] args) throws Exception {
        LoggerFactory.getLogger(AppImpl.class).trace("app starting");
        SuncFrame frame = context.getBean(SuncFrame.class);
        LoggerFactory.getLogger(AppImpl.class).trace("spring app started");
        frame.setVisible(true);
        LoggerFactory.getLogger(AppImpl.class).trace("app started");
    }

}
