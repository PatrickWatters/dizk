/* @file
 *****************************************************************************
 * @author     This file is part of zkspark, developed by SCIPR Lab
 *             and contributors (see AUTHORS).
 * @copyright  MIT license (see LICENSE file)
 *****************************************************************************/

package algebra.curves.barreto_naehrig.bn254a.bn254a_parameters;

import algebra.fields.Fp;
import algebra.curves.barreto_naehrig.abstract_bn_parameters.AbstractBNFrParameters;

import java.io.Serializable;
import java.math.BigInteger;

public class BN254aFrParameters extends AbstractBNFrParameters implements Serializable {
    public BigInteger modulus;
    public BigInteger root;
    public Fp multiplicativeGenerator;
    public long numBits;

    public BigInteger euler;
    public long s;
    public BigInteger t;
    public BigInteger tMinus1Over2;
    public Fp nqr;
    public Fp nqrTot;

    public Fp ZERO;
    public Fp ONE;

    public BN254aFrParameters() {

        /* parameters for scalar field Fr */

        /*
        The prime field we work in when programming zkSNARKs is defined by the elliptic curve 
        chosen to implement a zkSNARK proving system. To be more precise, the prime p which 
        represents the field modulus is defined by the group order r of the elliptic curve.
        In BN254 operations will operarate around the prime 21888242871839275222246405745257275088548364400416034343698204186575808495617
        which equates to the group order r of BN128
         * 
         * //modulus of bn128's elliptic curve group (n) Order of elliptic curve E(Fq) G1, and therefore also the characteristic of the prime field we choose our exponents from
         * this is the number  the number of elements in both G₁ and G₂
         *  Order is the number of elements in both G₁ and G₂: 36u⁴+36u³+18u²+6u+1.
         * Needs to be highly 2-adic for efficient SNARK key and proof generation.
         * Order - 1 = 2^28 * 3^2 * 13 * 29 * 983 * 11003 * 237073 * 405928799 * 1670836401704629 * 13818364434197438864469338081.
        // Refer to https://eprint.iacr.org/2013/879.pdf and https://eprint.iacr.org/2013/507.pdf for more information on these parameters.
        */

        this.modulus = new BigInteger("21888242871839275222246405745257275088548364400416034343698204186575808495617"); 


        this.root = new BigInteger("19103219067921713944291392827692070036145651957329286315305642004821462161904"); //root of unity
        this.multiplicativeGenerator = new Fp("5", this); //generator G
        this.numBits = 254;

        //omega=power_mod(root,2^(28-11),p)
        
        this.euler = new BigInteger("10944121435919637611123202872628637544274182200208017171849102093287904247808");
        this.s = 28;
        this.t = new BigInteger("81540058820840996586704275553141814055101440848469862132140264610111");
        this.tMinus1Over2 = new BigInteger("40770029410420498293352137776570907027550720424234931066070132305055");
        this.nqr = new Fp("5", this);
        this.nqrTot = new Fp("19103219067921713944291392827692070036145651957329286315305642004821462161904", this);

        this.ZERO = new Fp(BigInteger.ZERO, this);
        this.ONE = new Fp(BigInteger.ONE, this);
    }

    public BigInteger modulus() {
        return modulus;
    }

    public BigInteger root() {
        return root;
    }

    public Fp multiplicativeGenerator() {
        return multiplicativeGenerator;
    }

    public long numBits() {
        return numBits;
    }

    public BigInteger euler() {
        return euler;
    }

    public long s() {
        return s;
    }

    public BigInteger t() {
        return t;
    }

    public BigInteger tMinus1Over2() {
        return tMinus1Over2;
    }

    public Fp nqr() {
        return nqr;
    }

    public Fp nqrTot() {
        return nqrTot;
    }

    public Fp ZERO() {
        return ZERO;
    }

    public Fp ONE() {
        return ONE;
    }
}
