package main.java.com.tcas_sim.util.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector3dTest {

    @Test
    void addWithOtherVector1() {
        Vector3d test = new Vector3d(5), test2 = new Vector3d(2);
        assertEquals(new Vector3d(7), test.add(test2));
    }

    @Test
    void addWithOtherVector2() {
        Vector3d test = new Vector3d(5), test2 = new Vector3d(2,3,4);
        assertEquals(new Vector3d(7,8,9), test.add(test2));
    }

    @Test
    void addWithAddend() {
        Vector3d test = new Vector3d(5);
        assertEquals(new Vector3d(7), test.add(2));
    }

    @Test
    void multiplyWithOtherVector() {
        assertEquals(new Vector3d(3,6,9),
                new Vector3d(3).multiply(new Vector3d(1,2,3)));
    }

    @Test
    void multiplyWithFactor() {
        assertEquals(new Vector3d(70), new Vector3d(7).multiply(10));
    }

    @Test
    void distance() {
        assertEquals(2 * Math.sqrt(290), new Vector3d(25,16,42).distance(
                new Vector3d(9,18,72)
        ));
    }

    @Test
    void distance_squared() {
        assertEquals(1160, new Vector3d(25,16,42).distance_squared(
                new Vector3d(9,18,72)
        ));
    }

    @Test
    void testToString() {
        assertEquals("(1.0, 2.0, 3.0)", new Vector3d(1,2,3).toString());
    }

    @Test
    void x() {
        assertEquals(8, new Vector3d(8,52,749187).x());
    }

    @Test
    void y() {
        assertEquals(52, new Vector3d(8,52,749187).y());
    }

    @Test
    void z() {
        assertEquals(749187, new Vector3d(8,52,749187).z());
    }
}