package primitives;

public class Material {
    public Double3 kD  = new Double3(0);
    public Double3 kS = new Double3(0);
    public int nShininess = 0;

    /**
     * constructor for Material
     * @param kD the diffuse factor
     *
     */
    public Material setkD(Double3 kD) {
        this.kD = kD;
        return this;
    }

    /**
     * constructor for Material
     * @param kS the specular factor
     *
     */
    public Material setkS(Double3 kS) {
        this.kS = kS;
        return this;
    }


    public Material setkD(double kD) {
        this.kD = new Double3(kD);
        return this;
    }

    public Material setkS(double kS) {
        this.kS = new Double3(kS);
        return this;
    }

    /**
     * constructor for Material
     * @param nShininess the shininess factor
     *
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
