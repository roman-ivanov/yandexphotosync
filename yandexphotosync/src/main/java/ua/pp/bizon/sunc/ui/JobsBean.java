package ua.pp.bizon.sunc.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ua.pp.bizon.sunc.api.Path;

public class JobsBean {
    
    private final Logger logger = LoggerFactory.getLogger(getClass());

    public void addJob(Direction direction, Path selectedLeft, Path selectedRight) {
        // TODO Auto-generated method stub
        logger.trace("job added: left=" + selectedLeft + ", rigth=" + selectedRight + ", direction=" + direction);
    }
    
    
    

}
