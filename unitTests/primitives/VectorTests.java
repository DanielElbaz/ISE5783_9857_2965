package primitives;

import org.junit.jupiter.api.Test;
import primitives.*;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class VectorTests {
    Vector v1 = new Vector(1, 2, 3);
    Vector v2 = new Vector(-2, -4, -6);
    Vector v3 = new Vector(0, 3, -2);

    /**
     * Test method for {@link primitives.Vector#dotProduct(primitives.Vector)}.
     */
    @Test
    public void testDotProductEP() {

        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple dotProduct test
        assertEquals(-28d, v1.dotProduct(v2), 0.00001, "dotProduct() wrong value");
    }

    @Test
    public void testDotProductBVA() {
        // =============== Boundary Values Tests ==================
        // TC11: dotProduct for orthogonal vectors
        assertEquals(
                0d, v1.dotProduct(v3),
                0.00001,
                "dotProduct() for orthogonal vectors is not zero");
    }

    @Test
    void testCrossProduct() {

        // ============ Equivalence Partitions Tests ==============
        Vector vr = v1.crossProduct(v3);

        // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
        assertEquals(v1.length() * v3.length(), vr.length(), 0.00001, "crossProduct() wrong result length");

        // Test cross-product result orthogonality to its operands
        assertTrue(isZero(vr.dotProduct(v1)), "crossProduct() result is not orthogonal to 1st operand");
        assertTrue(isZero(vr.dotProduct(v3)), "crossProduct() result is not orthogonal to 2nd operand");

        // =============== Boundary Values Tests ==================
        assertThrows(
                IllegalArgumentException.class,
                () -> v1.crossProduct(v2),
                "crossProduct() for parallel vectors does not throw an exception"
        );
    }


    @Test
    void testLength() {
        assertEquals(5d, new Vector(0, 3, 4).length(), "length() wrong value");
    }

    @Test
    void testLengthSquared() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(14d,
                v1.lengthSquared(), 0.00001,
                "lengthSquared() wrong value");

    }


    @Test
    void testNormalize() {
        Vector v = new Vector(3.5, -5, 10);
        v = v.normalize();
        assertEquals(1, v.length(), 1e-10,"normalize() function creates a vector with length other than 1");

        assertThrows(IllegalArgumentException.class,
                () -> new Vector(0, 0, 0),
                "head camnot be (0,0,0)");
    }

    @Test
    public void testAdd() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1),
                new Vector(2, 3, 4).add(new Vector(-1, -2, -3)),
                "Wrong vector add");

        // =============== Boundary Values Tests ==================
        // TC11: test adding v + (-v)
//		assertThrows("Add v plus -v must throw exception", IllegalArgumentException.class,
//				() -> new Vector(1, 2, 3).add(new Vector(-1, -2, -3)));
    }

    @Test
    void testSubtractEP() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(
                new Vector(1, 1, 1),
                new Vector(2, 3, 4).subtract(new Vector(1, 2, 3)),
                "Wrong vector subtract");
    }

    @Test
    void testSubtractBVA() {
        // =============== Boundary Values Tests ==================
        // TC11: test subtracting same vector
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).subtract(new Vector(1, 2, 3)),
                "Subtract v from v must throw exception");
    }

    /**
     * Test method for {@link primitives.Point#subtract(primitives.Point)}.
     */
    @Test
    public void testPointSubtract() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(1, 1, 1),
                new Point(2, 3, 4).subtract(new Point(1, 2, 3)),
                "Wrong point subtract");

        // =============== Boundary Values Tests ==================
        // TC11: test subtracting same point
//		assertThrows("Subtract P from P must throw exception", IllegalArgumentException.class,
//				() -> new Point(1, 2, 3).subtract(new Point(1, 2, 3)));
    }

    @Test
    public void testScaleEP() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: Simple test
        assertEquals(new Vector(2, 4, 6),
                new Vector(1, 2, 3).scale(2),
                "Wrong vector scale");
    }

    @Test
    public void testScaleBVA() {
        // =============== Boundary Values Tests ==================
        // TC11: test scaling to 0
        assertThrows(IllegalArgumentException.class,
                () -> new Vector(1, 2, 3).scale(0d),
                "Scale by 0 must throw exception");
    }

}