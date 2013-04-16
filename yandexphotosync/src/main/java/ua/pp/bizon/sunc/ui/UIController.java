package ua.pp.bizon.sunc.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;

import ua.pp.bizon.sunc.api.PathFactory;
import ua.pp.bizon.sunc.api.impl.FactoryConfiguration;
import ua.pp.bizon.sunc.remote.api.OAuthUI;
import ua.pp.bizon.sunc.ui.impl.PathFactoryUIImpl;

@Controller
public class UIController extends FactoryConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public SuncFrame getSuncFrame() {
        return new SuncFrame();
    }

    @Bean(name = "FilePanelLeft")
    public FilePanel getLeftFilePanel() {
        FilePanel left = new FilePanel();
        left.setBean(applicationContext.getBean("FileFrameBeanLeft", FileFrameBean.class));
        return left;
    }

    @Bean(name = "FilePanelRight")
    public FilePanel getRightFilePanel() {
        FilePanel rigth = new FilePanel();
        rigth.setBean(applicationContext.getBean("FileFrameBeanRight", FileFrameBean.class));
        return rigth;
    }

    @Bean
    public SuncFrameBean getSuncFrameBean() {
        return new SuncFrameBean();
    }

    @Bean(name = "FileFrameBeanLeft")
    public FileFrameBean getFileFrameBeanLeft() {
        return  new FileFrameBean();
    }

    @Bean(name = "FileFrameBeanRight")
    public FileFrameBean getFileFrameBeanRight() {
        return new FileFrameBean();
    }

    @Bean
    public JobsBean getJobsBean() {
        return new JobsBean();
    }

    @Bean
    public ErrorHandler getErrorHandler() {
        return new ErrorHandler();
    }

    @Bean
    public OAuthUI getAuthUI() {
        return new YandexLoginDialog(applicationContext.getBean(SuncFrame.class), true);

    }

    @Bean(name="pathFactoryUI")
    public PathFactory getFactoryUI() {
        return new PathFactoryUIImpl(applicationContext.getBean("pathFactory", PathFactory.class));
    }
}
