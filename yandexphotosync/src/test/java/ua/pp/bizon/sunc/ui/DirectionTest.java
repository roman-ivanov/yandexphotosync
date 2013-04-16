package ua.pp.bizon.sunc.ui;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DirectionTest {

    @Test
    public void testIsApplicable() {
        assertTrue(Direction.Both.isApplicable(Direction.Both));
        assertTrue(Direction.Both.isApplicable(Direction.LeftToRigth));
        assertTrue(Direction.Both.isApplicable(Direction.RigthToLeft));
        assertTrue(Direction.LeftToRigth.isApplicable(Direction.Both));
        assertFalse(Direction.LeftToRigth.isApplicable(Direction.RigthToLeft));
        assertTrue(Direction.LeftToRigth.isApplicable(Direction.LeftToRigth));
        assertTrue(Direction.RigthToLeft.isApplicable(Direction.Both));
        assertFalse(Direction.RigthToLeft.isApplicable(Direction.LeftToRigth));
        assertTrue(Direction.RigthToLeft.isApplicable(Direction.RigthToLeft));
    }

}
