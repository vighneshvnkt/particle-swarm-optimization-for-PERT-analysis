/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package algosproject;

/**
 *
 * @author vighnesh
 */
  public class Particle {

        private int mData[] = new int[AlgosProject.MAX_INPUTS];
        public int mpBest = 0;
        private double mVelocity = 0.0;

        public Particle() {
            this.mpBest = 0;
            this.mVelocity = 0.0;
        }

        public int data(int index) {
            return this.mData[index];
        }

        public void data(int index, int value) {
            this.mData[index] = value;
            return;
        }

        public int pBest() {
            return this.mpBest;
        }

        public void pBest(int value) {
            this.mpBest = value;
            return;
        }

        public double velocity() {
            return this.mVelocity;
        }

        public void velocity(double velocityScore) {
            this.mVelocity = velocityScore;
            return;
        }
    } // Particle
