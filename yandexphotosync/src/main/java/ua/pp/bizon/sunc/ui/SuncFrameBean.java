package ua.pp.bizon.sunc.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class SuncFrameBean {

    @Autowired
    @Qualifier(value="FileFrameBeanLeft")
    private FileFrameBean left;
    
    @Autowired
    @Qualifier(value="FileFrameBeanRight")
    private FileFrameBean rigth;

    @Autowired
    private JobsBean jobs;

    public FileFrameBean getLeft() {
        return left;
    }

    public void setLeft(FileFrameBean left) {
        this.left = left;
    }

    public FileFrameBean getRigth() {
        return rigth;
    }

    public void setRigth(FileFrameBean rigth) {
        this.rigth = rigth;
    }

    public JobsBean getJobs() {
        return jobs;
    }

    public void setJobs(JobsBean jobs) {
        this.jobs = jobs;
    }

}
