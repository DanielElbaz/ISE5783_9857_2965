package primitives;

public class Material {
    public Double3 kD  = Double3.ZERO;
    public Double3 kS = Double3.ZERO;
    public Double3 kT = Double3.ZERO;
    public Double3 kR = Double3.ZERO;
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

    /**
     * @param kT refraction
     * @return
     */
    public Material setkT(Double3 kT) {
        this.kT = kT;
        return this;
    }
    /**
     * @param kR reflection
     * @return
     */
    public Material setkR(Double3 kR) {
        this.kR = kR;
        return this;
    }


    /**
     * @param kR reflection
     * @return
     */
    public Material setkR(double kR) {
        this.kR = new Double3(kR);
        return this;
    }
    /**
     * @param kT refraction
     * @return
     */
    public Material setkT(double kT) {
        this.kT = new Double3(kT);
        return this;
    }


}
