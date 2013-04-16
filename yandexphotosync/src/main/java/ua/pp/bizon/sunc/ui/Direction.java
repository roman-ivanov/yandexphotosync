package ua.pp.bizon.sunc.ui;

public enum Direction {
    
    LeftToRigth (0b01),
    RigthToLeft (0b10),
    Both        (0b11),
    Local     (0b1000),
    Remote    (0b0100);
    
    private int direction;
    
    private Direction(int i) {
        this.direction = i;
    }
    
    public boolean isApplicable(Direction to) {
        return (direction & to.direction) != 0;

    }

}
